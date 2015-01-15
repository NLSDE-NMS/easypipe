package org.greenpipe.workspace.entry;

import java.util.Date;

import org.greenpipe.workspace.handler.HandlerContainer;
import org.greenpipe.workspace.model.bean.User;
import org.greenpipe.workspace.model.bean.Workspace;
import org.greenpipe.workspace.states.WorkspaceState;
import org.greenpipe.workspace.util.Parameters;
import org.greenpipe.workspace.util.WorkspaceUpdater;
import org.greenpipe.workspace.util.WorkspaceXmlParser;

public class ServicesInDetail {
	private static final String CREATE_METHOD = "create";
	private static final String BOOTSTRAP_METHOD = "bootstrap";

	/**
	 * Create or bootstrap a workspace in detail
	 * @param workspaceXML
	 * @param username
	 * @param password
	 * @param method
	 * @param bootstrapMethod 
	 * @return
	 */
	public static String createOrBootstrapInDetail(String workspaceID, String workspaceXML, String username, 
			String password, String method) {

		String status = null;
		Workspace workspace = new Workspace();

		// Prepare workspace
		User user = UserValidation.validateUser(username, password);
		if(user != null) {
			if(method.equals(CREATE_METHOD)) {
				workspace = WorkspaceXmlParser.parseWorkspaceXML(workspaceXML);
				// Check parse result
				if(workspace.getState().equals(WorkspaceState.PARSE_SUCCESSFUL.name())) {			
					// Set properties and add workspace into database
					WorkspaceUpdater.saveWorkspace(workspace, user, WorkspaceState.CREATE_NEW.name(), 
							WorkspaceState.CREATE_NEW.name(), Parameters.DEFAULT_WORKSPACE_NO, 
							new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
					// Add workspace-cookbook's association into database
					WorkspaceUpdater.updateWorkspace(workspace.getWorkspaceCookbookAssociations());
					// Add workspace to create-queue
					HandlerContainer handlerContainer = HandlerContainer.getSingleInstance();
					handlerContainer.addToCreateQueue(workspace);	
				}
			} else {
				workspace = WorkspaceUpdater.getWorkspace(workspaceID);
				if(workspace.getUser().getId().equals(user.getId())) {
					WorkspaceXmlParser.parseWorkspaceXML(workspace, workspaceXML);
					// Check parse result
					if(workspace.getState().equals(WorkspaceState.PARSE_SUCCESSFUL.name())) {
						WorkspaceUpdater.updateWorkspace(workspace, WorkspaceState.BOOTSTRAP_NEW.name(), 
								WorkspaceState.BOOTSTRAP_NEW.name(), new Date(System.currentTimeMillis()));
						// Add workspace-cookbook's association into database
						WorkspaceUpdater.updateWorkspace(workspace.getWorkspaceCookbookAssociations());
						// Add workspace to create or bootstrap-queue
						HandlerContainer handlerContainer = HandlerContainer.getSingleInstance();
						handlerContainer.addToBootstrapQueue(workspace);
					}
				} else {
					workspace.setState(WorkspaceState.BOOTSTRAP_FAILED.name());
					workspace.setMessage("You have no bootstrap right on workspace " + workspaceID);
				}
			}
		} else {
			workspace.setState(WorkspaceState.LOGIN_FAILED.name());
			workspace.setMessage("Incorrect username or password");
		}

		// Wait for workspace deployment's completion
		boolean terminate = false;
		while(!terminate) {
			switch(WorkspaceState.valueOf(workspace.getState())) {
			case LOGIN_FAILED:
			case PARSE_FAILED:
			case RESOURCES_FAILED:
			case CREATE_FAILED:
			case DRM_FAILED:
			case BOOTSTRAP_FAILED:
			case BOOTSTRAP_SUCCESSFUL:
				if(method.equals(CREATE_METHOD) && 
						workspace.getState().equals(WorkspaceState.BOOTSTRAP_SUCCESSFUL.name())) {
					WorkspaceUpdater.changeDefaultWorkspace(workspace, user);
				}
				status = Reporter.getStatus(workspace);
				System.out.println("Workspace deployment finished !");
				terminate = true;
				break;
			default:
				break;
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return status;
	}

	/**
	 * Bootstrap a workspace for running a workflow in detail
	 * @param workspaceXML
	 * @param username
	 * @param password
	 * @return
	 */
	public static String bootstrapForRunInDetail(String workspaceXML, String username, String password) {

		String status = null;
		Workspace workspace = new Workspace();

		// Prepare workspace
		User user = UserValidation.validateUser(username, password);
		if(user != null) {
			Workspace target = WorkspaceUpdater.getWorkspaceByDefault(user);
			if(target != null) {
				workspace = target;
				WorkspaceXmlParser.parseWorkspaceXML(workspace, workspaceXML);
				// Check parse result
				if(workspace.getState().equals(WorkspaceState.PARSE_SUCCESSFUL.name())) {
					WorkspaceUpdater.updateWorkspace(workspace, WorkspaceState.BOOTSTRAP_NEW.name(), 
							WorkspaceState.BOOTSTRAP_NEW.name(), new Date(System.currentTimeMillis()));
					// Add workspace-cookbook's association into database
					WorkspaceUpdater.updateWorkspace(workspace.getWorkspaceCookbookAssociations());
					// Add workspace to create or bootstrap-queue
					HandlerContainer handlerContainer = HandlerContainer.getSingleInstance();
					handlerContainer.addToBootstrapQueue(workspace);
				}
			} else {
				workspace.setState(WorkspaceState.BOOTSTRAP_FAILED.name());
				workspace.setMessage("No available default workspace");
			}
		} else {
			workspace.setState(WorkspaceState.LOGIN_FAILED.name());
			workspace.setMessage("Incorrect username or password");
		}

		// Wait for workspace deployment's completion
		boolean terminate = false;
		while(!terminate) {
			switch(WorkspaceState.valueOf(workspace.getState())) {
			case LOGIN_FAILED:
			case PARSE_FAILED:
			case RESOURCES_FAILED:
			case CREATE_FAILED:
			case DRM_FAILED:
			case BOOTSTRAP_FAILED:
			case BOOTSTRAP_SUCCESSFUL:
				status = Reporter.getStatus(workspace);
				System.out.println("Workspace deployment finished !");
				terminate = true;
				break;
			default:
				break;
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return status;
	}

	/**
	 * Delete a workspace in detail
	 * @param workspaceID
	 * @param username
	 * @param password
	 * @return
	 */
	public static String deleteInDetail(String workspaceID, String username, String password) {

		String status = null;
		Workspace workspace = new Workspace();

		// Prepare workspace
		User user = UserValidation.validateUser(username, password);
		if(user != null) {
			// Get workspace by ID
			workspace = WorkspaceUpdater.getWorkspace(workspaceID);
			if(workspace.getUser().getId().equals(user.getId())) {
				// Update status of workspace
				WorkspaceUpdater.updateWorkspace(workspace, WorkspaceState.DELETE_NEW.name(), 
						WorkspaceState.DELETE_NEW.name(), new Date(System.currentTimeMillis()));
				// Get handler container
				HandlerContainer handlerContainer = HandlerContainer.getSingleInstance();
				// Add workspace to delete-queue
				handlerContainer.addToDeleteQueue(workspace);	
			} else {
				workspace.setState(WorkspaceState.DELETE_FAILED.name());
				workspace.setMessage("You have no delete right on workspace " + workspaceID);
			}
		} else {
			workspace.setState(WorkspaceState.LOGIN_FAILED.name());
			workspace.setMessage("Incorrect username or password");
		}

		// Wait for workspace deployment's completion
		boolean terminate = false;
		while(!terminate) {
			switch(WorkspaceState.valueOf(workspace.getState())) {
			case LOGIN_FAILED:
			case DELETE_FAILED:
			case DELETE_SUCCESSFUL:
				status = Reporter.getStatus(workspace);
				System.out.println("Workspace deletion finished !");
				terminate = true;
				break;
			default:
				break;
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return status;
	}

	/**
	 * List workspace of a user in detail
	 * @param username
	 * @param password
	 * @return
	 */
	public static String listInDetail(String username, String password) {

		String workspaces = null;
		String state = null;
		String message = null;

		User user = UserValidation.validateUser(username, password);
		if(user != null) {
			state = WorkspaceState.LOGIN_SUCCESSFUL.name();
			message = "login successfully";
		} else {
			state = WorkspaceState.LOGIN_FAILED.name();
			message = "Incorrect username or password";
		}

		workspaces = Reporter.getWorkspaces(user, state, message);
		System.out.println(workspaces);

		return workspaces;
	}

	/**
	 * Start VMs in detail
	 * @param workspaceID
	 * @param username
	 * @param password
	 * @return
	 */
	public static String startInDetail(String workspaceID, String username,
			String password) {
		String status = null;
		Workspace workspace = new Workspace();

		// Prepare workspace
		User user = UserValidation.validateUser(username, password);
		if(user != null) {
			// Get workspace by ID
			workspace = WorkspaceUpdater.getWorkspace(workspaceID);
			if(workspace.getUser().getId().equals(user.getId())) {
				// Update status of workspace
				WorkspaceUpdater.updateWorkspace(workspace, WorkspaceState.START_NEW.name(), 
						WorkspaceState.START_NEW.name(), new Date(System.currentTimeMillis()));
				// Get handler container
				HandlerContainer handlerContainer = HandlerContainer.getSingleInstance();
				// Add workspace to delete-queue
				handlerContainer.addToStartQueue(workspace);	
			} else {
				workspace.setState(WorkspaceState.START_FAILED.name());
				workspace.setMessage("You have no start right on workspace " + workspaceID);
			}
		} else {
			workspace.setState(WorkspaceState.LOGIN_FAILED.name());
			workspace.setMessage("Incorrect username or password");
		}

		// Wait for workspace deployment's completion
		boolean terminate = false;
		while(!terminate) {
			switch(WorkspaceState.valueOf(workspace.getState())) {
			case LOGIN_FAILED:
			case START_FAILED:
			case START_SUCCESSFUL:
				status = Reporter.getStatus(workspace);
				System.out.println("Workspace start finished !");
				terminate = true;
				break;
			default:
				break;
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return status;
	}

	/**
	 * Stop VMs in detail
	 * @param workspaceID
	 * @param username
	 * @param password
	 * @return
	 */
	public static String stopInDetail(String workspaceID, String username,
			String password) {
		// TODO Auto-generated method stub
		String status = null;
		Workspace workspace = new Workspace();

		// Prepare workspace
		User user = UserValidation.validateUser(username, password);
		if(user != null) {
			// Get workspace by ID
			workspace = WorkspaceUpdater.getWorkspace(workspaceID);
			if(workspace.getUser().getId().equals(user.getId())) {
				// Update status of workspace
				WorkspaceUpdater.updateWorkspace(workspace, WorkspaceState.STOP_NEW.name(), 
						WorkspaceState.STOP_NEW.name(), new Date(System.currentTimeMillis()));
				// Get handler container
				HandlerContainer handlerContainer = HandlerContainer.getSingleInstance();
				// Add workspace to delete-queue
				handlerContainer.addToStopQueue(workspace);	
			} else {
				workspace.setState(WorkspaceState.STOP_FAILED.name());
				workspace.setMessage("You have no stop right on workspace " + workspaceID);
			}
		} else {
			workspace.setState(WorkspaceState.LOGIN_FAILED.name());
			workspace.setMessage("Incorrect username or password");
		}

		// Wait for workspace deployment's completion
		boolean terminate = false;
		while(!terminate) {
			switch(WorkspaceState.valueOf(workspace.getState())) {
			case LOGIN_FAILED:
			case STOP_FAILED:
			case STOP_SUCCESSFUL:
				status = Reporter.getStatus(workspace);
				System.out.println("Workspace stop finished !");
				terminate = true;
				break;
			default:
				break;
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return status;
	}

	/**
	 * Restart VMs in detail
	 * @param workspaceID
	 * @param username
	 * @param password
	 * @return
	 */
	public static String restartInDetail(String workspaceID, String username,
			String password) {
		// TODO Auto-generated method stub
		String status = null;
		Workspace workspace = new Workspace();

		// Prepare workspace
		User user = UserValidation.validateUser(username, password);
		if(user != null) {
			// Get workspace by ID
			workspace = WorkspaceUpdater.getWorkspace(workspaceID);
			if(workspace.getUser().getId().equals(user.getId())) {
				// Update status of workspace
				WorkspaceUpdater.updateWorkspace(workspace, WorkspaceState.RESTART_NEW.name(), 
						WorkspaceState.RESTART_NEW.name(), new Date(System.currentTimeMillis()));
				// Get handler container
				HandlerContainer handlerContainer = HandlerContainer.getSingleInstance();
				// Add workspace to delete-queue
				handlerContainer.addToRestartQueue(workspace);	
			} else {
				workspace.setState(WorkspaceState.RESTART_FAILED.name());
				workspace.setMessage("You have no restart right on workspace " + workspaceID);
			}
		} else {
			workspace.setState(WorkspaceState.LOGIN_FAILED.name());
			workspace.setMessage("Incorrect username or password");
		}

		// Wait for workspace deployment's completion
		boolean terminate = false;
		while(!terminate) {
			switch(WorkspaceState.valueOf(workspace.getState())) {
			case LOGIN_FAILED:
			case RESTART_FAILED:
			case RESTART_SUCCESSFUL:
				status = Reporter.getStatus(workspace);
				System.out.println("Workspace restart finished !");
				terminate = true;
				break;
			default:
				break;
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return status;
	}

	/**
	 * Set a workspace as 'default' workspace in detail
	 * @param workspaceID
	 * @param username
	 * @param password
	 * @return
	 */
	public static String setDefaultWorkspaceInDetail(String workspaceID,
			String username, String password) {
		// TODO Auto-generated method stub
		String status = "Set default workspace failed";
		Workspace workspace = new Workspace();

		// Prepare workspace
		User user = UserValidation.validateUser(username, password);
		if(user != null) {
			// Get workspace by ID
			workspace = WorkspaceUpdater.getWorkspace(workspaceID);
			if(workspace.getUser().getId().equals(user.getId())) {
				switch(WorkspaceState.valueOf(workspace.getState())) {
				case BOOTSTRAP_SUCCESSFUL:
				case START_SUCCESSFUL:
				case RESTART_SUCCESSFUL:
					status = WorkspaceUpdater.changeDefaultWorkspace(workspace, user);
					break;
				default:
					break;
				}
			} else {
				workspace.setState(WorkspaceState.SET_DEFAULT_FAILED.name());
				status = "You have no set-default right on workspace " + workspaceID;
			}
		} else {
			workspace.setState(WorkspaceState.LOGIN_FAILED.name());
			status = "Incorrect username or password";
		}

		return status;
	}


}
