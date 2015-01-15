package org.greenpipe.workspace.util;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.greenpipe.workspace.attributes.bean.Attributes;
import org.greenpipe.workspace.model.bean.Vm;
import org.greenpipe.workspace.model.bean.Workspace;
import org.greenpipe.workspace.model.dao.VmHome;

public class VmUpdater {
	
	public static void saveVMs(Workspace workspace, String username, String password, 
			String state, String image, Date createTime, Date updateTime) {
		Set<Vm> vms = new HashSet<Vm>();
		for (int i = 1; i <= workspace.getVmNumber(); i++) {
			Vm vm = new Vm();
			saveVM(vm, workspace, getHostname(workspace, i), username, password, 
					state, image, createTime, updateTime);
			vms.add(vm);
		}
		WorkspaceUpdater.updateWorkspace(workspace, vms);
	}

	public static void saveVM(Vm vm, Workspace workspace, String hostname, String username, String password, 
			 String state, String image, Date createTime, Date updateTime) {
		vm.setWorkspace(workspace);
		vm.setProvider(workspace.getProvider());
		vm.setSize(workspace.getVmSize());
		vm.setHostname(hostname);
		vm.setUsername(username);
		vm.setPassword(password);
		vm.setState(state);
		vm.setImage(image);
		vm.setCreateTime(createTime);
		vm.setUpdateTime(updateTime);
		
		VmHome vmHome = new VmHome();
		vmHome.persist(vm);
	}
	
	public static void updateVMs(Set<Vm> vms, String state, Date updateTime) {
		for(Vm vm : vms) {
			updateVM(vm, state, updateTime);
		}
	}
	
	public static void updateVM(Vm vm, String state, Date updateTime) {
		vm.setState(state);
		vm.setUpdateTime(updateTime);
		
		VmHome vmHome = new VmHome();
		vmHome.attachDirty(vm);
	}
	
	public static void updateVM(Vm vm, Date updateTime) {
		vm.setUpdateTime(updateTime);
		
		VmHome vmHome = new VmHome();
		vmHome.attachDirty(vm);
	}
	
	public static String getHostname(Workspace workspace, int id) {
		return getClustername(workspace) + id;
	}
	
	public static String getClustername(Workspace workspace) {
		String clustername = workspace.getProvider() + "-" + Parameters.CLUSTERNAME + workspace.getId() + "-"
				+ workspace.getVmNumber() + "-";
		return clustername;
	}
	
}
