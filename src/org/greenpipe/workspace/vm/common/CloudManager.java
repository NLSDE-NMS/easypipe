package org.greenpipe.workspace.vm.common;

import java.io.IOException;
import java.net.MalformedURLException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.greenpipe.workspace.handler.ChefEngine;
import org.greenpipe.workspace.model.bean.Cookbook;
import org.greenpipe.workspace.model.bean.CookbookDependency;
import org.greenpipe.workspace.model.bean.Vm;
import org.greenpipe.workspace.model.bean.Workspace;
import org.greenpipe.workspace.model.bean.WorkspaceCookbookAssociation;
import org.greenpipe.workspace.restful.RestfulAPI;
import org.greenpipe.workspace.restful.RestfulAPIFactory;
import org.greenpipe.workspace.states.RunState;
import org.greenpipe.workspace.util.Parameters;
import org.greenpipe.workspace.util.VmUpdater;
import org.greenpipe.workspace.vm.drm.DRM;
import org.greenpipe.workspace.vm.drm.DRMFactory;
import org.greenpipe.workspace.vm.yaml.YAML;
import org.greenpipe.workspace.vm.yaml.YAMLFactory;

public class CloudManager {
	private static CloudManager cloudManager = new CloudManager();

	private CloudManager() {}

	public static CloudManager getSingleInstance() {
		return cloudManager;
	}

	/**
	 * Create VMs
	 * @param workspace
	 * @param chefEngine
	 * @return
	 */
	public RunState create(Workspace workspace, ChefEngine chefEngine) {
		// Check if there are enough cores
		RestfulAPI restful = RestfulAPIFactory.getRestfulAPI(workspace.getProvider());
		boolean hasEnough = restful.hasEnoughCores(workspace.getVmSize(), workspace.getVmNumber(), 
				chefEngine.getAttributes());
		if(!hasEnough) {
			return RunState.RESOURCES_FAILED;
		}
		
		// Get YAML service for this workspace
		YAML yamlService = YAMLFactory.getYAMLService(workspace.getProvider());
		if(yamlService == null)
			return RunState.YAML_FAILED;
		
		// Store yaml content
		String yamlContent = "";

		// Store cookbooks and recipes
		List<String> cookbooks = new ArrayList<String>();
		List<String> recipes = new ArrayList<String>();

		// 1. Setup 'cookbooks'
		cookbooks.clear();
		cookbooks.add(Parameters.COOKBOOK_INIT_ROOT);
		cookbooks.add(Parameters.COOKBOOK_HADOOP);
		cookbooks.add(Parameters.COOKBOOK_JAVA);
		yamlContent = yamlContent + yamlService.fillCookbooks(cookbooks) + "\n";

		// 2. Setup 'nodes' tag
		yamlContent = yamlContent + yamlService.fillNodesTag() + "\n";

		// 3. Setup 'Create VMs'
		recipes.clear();
		recipes.add(Parameters.RECIPE_INIT_ROOT);
		yamlContent = yamlContent + yamlService.fillNodesCreation(workspace, chefEngine.getAttributes(), recipes, 
				VmUpdater.getClustername(workspace), Parameters.NORMAL_USERNAME, Parameters.PASSWORD) + "\n";
		
		// 4. Setup 'Configure SSH public keys', ...take this step out of creation
		for(int i = 1; i <= workspace.getVmNumber(); i++) {
			recipes.clear();
			recipes.add(Parameters.RECIPE_SSH_PUBLIC_KEYS);
			yamlContent = yamlContent + yamlService.fillNodeBootstrap(VmUpdater.getHostname(workspace, i), 
					chefEngine.getAttributes().getDomain(), Parameters.ROOT_USERNAME, Parameters.PASSWORD, recipes) + "\n";
		}

		// 5. Setup 'Authorized keys, and /etc/hosts'
		for(int i = 1; i <= workspace.getVmNumber(); i++) {
			recipes.clear();
			recipes.add(Parameters.RECIPE_AUTHORIZED_NODES);
			recipes.add(Parameters.RECIPE_SETUP_HOSTS);
			yamlContent = yamlContent + yamlService.fillNodeBootstrap(VmUpdater.getHostname(workspace, i), 
					chefEngine.getAttributes().getDomain(), Parameters.ROOT_USERNAME, Parameters.PASSWORD, recipes) + "\n";
		}

		System.out.println("\n----- Create VMs Spiceweasel -----\n" + yamlContent + "\n");
		String fileName = "create_" + workspace.getId() + ".yml";
		int status = execute(yamlContent, chefEngine, fileName);
		if(status == 0)
			return RunState.CREATE_SUCCESSFUL;
		else
			return RunState.CREATE_FAILED;
	}

	/**
	 * Bootstrap VMs
	 * @param workspace
	 * @param chefEngine
	 * @return
	 */
	public RunState bootstrap(Workspace workspace, ChefEngine chefEngine) {
		// Get YAML service for this workspace
		YAML yamlService = YAMLFactory.getYAMLService(workspace.getProvider());
		if(yamlService == null)
			return RunState.YAML_FAILED;
		
		// Store yaml content
		String yamlContent = "";
		
		// Store cookbooks and recipes
		List<String> cookbooks = new ArrayList<String>();
		List<String> recipes = new ArrayList<String>();
		
		// Get cookbooks
		Set workspaceCookbookAssociations = workspace.getWorkspaceCookbookAssociations();
		Set<Cookbook> cookbookSet = new HashSet<Cookbook>();
		for (Object object : workspaceCookbookAssociations) {
			WorkspaceCookbookAssociation association = (WorkspaceCookbookAssociation) object;
			Cookbook cookbook = association.getCookbook();
			// Only add 'package'-level cookbooks into cookbookSet
			if (!cookbookSet.contains(cookbook) && 
					cookbook.getCategory().equals(Parameters.COOKBOOK_CATEGORY_PACKAGE)) {
				cookbookSet.add(cookbook);
				findDependencyCookbook(cookbookSet, cookbook);
			}
		}
		
		// 1. Setup 'cookbooks'
		for(Cookbook cookbook : cookbookSet) {
			cookbooks.add(cookbook.getName());
		}
		if (cookbooks.size() == 0)
			return RunState.BOOTSTRAP_SUCCESSFUL;
		else
			yamlContent = yamlContent + yamlService.fillCookbooks(cookbooks) + "\n";
		

		// 2. Setup 'nodes' tag
		yamlContent = yamlContent + yamlService.fillNodesTag() + "\n";

		// 3. Setup 'Bootstrap VMs'
		for (Object object : workspaceCookbookAssociations) {
			WorkspaceCookbookAssociation association = (WorkspaceCookbookAssociation) object;
			Cookbook cookbook = association.getCookbook();
			// Only add 'package'-level cookbooks into recipes
			if (cookbook.getCategory().equals(Parameters.COOKBOOK_CATEGORY_PACKAGE)) {
				recipes.add(cookbook.getName() + "::default");
			}
		}
		for(int i = 1; i <= workspace.getVmNumber(); i++) {
			yamlContent = yamlContent + yamlService.fillNodeBootstrap(VmUpdater.getHostname(workspace, i), 
					chefEngine.getAttributes().getDomain(), Parameters.ROOT_USERNAME, Parameters.PASSWORD, recipes) + "\n";
		}

		System.out.println("\n----- Bootstrap VMs Spiceweasel -----\n" + yamlContent + "\n");
		String fileName = "bootstrap_" + workspace.getId() + ".yml";
		int status = execute(yamlContent, chefEngine, fileName);
		if(status == 0)
			return RunState.BOOTSTRAP_SUCCESSFUL;
		else 
			return RunState.BOOTSTRAP_FAILED;
	}

	/**
	 * Delete VMs
	 * @param workspace
	 * @param chefEngine
	 * @return
	 */
	public RunState delete(Workspace workspace, ChefEngine chefEngine) {
		// Get YAML service for this workspace
		YAML yamlService = YAMLFactory.getYAMLService(workspace.getProvider());
		if(yamlService == null)
			return RunState.YAML_FAILED;
		
		// Store yaml content
		String yamlContent = "";
		
		// Store cookbooks and recipes
		List<String> cookbooks = new ArrayList<String>();
		List<String> recipes = new ArrayList<String>();
		
		// 1. Setup 'knife' tag
		yamlContent = yamlContent + yamlService.fillKnifeTag() + "\n";
		
		// 2. Setup 'Delete VMs'
		for(int i = 1; i <= workspace.getVmNumber(); i++) {
			yamlContent = yamlContent + yamlService.fillNodeDeletion(VmUpdater.getHostname(workspace, i)) + "\n";
		}
		
		System.out.println("\n----- Delete VMs Spiceweasel -----\n" + yamlContent + "\n");
		String fileName = "delete_" + workspace.getId() + ".yml";
		int status = execute(yamlContent, chefEngine, fileName);
		if(status == 0)
			return RunState.DELETE_SUCCESSFUL;
		else
			return RunState.DELETE_FAILED;
	}

	/**
	 * List workspaces, implemented in other place
	 * @param workspace
	 * @param chefEngine
	 * @return
	 */
	public RunState list(Workspace workspace, ChefEngine chefEngine) {
		return RunState.LIST_SUCCESSFUL;
	}

	/**
	 * Start VMs
	 * @param workspace
	 * @param chefEngine
	 * @return
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyStoreException 
	 * @throws KeyManagementException 
	 * @throws UnrecoverableKeyException 
	 */
	@SuppressWarnings("unchecked")
	public RunState start(Workspace workspace, ChefEngine chefEngine) {
		RestfulAPI restful = RestfulAPIFactory.getRestfulAPI(workspace.getProvider());
		Set<Vm> vms = workspace.getVms();
		List<String> vmNameList = new ArrayList<String>();
		for(Vm vm : vms) {
			vmNameList.add(vm.getHostname());
		}
		return restful.startVMs(vmNameList, chefEngine.getAttributes());
	}

	/**
	 * Stop VMs
	 * @param workspace
	 * @param chefEngine
	 * @return
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyStoreException 
	 * @throws KeyManagementException 
	 * @throws UnrecoverableKeyException 
	 */
	@SuppressWarnings("unchecked")
	public RunState stop(Workspace workspace, ChefEngine chefEngine) {
		RestfulAPI restful = RestfulAPIFactory.getRestfulAPI(workspace.getProvider());
		Set<Vm> vms = workspace.getVms();
		List<String> vmNameList = new ArrayList<String>();
		for(Vm vm : vms) {
			vmNameList.add(vm.getHostname());
		}
		return restful.stopVMs(vmNameList, chefEngine.getAttributes());
	}
	
	/**
	 * Restart VMs
	 * @param workspace
	 * @param chefEngine
	 * @return
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyStoreException 
	 * @throws KeyManagementException 
	 * @throws UnrecoverableKeyException 
	 */
	@SuppressWarnings("unchecked")
	public RunState restart(Workspace workspace, ChefEngine chefEngine) {
		RestfulAPI restful = RestfulAPIFactory.getRestfulAPI(workspace.getProvider());
		Set<Vm> vms = workspace.getVms();
		for(Vm vm : vms) {
			RunState status = null;
			status = restful.restartSingleVM(vm.getHostname(), chefEngine.getAttributes());
			if(status != RunState.RESTART_SUCCESSFUL)
				return status;
			
			try {
				Thread.sleep(60 * 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return RunState.RESTART_SUCCESSFUL;
	}
	
	/**
	 * Install DRM
	 * @param workspace
	 * @param chefEngine
	 * @param drmName
	 * @return
	 */
	public RunState installDRM(Workspace workspace, ChefEngine chefEngine, String drmName) {
		DRM drmService = DRMFactory.getDRM(drmName);
		return drmService.installDRM(workspace, chefEngine);
	}

	/**
	 * Find dependency for cookbooks
	 * @param cookbookSet
	 * @param cookbook
	 */
	private void findDependencyCookbook(Set<Cookbook> cookbookSet, Cookbook cookbook) {
		// TODO Auto-generated method stub
		//CookbookDependencyHome dependencyHome = new CookbookDependencyHome();
		Set dependencySet = cookbook.getCookbookDependenciesForDestinationId();
		if (dependencySet != null && !dependencySet.isEmpty()) {
			for (Object object : dependencySet) {
				CookbookDependency dependency = (CookbookDependency) object;
				Cookbook sourceCookbook = dependency.getCookbookBySourceId();
				if (!cookbookSet.contains(sourceCookbook) && 
						sourceCookbook.getCategory().equals(Parameters.COOKBOOK_CATEGORY_PACKAGE)) {
					cookbookSet.add(sourceCookbook);
					findDependencyCookbook(cookbookSet, sourceCookbook);
				}
			}
		}
	}
	
	/**
	 * Execute on remote chef server
	 * @param yamlContent
	 * @param chefEngine
	 * @param fileName
	 * @return
	 */
	private int execute(String yamlContent, ChefEngine chefEngine, String fileName) {
		// Connect to remote server
		RemoteProcess remoteProcess = new RemoteProcess(chefEngine.getHostname(), 
				chefEngine.getUsername(), chefEngine.getPassword());

		// Execute
		return remoteProcess.executeCommand("cd chef-repo && echo " + "'" + yamlContent + "'" + " > yaml/" + fileName + " && " + 
				"/root/.rbenv/shims/spiceweasel -e yaml/" + fileName);	
	}

}
