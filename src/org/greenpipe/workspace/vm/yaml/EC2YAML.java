package org.greenpipe.workspace.vm.yaml;

import java.util.List;

import org.greenpipe.workspace.attributes.bean.Attributes;
import org.greenpipe.workspace.model.bean.Workspace;

public class EC2YAML extends YAML {
	private static YAML yaml = new EC2YAML();
	
	private EC2YAML() {}
	
	public static YAML getSingleInstance() {
		return yaml;
	}
	
	@Override
	public String fillNodesCreation(Workspace workspace, Attributes attributes,
			List recipes, String hostname, String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String fillNodeBootstrap(String hostname, String domain,
			String username, String password, List recipes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String fillNodeDeletion(String hostname) {
		// TODO Auto-generated method stub
		return null;
	}

}
