package org.greenpipe.workspace.handler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.greenpipe.workspace.attributes.bean.Attributes;
import org.greenpipe.workspace.model.bean.Cookbook;
import org.greenpipe.workspace.model.bean.Vm;
import org.greenpipe.workspace.model.bean.Workspace;
import org.greenpipe.workspace.model.bean.WorkspaceCookbookAssociation;
import org.greenpipe.workspace.model.dao.VmHome;
import org.greenpipe.workspace.states.RunState;
import org.greenpipe.workspace.states.VmState;
import org.greenpipe.workspace.states.WorkspaceState;
import org.greenpipe.workspace.util.Parameters;
import org.greenpipe.workspace.util.VmUpdater;
import org.greenpipe.workspace.util.WorkspaceUpdater;
import org.greenpipe.workspace.vm.common.CloudManager;

public class Action {

	/**
	 * Create a workspace
	 * @param workspace
	 * @param chefEngine
	 */
	public static void createWorkspace(Workspace workspace, ChefEngine chefEngine) {
		// Create VMs
		RunState createStatus = createVMs(workspace, chefEngine);
		if(createStatus != RunState.CREATE_SUCCESSFUL)
			return;
		
		// Install DRMs if necessary
		RunState drmStatus = installDRMs(workspace, chefEngine);
		if(drmStatus != RunState.DRM_SUCCESSFUL)
			return;
		
		// Bootstrap VMs if necessary
		bootstrapVMs(workspace, chefEngine);
	}

	/**
	 * Bootstrap a workspace
	 * @param workspace
	 * @param chefEngine
	 */
	public static void bootstrapWorkspace(Workspace workspace, ChefEngine chefEngine) {
		// Install DRMs if necessary
		RunState drmStatus = installDRMs(workspace, chefEngine);
		if(drmStatus != RunState.DRM_SUCCESSFUL)
			return;
		
		// Bootstrap VMs
		bootstrapVMs(workspace, chefEngine);
	}
	
	/**
	 * Delete a workspace
	 * @param workspace
	 * @param chefEngine
	 */
	public static void deleteWorkspace(Workspace workspace, ChefEngine chefEngine) {
		// Delete VMs
		deleteVMs(workspace, chefEngine);
	}
	
	public static void listWorkspace(Workspace workspace, ChefEngine chefEngine) {
		
	}
	
	/**
	 * Start a workspace
	 * @param workspace
	 * @param chefEngine
	 */
	public static void startWorkspace(Workspace workspace, ChefEngine chefEngine) {
		// Start VMs
		startVMs(workspace, chefEngine);
	}
	
	/**
	 * Stop a workspace
	 * @param workspace
	 * @param chefEngine
	 */
	public static void stopWorkspace(Workspace workspace, ChefEngine chefEngine) {
		// Stop VMs
		stopVMs(workspace, chefEngine);
	}
	
	/**
	 * Restart a workspace
	 * @param workspace
	 * @param chefEngine
	 */
	public static void restartWorkspace(Workspace workspace, ChefEngine chefEngine) {
		// Restart VMs
		restartVMs(workspace, chefEngine);
	}
	
	/**
	 * Create VMs in this workspace through the tied chef engine
	 * @param workspace
	 * @param chefEngine
	 */
	private static RunState createVMs(Workspace workspace, ChefEngine chefEngine) {
		// Get cloud manager
		CloudManager cloudManager = CloudManager.getSingleInstance();
		
		// Update status of workspace
		WorkspaceUpdater.updateWorkspace(workspace, chefEngine.getId());
		WorkspaceUpdater.updateWorkspace(workspace, WorkspaceState.CREATE_INITIALIZING.name(), 
				WorkspaceState.CREATE_INITIALIZING.name(), new Date(System.currentTimeMillis()));

		// Save VMs
		VmUpdater.saveVMs(workspace, Parameters.NORMAL_USERNAME, Parameters.PASSWORD, 
				VmState.INITIALIZING.name(), chefEngine.getAttributes().getImage(),
				new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
		
		// States and messages
		String workspaceState = null;
		String workspaceMessage = null;
		String vmState = null;
		
		// Create VMs in this workspace
		RunState status = cloudManager.create(workspace, chefEngine);
		switch(status) {
		case RESOURCES_FAILED:
			workspaceState = WorkspaceState.RESOURCES_FAILED.name();
			workspaceMessage = "Not enough resources";
			vmState = VmState.FAILED.name();
			break;
		case YAML_FAILED:
			workspaceState = WorkspaceState.CREATE_FAILED.name();
			workspaceMessage = "YAML service not found for this cloud provider";
			vmState = VmState.FAILED.name();
			break;
		case CREATE_FAILED:
			workspaceState = WorkspaceState.CREATE_FAILED.name();
			workspaceMessage = WorkspaceState.CREATE_FAILED.name();
			vmState = VmState.FAILED.name();
			break;
		default:
			workspaceState = WorkspaceState.CREATE_SUCCESSFUL.name();
			workspaceMessage = WorkspaceState.CREATE_SUCCESSFUL.name();
			vmState = VmState.SUCCESSFUL.name();
			break;
		}
		
		// Update status of workspace and VMs
		WorkspaceUpdater.updateWorkspace(workspace, workspaceState, workspaceMessage, new Date(System.currentTimeMillis()));
		VmUpdater.updateVMs(workspace.getVms(), vmState, new Date(System.currentTimeMillis()));
		
		return status;
	}

	/**
	 * Bootstrap VMs in this workspace through the tied chef engine
	 * @param workspace
	 * @param chefEngine
	 */
	private static RunState bootstrapVMs(Workspace workspace, ChefEngine chefEngine) {
		// Get cloud manager
		CloudManager cloudManager = CloudManager.getSingleInstance();
		
		// Update status of workspace and VMs
		WorkspaceUpdater.updateWorkspace(workspace, WorkspaceState.BOOTSTRAP_INITIALIZING.name(), 
				WorkspaceState.BOOTSTRAP_INITIALIZING.name(), new Date(System.currentTimeMillis()));
		VmUpdater.updateVMs(workspace.getVms(), VmState.BOOTSTRAPPING.name(), new Date(System.currentTimeMillis()));
		
		// States and messages
		String workspaceState = null;
		String workspaceMessage = null;
		String vmState = null;

		// Bootstrap VMs in this workspace
		RunState status = cloudManager.bootstrap(workspace, chefEngine);
		switch(status) {
		case YAML_FAILED:
			workspaceState = WorkspaceState.BOOTSTRAP_FAILED.name();
			workspaceMessage = "YAML service not found for this cloud provider";
			vmState = VmState.SUCCESSFUL.name();
			break;
		case BOOTSTRAP_FAILED:
			workspaceState = WorkspaceState.BOOTSTRAP_FAILED.name();
			workspaceMessage = WorkspaceState.BOOTSTRAP_FAILED.name();
			vmState = VmState.SUCCESSFUL.name();
			break;
		default:
			workspaceState = WorkspaceState.BOOTSTRAP_SUCCESSFUL.name();
			workspaceMessage = WorkspaceState.BOOTSTRAP_SUCCESSFUL.name();
			vmState = VmState.SUCCESSFUL.name();
			break;
		}
		
		// Update status of workspace and VMs
		WorkspaceUpdater.updateWorkspace(workspace, workspaceState, workspaceMessage, new Date(System.currentTimeMillis()));
		VmUpdater.updateVMs(workspace.getVms(), vmState, new Date(System.currentTimeMillis()));
		
		return status;
	}

	/**
	 * Delete VMs in this workspace through the tied chef engine
	 * @param workspace
	 * @param chefEngine
	 */
	private static RunState deleteVMs(Workspace workspace, ChefEngine chefEngine) {
		// Get cloud manager
		CloudManager cloudManager = CloudManager.getSingleInstance();
		
		// Update status of workspace and VMs
		WorkspaceUpdater.updateWorkspace(workspace, WorkspaceState.DELETE_INITIALIZING.name(), 
				WorkspaceState.DELETE_INITIALIZING.name(), new Date(System.currentTimeMillis()));
		VmUpdater.updateVMs(workspace.getVms(), VmState.DELETING.name(), new Date(System.currentTimeMillis()));
		
		// States and messages
		String workspaceState = null;
		String workspaceMessage = null;
		String vmState = null;
		
		RunState status = cloudManager.delete(workspace, chefEngine);
		switch(status) {
		case YAML_FAILED:
			workspaceState = WorkspaceState.DELETE_FAILED.name();
			workspaceMessage = "YAML service not found for this cloud provider";
			vmState = VmState.SUCCESSFUL.name();
			break;
		case DELETE_FAILED:
			workspaceState = WorkspaceState.DELETE_FAILED.name();
			workspaceMessage = WorkspaceState.DELETE_FAILED.name();
			vmState = VmState.FAILED.name();
			break;
		default:
			workspaceState = WorkspaceState.DELETE_SUCCESSFUL.name();
			workspaceMessage = WorkspaceState.DELETE_SUCCESSFUL.name();
			vmState = VmState.DELETED.name();
			break;
		}
		
		// Update status of workspace and VMs
		WorkspaceUpdater.updateWorkspace(workspace, workspaceState, workspaceMessage, new Date(System.currentTimeMillis()));
		VmUpdater.updateVMs(workspace.getVms(), vmState, new Date(System.currentTimeMillis()));
		
		return status;
	}

	/**
	 * List VMs in this workspace
	 * TODO Action for 'list'
	 * @param workspace
	 * @param chefEngine
	 * @return
	 */
	private static int listVMs(Workspace workspace, ChefEngine chefEngine) {
		return 0;
	}

	/**
	 * Start VMs in this workspace
	 * @param workspace
	 * @param chefEngine
	 * @return
	 */
	private static RunState startVMs(Workspace workspace, ChefEngine chefEngine) {
		// Get cloud manager
		CloudManager cloudManager = CloudManager.getSingleInstance();
		
		// Update status of workspace and VMs
		WorkspaceUpdater.updateWorkspace(workspace, WorkspaceState.START_INITIALIZING.name(), 
				WorkspaceState.START_INITIALIZING.name(), new Date(System.currentTimeMillis()));
		VmUpdater.updateVMs(workspace.getVms(), VmState.STARTING.name(), new Date(System.currentTimeMillis()));
		
		// States and messages
		String workspaceState = null;
		String workspaceMessage = null;
		String vmState = null;
		
		RunState status = cloudManager.start(workspace, chefEngine);
		switch(status) {
		case YAML_FAILED:
			workspaceState = WorkspaceState.START_FAILED.name();
			workspaceMessage = "YAML service not found for this cloud provider";
			vmState = VmState.STOPED.name();
			break;
		case START_FAILED:
			workspaceState = WorkspaceState.START_FAILED.name();
			workspaceMessage = WorkspaceState.START_FAILED.name();
			vmState = VmState.FAILED.name();
			break;
		default:
			workspaceState = WorkspaceState.START_SUCCESSFUL.name();
			workspaceMessage = WorkspaceState.START_SUCCESSFUL.name();
			vmState = VmState.STARTED.name();
			break;
		}
		
		// Update status of workspace and VMs
		WorkspaceUpdater.updateWorkspace(workspace, workspaceState, workspaceMessage, new Date(System.currentTimeMillis()));
		VmUpdater.updateVMs(workspace.getVms(), vmState, new Date(System.currentTimeMillis()));
		
		return status;
	}

	/**
	 * Stop VMs in this workspace
	 * @param workspace
	 * @param chefEngine
	 * @return
	 */
	private static RunState stopVMs(Workspace workspace, ChefEngine chefEngine) {
		// Get cloud manager
		CloudManager cloudManager = CloudManager.getSingleInstance();
		
		// Update status of workspace and VMs
		WorkspaceUpdater.updateWorkspace(workspace, WorkspaceState.STOP_INITIALIZING.name(), 
				WorkspaceState.STOP_INITIALIZING.name(), new Date(System.currentTimeMillis()));
		VmUpdater.updateVMs(workspace.getVms(), VmState.STOPPING.name(), new Date(System.currentTimeMillis()));
		
		// States and messages
		String workspaceState = null;
		String workspaceMessage = null;
		String vmState = null;
		
		RunState status = cloudManager.stop(workspace, chefEngine);
		switch(status) {
		case YAML_FAILED:
			workspaceState = WorkspaceState.STOP_FAILED.name();
			workspaceMessage = "YAML service not found for this cloud provider";
			vmState = VmState.STARTED.name();
			break;
		case STOP_FAILED:
			workspaceState = WorkspaceState.STOP_FAILED.name();
			workspaceMessage = WorkspaceState.STOP_FAILED.name();
			vmState = VmState.FAILED.name();
			break;
		default:
			workspaceState = WorkspaceState.STOP_SUCCESSFUL.name();
			workspaceMessage = WorkspaceState.STOP_SUCCESSFUL.name();
			vmState = VmState.STOPED.name();
			break;
		}
		
		// Update status of workspace and VMs
		WorkspaceUpdater.updateWorkspace(workspace, workspaceState, workspaceMessage, new Date(System.currentTimeMillis()));
		VmUpdater.updateVMs(workspace.getVms(), vmState, new Date(System.currentTimeMillis()));
		
		return status;
	}
	
	/**
	 * Restart VMs in this workspace
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
	private static RunState restartVMs(Workspace workspace, ChefEngine chefEngine) {
		// Get cloud manager
		CloudManager cloudManager = CloudManager.getSingleInstance();
		
		// Update status of workspace and VMs
		WorkspaceUpdater.updateWorkspace(workspace, WorkspaceState.RESTART_INITIALIZING.name(), 
				WorkspaceState.RESTART_INITIALIZING.name(), new Date(System.currentTimeMillis()));
		VmUpdater.updateVMs(workspace.getVms(), VmState.RESTARTING.name(), new Date(System.currentTimeMillis()));
		
		// States and messages
		String workspaceState = null;
		String workspaceMessage = null;
		String vmState = null;
		
		RunState status = cloudManager.restart(workspace, chefEngine);
		switch(status) {
		case YAML_FAILED:
			workspaceState = WorkspaceState.RESTART_FAILED.name();
			workspaceMessage = "YAML service not found for this cloud provider";
			vmState = VmState.STARTED.name();
			break;
		case RESTART_FAILED:
			workspaceState = WorkspaceState.RESTART_FAILED.name();
			workspaceMessage = WorkspaceState.RESTART_FAILED.name();
			vmState = VmState.FAILED.name();
			break;
		default:
			workspaceState = WorkspaceState.RESTART_SUCCESSFUL.name();
			workspaceMessage = WorkspaceState.RESTART_SUCCESSFUL.name();
			vmState = VmState.RESTARTED.name();
			break;
		}
		
		// Update status of workspace and VMs
		WorkspaceUpdater.updateWorkspace(workspace, workspaceState, workspaceMessage, new Date(System.currentTimeMillis()));
		VmUpdater.updateVMs(workspace.getVms(), vmState, new Date(System.currentTimeMillis()));
		
		return status;
	}
	
	/**
	 * Try to install DRMs that requested
	 * @param workspace
	 * @param chefEngine
	 * @return
	 */
	private static RunState installDRMs(Workspace workspace, ChefEngine chefEngine) {
		Set workspaceCookbookAssociations = workspace.getWorkspaceCookbookAssociations();
		for (Object object : workspaceCookbookAssociations) {
			WorkspaceCookbookAssociation association = (WorkspaceCookbookAssociation) object;
			Cookbook cookbook = association.getCookbook();
			// Check 'environment'-level cookbooks
			if (cookbook != null && cookbook.getCategory().equals(Parameters.COOKBOOK_CATEGORY_ENVIRONMENT)) {
				RunState status = installDRM(workspace, chefEngine, cookbook.getName());
				if(status != RunState.DRM_SUCCESSFUL)
					return status;
			}
		}
		return RunState.DRM_SUCCESSFUL;
	}
	
	/**
	 * Install a specific DRM
	 * @param workspace
	 * @param chefEngine
	 * @param drmName
	 * @return
	 */
	private static RunState installDRM(Workspace workspace, ChefEngine chefEngine, String drmName) {
		// Get cloud manager
		CloudManager cloudManager = CloudManager.getSingleInstance();
		
		// Update status of workspace and VMs
		WorkspaceUpdater.updateWorkspace(workspace, WorkspaceState.DRM_INITIALIZING.name(), 
				"begin to install " + drmName, new Date(System.currentTimeMillis()));
		VmUpdater.updateVMs(workspace.getVms(), VmState.BOOTSTRAPPING.name(), new Date(System.currentTimeMillis()));
		
		// States and messages
		String workspaceState = "";
		String workspaceMessage = "";
		String vmState = "";
		
		// Install DRMs
		RunState status = cloudManager.installDRM(workspace, chefEngine, drmName);
		switch(status) {
		case YAML_FAILED:
			workspaceState = WorkspaceState.DRM_FAILED.name();
			workspaceMessage = "YAML service not found for this cloud provider";
			vmState = VmState.SUCCESSFUL.name();
			break;
		case DRM_FAILED:
			workspaceState = WorkspaceState.DRM_FAILED.name();
			workspaceMessage = "Error happened when installing " + drmName;
			vmState = VmState.SUCCESSFUL.name();
			break;
		default:
			workspaceState = WorkspaceState.DRM_SUCCESSFUL.name();
			workspaceMessage = drmName + " is successfully installed";
			vmState = VmState.SUCCESSFUL.name();
			break;
		}
		
		// Update status of workspace and VMs
		WorkspaceUpdater.updateWorkspace(workspace, workspaceState, workspaceMessage, new Date(System.currentTimeMillis()));
		VmUpdater.updateVMs(workspace.getVms(), vmState, new Date(System.currentTimeMillis()));
		
		return status;
	}

}
