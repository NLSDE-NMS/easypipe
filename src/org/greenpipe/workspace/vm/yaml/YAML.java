package org.greenpipe.workspace.vm.yaml;

import java.util.List;

import org.greenpipe.workspace.attributes.bean.Attributes;
import org.greenpipe.workspace.attributes.bean.AzureAttributes;
import org.greenpipe.workspace.model.bean.Workspace;
import org.greenpipe.workspace.util.Parameters;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

public abstract class YAML {
	
	/**
	 * Add 'cookbooks' into YAML file
	 * @param cookbooks
	 * @return
	 */
	public String fillCookbooks(List cookbooks) {
		STGroup stGroup = new STGroupFile(Parameters.YAML_DEPLOY_TEMPLATE_FILE);
		ST st = stGroup.getInstanceOf(Parameters.YAML_COOKBOOK_TAG);
		st.add("cookbooks", cookbooks);
		return st.render();
	}
	
	/**
	 * Add 'nodes' tag into YAML file
	 * @return
	 */
	public String fillNodesTag() {
		STGroup stGroup = new STGroupFile(Parameters.YAML_DEPLOY_TEMPLATE_FILE);
		ST st = stGroup.getInstanceOf(Parameters.YAML_NODES_TAG);
		return st.render();
	}
	
	/**
	 * Add 'knife' tag into YAML file
	 * @return
	 */
	public String fillKnifeTag() {
		STGroup stGroup = new STGroupFile(Parameters.YAML_DEPLOY_TEMPLATE_FILE);
		ST st = stGroup.getInstanceOf(Parameters.YAML_KNIFE_TAG);
		return st.render();
	}
	
	/**
	 * Add 'create nodes' part into YAML file
	 * @param workspace
	 * @param attributes
	 * @param recipes
	 * @param hostname
	 * @param username
	 * @param password
	 * @return
	 */
	public abstract String fillNodesCreation(Workspace workspace, Attributes attributes, List recipes,
			String hostname, String username, String password);
	
	/**
	 * Add 'bootstrap node' part into YAML file
	 * @param hostname
	 * @param domain
	 * @param username
	 * @param password
	 * @param recipes
	 * @return
	 */
	public abstract String fillNodeBootstrap(String hostname, String domain, String username, 
			String password, List recipes);
	
	/**
	 * Add 'delete node' part into YAML file
	 * @param hostname
	 * @return
	 */
	public abstract String fillNodeDeletion(String hostname);
	
}
