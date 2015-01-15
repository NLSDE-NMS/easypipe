package org.greenpipe.workspace.vm.yaml;

import java.util.HashMap;

public class YAMLFactory {
	private static HashMap<String, YAML> yamlServices = new HashMap<String, YAML>();
	
	static {
		yamlServices.put("azure", AzureYAML.getSingleInstance());
		yamlServices.put("ec2", EC2YAML.getSingleInstance());
		yamlServices.put("openstack", OpenstackYAML.getSingleInstance());
	}
	
	public static YAML getYAMLService(String key) {
		if(yamlServices.containsKey(key)) {
			return yamlServices.get(key);
		} else {
			return null;
		}
	}
}
