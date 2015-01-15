package org.greenpipe.workspace.util;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.greenpipe.workspace.model.bean.User;
import org.greenpipe.workspace.model.bean.Vm;
import org.greenpipe.workspace.model.bean.Workspace;
import org.greenpipe.workspace.model.bean.WorkspaceCookbookAssociation;
import org.greenpipe.workspace.model.dao.WorkspaceCookbookAssociationHome;
import org.greenpipe.workspace.model.dao.WorkspaceHome;

public class WorkspaceUpdater {
	
	public static Workspace getWorkspace(String workspaceID) {
		WorkspaceHome workspaceHome = new WorkspaceHome();

		Workspace workspace = workspaceHome.findById(Integer.parseInt(workspaceID));
		return workspace;
	}
	
	public static Workspace getWorkspaceByDefault(User user) {
		WorkspaceHome workspaceHome = new WorkspaceHome();
		
		Workspace workspaceExample = new Workspace();
		workspaceExample.setUser(user);
		List<Workspace> workspaceList = workspaceHome.findByExampleUser(workspaceExample);
		
		for (Workspace ws : workspaceList) {
			if(ws.getIsDefault().equals(Parameters.DEFAULT_WORKSPACE_YES)) {
				return ws;
			}
		}
		
		return null;
	}
	
	public static void saveWorkspace(Workspace workspace, User user, String state, String message, 
			String isDefault, Date createTime, Date updateTime) {
		workspace.setUser(user);
		workspace.setState(state);
		workspace.setMessage(message);
		workspace.setCreateTime(createTime);
		workspace.setUpdateTime(updateTime);
		workspace.setIsDefault(isDefault);
		
		WorkspaceHome workspaceHome = new WorkspaceHome();
		workspaceHome.persist(workspace);
		
		System.out.println(message);
	}
	
	public static void updateWorkspace(Workspace workspace, String state, String message, Date updateTime) {
		workspace.setState(state);
		workspace.setMessage(message);
		workspace.setUpdateTime(updateTime);
		
		WorkspaceHome workspaceHome = new WorkspaceHome();
		workspaceHome.attachDirty(workspace);
		
		System.out.println(message);
	}
	
	public static void updateWorkspace(Workspace workspace, String message, Date date) {
		workspace.setMessage(message);
		workspace.setUpdateTime(date);
		
		WorkspaceHome workspaceHome = new WorkspaceHome();
		workspaceHome.attachDirty(workspace);
		
		System.out.println(message);
	}
	
	public static void updateWorkspace(Workspace workspace, int serverID) {
		workspace.setServer(serverID);
		
		WorkspaceHome workspaceHome = new WorkspaceHome();
		workspaceHome.attachDirty(workspace);
	}
	
	public static void updateWorkspace(Workspace workspace, Set<Vm> vms) {
		workspace.setVms(vms);
	}
	
	public static void updateWorkspace(Set<WorkspaceCookbookAssociation> associations) {
		WorkspaceCookbookAssociationHome associationHome = new WorkspaceCookbookAssociationHome();
		for (WorkspaceCookbookAssociation association : associations) {
			associationHome.persist(association);
		}
	}

	public static void updateWorkspace(Workspace workspace, String isDefault) {
		// TODO Auto-generated method stub
		workspace.setIsDefault(isDefault);
		
		WorkspaceHome workspaceHome = new WorkspaceHome();
		workspaceHome.attachDirty(workspace);
	}
	
	public static String changeDefaultWorkspace(Workspace workspace, User user) {
		// TODO Auto-generated method stub
		String workspaceID = workspace.getId().toString();
		WorkspaceHome workspaceHome = new WorkspaceHome();
		
		Workspace workspaceExample = new Workspace();
		workspaceExample.setUser(user);
		List<Workspace> workspaceList = workspaceHome.findByExampleUser(workspaceExample);
		
		for (Workspace ws : workspaceList) {
			if(!ws.getId().toString().equals(workspaceID) && ws.getIsDefault().equals(Parameters.DEFAULT_WORKSPACE_YES)) {
				ws.setIsDefault(Parameters.DEFAULT_WORKSPACE_NO);
				workspaceHome.attachDirty(ws);
			}
		}
		
		workspace.setIsDefault(Parameters.DEFAULT_WORKSPACE_YES);
		workspaceHome.attachDirty(workspace);
		
		return "Set default workspace successful";
	}
	
}
