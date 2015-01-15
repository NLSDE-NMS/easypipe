package org.greenpipe.workspace.vm.yaml;

import java.util.List;

import org.greenpipe.workspace.attributes.bean.Attributes;
import org.greenpipe.workspace.attributes.bean.AzureAttributes;
import org.greenpipe.workspace.model.bean.Workspace;
import org.greenpipe.workspace.util.Parameters;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

public class AzureYAML extends YAML {
	private static YAML yaml = new AzureYAML();
	
	private AzureYAML() {}
	
	public static YAML getSingleInstance() {
		return yaml;
	}
	
	@Override
	public String fillNodesCreation(Workspace workspace, Attributes attributes,
			List recipes, String hostname, String username, String password) {
		// TODO Auto-generated method stub
		STGroup stGroup = new STGroupFile(Parameters.YAML_DEPLOY_TEMPLATE_FILE);
		ST st = stGroup.getInstanceOf(Parameters.YAML_AZURE_CREATE_TAG);
		st.add("workspace", workspace);
		st.add("attributes", (AzureAttributes) attributes);
		st.add("recipes", recipes);
		st.add("hostname", hostname);
		st.add("username", username);
		st.add("password", password);
		return st.render();
	}

	@Override
	public String fillNodeBootstrap(String hostname, String domain,
			String username, String password, List recipes) {
		// TODO Auto-generated method stub
		STGroup stGroup = new STGroupFile(Parameters.YAML_DEPLOY_TEMPLATE_FILE);
		ST st = stGroup.getInstanceOf(Parameters.YAML_AZURE_BOOTSTRAP_TAG);
		st.add("hostname", hostname);
		st.add("domain", domain);
		st.add("username", username);
		st.add("password", password);
		st.add("recipes", recipes);
		return st.render();
	}

	@Override
	public String fillNodeDeletion(String hostname) {
		// TODO Auto-generated method stub
		STGroup stGroup = new STGroupFile(Parameters.YAML_DEPLOY_TEMPLATE_FILE);
		ST st = stGroup.getInstanceOf(Parameters.YAML_AZURE_DELETE_TAG);
		st.add("hostname", hostname);
		return st.render();
	}

}
