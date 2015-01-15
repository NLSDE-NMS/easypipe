package org.greenpipe.workspace.restful;

import java.util.HashMap;

public class RestfulAPIFactory {
	private static HashMap<String, RestfulAPI> restfuls = new HashMap<String, RestfulAPI>();
	
	static {
		restfuls.put("azure", AzureRestfulAPI.getSingleInstance());
		restfuls.put("ec2", EC2RestfulAPI.getSingleInstance());
		restfuls.put("openstack", OpenstackRestfulAPI.getSingleIntance());
	}
	
	public static RestfulAPI getRestfulAPI(String key) {
		if(restfuls.containsKey(key)) {
			return restfuls.get(key);
		} else {
			return null;
		}
	}
}
