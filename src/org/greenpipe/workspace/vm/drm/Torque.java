package org.greenpipe.workspace.vm.drm;

import java.util.ArrayList;
import java.util.List;

import org.greenpipe.workspace.handler.ChefEngine;
import org.greenpipe.workspace.model.bean.Workspace;
import org.greenpipe.workspace.states.RunState;
import org.greenpipe.workspace.util.Parameters;
import org.greenpipe.workspace.util.VmUpdater;
import org.greenpipe.workspace.vm.yaml.YAML;
import org.greenpipe.workspace.vm.yaml.YAMLFactory;

public class Torque extends DRM {
	private static DRM drm = new Torque();
	
	private Torque() {}
	
	public static DRM getSingleInstance() {
		return drm;
	}

	/**
	 * Install Torque
	 */
	@Override
	public RunState installDRM(Workspace workspace, ChefEngine chefEngine) {
		// Get YAML service for this workspace
		YAML yamlService = YAMLFactory.getYAMLService(workspace.getProvider());
		if(yamlService == null)
			return RunState.YAML_FAILED;

		// Store yaml content
		String yamlContent = "";

		// Store cookbooks and recipes
		List cookbooks = new ArrayList<String>();
		List recipes = new ArrayList<String>();

		// Setup 'cookbooks'
		cookbooks.clear();
		cookbooks.add(Parameters.COOKBOOK_TORQUE);
		cookbooks.add(Parameters.COOKBOOK_YUM_EPEL);
		cookbooks.add(Parameters.COOKBOOK_YUM);
		yamlContent = yamlContent + yamlService.fillCookbooks(cookbooks) + "\n";

		// Setup 'nodes' tag
		yamlContent = yamlContent + yamlService.fillNodesTag() + "\n";

		// 1. Setup 'Install and start torque server'
		recipes.clear();	
		recipes.add(Parameters.RECIPE_HEAD_NODE);
		yamlContent = yamlContent + yamlService.fillNodeBootstrap(VmUpdater.getHostname(workspace, 1), 
				chefEngine.getAttributes().getDomain(), Parameters.ROOT_USERNAME, Parameters.PASSWORD, recipes) + "\n";

		// 2. Setup 'Install and start torque mom'
		for(int i = 2; i <= workspace.getVmNumber(); i++) {
			recipes.clear();
			recipes.add(Parameters.RECIPE_COMPUTE_NODE);
			yamlContent = yamlContent + yamlService.fillNodeBootstrap(VmUpdater.getHostname(workspace, i), 
					chefEngine.getAttributes().getDomain(), Parameters.ROOT_USERNAME, Parameters.PASSWORD, recipes) + "\n";
		}

		// 3. Setup 'Restart torque server'
		recipes.clear();	
		recipes.add(Parameters.RECIPE_HEAD_NODE);
		yamlContent = yamlContent + yamlService.fillNodeBootstrap(VmUpdater.getHostname(workspace, 1), 
				chefEngine.getAttributes().getDomain(), Parameters.ROOT_USERNAME, Parameters.PASSWORD, recipes) + "\n";

		//		// 4. Restart torque server again ( a bug ? )
		//		recipes.clear();	
		//		recipes.add(Parameters.RECIPE_HEAD_NODE);
		//		serverName = provider + "-" + clusterName + "-" + vmNumber + "-" + 1;
		//		yaml = yaml + NodesBootstrapYAML.formNodesBootstrap(serverName, Parameters.REMOTE_INTERNAL_DOMAIN, 
		//				Parameters.ROOT_USERNAME, Parameters.PASSWORD, recipes) + "\n";

		System.out.println("\n----- Install Torque Spiceweasel -----\n" + yamlContent + "\n");
		String fileName = "torque_" + workspace.getId() + ".yml";
		int status = execute(yamlContent, chefEngine, fileName);
		if(status == 0)
			return RunState.DRM_SUCCESSFUL;
		else
			return RunState.DRM_FAILED;
	}

}
