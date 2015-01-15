package org.greenpipe.workspace.entry;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.greenpipe.workspace.model.bean.User;
import org.greenpipe.workspace.model.bean.Vm;
import org.greenpipe.workspace.model.bean.Workspace;
import org.greenpipe.workspace.model.dao.WorkspaceHome;
import org.greenpipe.workspace.util.Parameters;
import org.greenpipe.workspace.util.VmUpdater;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

public class Reporter {

	@SuppressWarnings("unchecked")
	public static String getWorkspaces(User user, String state, String message) {

		String workspaces = "";
		STGroup stGroup = new STGroupFile(Parameters.REPORT_STATUS_LOCATION);
		ST st = null;

		// 1. Set <workspaces>
		st = stGroup.getInstanceOf(Parameters.REPORT_WORKSPACES_BEGIN_TAG);
		workspaces = workspaces + st.render() + "\n";
		
		// 2. Set state and message
		st = stGroup.getInstanceOf(Parameters.REPORT_WORKSPACES_STATUS_TAG);
		st.add("state", state);
		st.add("message", message);
		workspaces = workspaces + st.render() + "\n";
		
		// 3. Set workspaces
		if(user != null) {
			Workspace workspace = new Workspace();
			workspace.setUser(user);
			WorkspaceHome workspaceHome = new WorkspaceHome();
			List<Workspace> workspaceList = workspaceHome.findByExampleUser(workspace);
			for (Workspace ws : workspaceList) {
				workspaces = workspaces + getStatus(ws) + "\n";	
			}
		}
		
		// 4. Set </workspaces>
		st = stGroup.getInstanceOf(Parameters.REPORT_WORKSPACES_END_TAG);
		workspaces = workspaces + st.render() + "\n";

		return workspaces;

	}
	
	public static String getStatus(Workspace workspace) {
		String master = VmUpdater.getHostname(workspace, 1);
		String username = Parameters.NORMAL_USERNAME;
		String password = Parameters.PASSWORD;

		STGroup stGroup = new STGroupFile(Parameters.REPORT_STATUS_LOCATION);
		ST st = stGroup.getInstanceOf(Parameters.REPORT_STATUS_TAG);
		st.add("workspace", workspace);
		st.add("master", master);
		st.add("username", username);
		st.add("password", password);
		return st.render();		
	}

}
