package org.greenpipe.workspace.entry;

import org.greenpipe.workspace.util.Parameters;

/**
 * Every time, when a user send an request to this web service, 
 * axis container will launch a new instance of this Provisioner
 * which can be treated as a new thread to deal with this request.
 * @author jinchao
 * 
 */
public class Provisioner {
	private static final String configFilePath = Parameters.CONFIGURATION_FILE_PATH;
	private static final String CREATE_METHOD = "create";
	private static final String BOOTSTRAP_METHOD = "bootstrap";
	private static final String DEFAULT_WORKSPACE_ID = "0";

	static {
		Initialization.initHandlers(configFilePath);
	}

	public Provisioner() {}

	/**
	 * Create a workspace
	 * @param workspaceXML
	 * @param username
	 * @param password
	 * @return
	 */
	public String create(String workspaceXML, String username, String password) {
		return ServicesInDetail.createOrBootstrapInDetail(DEFAULT_WORKSPACE_ID, workspaceXML, username, password, CREATE_METHOD);
	}

	/**
	 * Bootstrap a workspace
	 * @param workspaceXML
	 * @param username
	 * @param password
	 * @return
	 */
	public String bootstrap(String workspaceID, String workspaceXML, String username, String password) {
		return ServicesInDetail.createOrBootstrapInDetail(workspaceID, workspaceXML, username, password, BOOTSTRAP_METHOD);
	}
	
	/**
	 * Bootstrap a workspace for running a workflow
	 * @param workspaceXML
	 * @param username
	 * @param password
	 * @return
	 */
	public String bootstrapForRun(String workspaceXML, String username, String password) {
		return ServicesInDetail.bootstrapForRunInDetail(workspaceXML, username, password);
	}

	/**
	 * Delete a workspace
	 * @param workspaceID
	 * @param username
	 * @param password
	 * @return
	 */
	public String delete(String workspaceID, String username, String password) {
		return ServicesInDetail.deleteInDetail(workspaceID, username, password);
	}

	/**
	 * List workspaces of a user
	 * @param username
	 * @param password
	 * @return
	 */
	public String list(String username, String password) {
		return ServicesInDetail.listInDetail(username, password);
	}

	/**
	 * Start VMs of a workspace
	 * @param workspaceID
	 * @param username
	 * @param password
	 * @return
	 */
	public String start(String workspaceID, String username, String password) {
		return ServicesInDetail.startInDetail(workspaceID, username, password);

	}

	/**
	 * Stop VMs of a workspace
	 * @param workspaceID
	 * @param username
	 * @param password
	 * @return
	 */
	public String stop(String workspaceID, String username, String password) {
		return ServicesInDetail.stopInDetail(workspaceID, username, password);
	}

	/**
	 * Restart VMs of a workspace
	 * @param workspaceID
	 * @param username
	 * @param password
	 * @return
	 */
	public String restart(String workspaceID, String username, String password) {
		return ServicesInDetail.restartInDetail(workspaceID, username, password);
	}

	/**
	 * Set a workspace as 'default' workspace to run jobs
	 * @param workspaceID
	 * @param username
	 * @param password
	 * @return
	 */
	public String setDefaultWorkspace(String workspaceID, String username, String password) {
		return ServicesInDetail.setDefaultWorkspaceInDetail(workspaceID, username, password);
	}
	
}