package org.greenpipe.workspace.vm.drm;

import org.greenpipe.workspace.handler.ChefEngine;
import org.greenpipe.workspace.model.bean.Workspace;
import org.greenpipe.workspace.states.RunState;
import org.greenpipe.workspace.vm.common.RemoteProcess;

public abstract class DRM {
	
	/**
	 * Install DRM on this workspace
	 * @param workspace
	 * @param chefEngine
	 * @return
	 */
	public abstract RunState installDRM(Workspace workspace, ChefEngine chefEngine);
	
	/**
	 * Execute on remote chef server
	 * @param yamlContent
	 * @param chefEngine
	 * @param fileName
	 * @return
	 */
	protected int execute(String yamlContent, ChefEngine chefEngine, String fileName) {
		// Connect to remote server
		RemoteProcess remoteProcess = new RemoteProcess(chefEngine.getHostname(), 
				chefEngine.getUsername(), chefEngine.getPassword());

		// Execute
		return remoteProcess.executeCommand("cd chef-repo && echo " + "'" + yamlContent + "'" + " > yaml/" + fileName + " && " + 
				"/root/.rbenv/shims/spiceweasel -e yaml/" + fileName);	
	}
	
}
