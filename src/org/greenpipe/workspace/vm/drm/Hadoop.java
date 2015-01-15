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

public class Hadoop extends DRM {
	private static DRM drm = new Hadoop();

	private Hadoop() {}

	public static DRM getSingleInstance() {
		return drm;
	}

	/**
	 * Install Hadoop
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
		cookbooks.add(Parameters.COOKBOOK_HADOOP);
		cookbooks.add(Parameters.COOKBOOK_JAVA);
		yamlContent = yamlContent + yamlService.fillCookbooks(cookbooks) + "\n";

		// Setup 'nodes' tag
		yamlContent = yamlContent + yamlService.fillNodesTag() + "\n";

		// 1. Setup 'Install Java'
		for(int i = 1; i <= workspace.getVmNumber(); i++) {
			recipes.clear();
			recipes.add(Parameters.RECIPE_DEFAULT);
			yamlContent = yamlContent + yamlService.fillNodeBootstrap(VmUpdater.getHostname(workspace, i), 
					chefEngine.getAttributes().getDomain(), Parameters.ROOT_USERNAME, Parameters.PASSWORD, recipes) + "\n";
		}

		// 2. Setup 'Install Hadoop'
		for(int i = 1; i <= workspace.getVmNumber(); i++) {
			recipes.clear();
			recipes.add(Parameters.RECIPE_SETUP_HADOOP);
			yamlContent = yamlContent + yamlService.fillNodeBootstrap(VmUpdater.getHostname(workspace, i), 
					chefEngine.getAttributes().getDomain(), Parameters.ROOT_USERNAME, Parameters.PASSWORD, recipes) + "\n";
		}

		// 3. Setup 'master'
		recipes.clear();	
		recipes.add(Parameters.RECIPE_SETUP_MASTER);
		yamlContent = yamlContent + yamlService.fillNodeBootstrap(VmUpdater.getHostname(workspace, 1), 
				chefEngine.getAttributes().getDomain(), Parameters.ROOT_USERNAME, Parameters.PASSWORD, recipes) + "\n";

		// 5. Setup 'Start Hadoop'
		recipes.clear();
		recipes.add(Parameters.RECIPE_START_HADOOP);
		yamlContent = yamlContent + yamlService.fillNodeBootstrap(VmUpdater.getHostname(workspace, 1), 
				chefEngine.getAttributes().getDomain(), Parameters.ROOT_USERNAME, Parameters.PASSWORD, recipes) + "\n";

		System.out.println("\n----- Install Hadoop Spiceweasel -----\n" + yamlContent + "\n");
		String fileName = "hadoop_" + workspace.getId() + ".yml";
		int status = execute(yamlContent, chefEngine, fileName);
		if(status == 0)
			return RunState.DRM_SUCCESSFUL;
		else
			return RunState.DRM_FAILED;
	}

}
