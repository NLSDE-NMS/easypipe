package org.greenpipe.workspace.restful;

import java.io.IOException;
import java.net.MalformedURLException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.HashMap;
import java.util.List;

import org.greenpipe.workspace.attributes.bean.Attributes;
import org.greenpipe.workspace.attributes.bean.AzureAttributes;
import org.greenpipe.workspace.restful.azure.Manager;
import org.greenpipe.workspace.states.RunState;

public class AzureRestfulAPI implements RestfulAPI {
	private static RestfulAPI restful = new AzureRestfulAPI();
	private HashMap<String, Integer> hashMap;

	private AzureRestfulAPI() {
		hashMap = new HashMap<String, Integer>();
		hashMap.put("Small", 1);
		hashMap.put("Medium", 2);
		hashMap.put("Large", 4);
		hashMap.put("ExtraLarge", 8);
	}
	
	public static RestfulAPI getSingleInstance() {
		return restful;
	}
	
	/**
	 * Check if there are enough resources
	 */
	@Override
	public boolean hasEnoughCores(String vmSize, int vmNumber, Attributes attributes) {
		// TODO Auto-generated method stub
		int requestCores = hashMap.get(vmSize) * vmNumber;
		int availableCores = Manager.getAvailableCores( (AzureAttributes) attributes );
		
		if(requestCores <= availableCores) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Try to start a VM
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyStoreException 
	 * @throws KeyManagementException 
	 * @throws UnrecoverableKeyException 
	 */
	@Override
	public RunState startSingleVM(String vmName, Attributes attributes) {
		// TODO Auto-generated method stub
		return Manager.startSingleVM(vmName, (AzureAttributes) attributes);
	}

	/**
	 * Try to stop a VM
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyStoreException 
	 * @throws KeyManagementException 
	 * @throws UnrecoverableKeyException 
	 */
	@Override
	public RunState stopSingleVM(String vmName, Attributes attributes) {
		// TODO Auto-generated method stub
		return Manager.stopSingleVM(vmName, (AzureAttributes) attributes);
	}

	/**
	 * Try to restart a VM
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyStoreException 
	 * @throws KeyManagementException 
	 * @throws UnrecoverableKeyException 
	 */
	@Override
	public RunState restartSingleVM(String vmName, Attributes attributes) {
		// TODO Auto-generated method stub
		return Manager.restartSingleVM(vmName, (AzureAttributes) attributes);
	}

	/**
	 * Try to start a number of VMs
	 */
	@Override
	public RunState startVMs(List<String> vmNameList, Attributes attributes) {
		// TODO Auto-generated method stub
		return Manager.startVMs(vmNameList, (AzureAttributes) attributes);
	}

	/**
	 * Try to stop a number of VMs
	 */
	@Override
	public RunState stopVMs(List<String> vmNameList, Attributes attributes) {
		// TODO Auto-generated method stub
		return Manager.stopVMs(vmNameList, (AzureAttributes) attributes);
	}

}
