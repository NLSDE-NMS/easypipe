package org.greenpipe.workspace.restful.azure;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.List;

import org.greenpipe.workspace.attributes.bean.AzureAttributes;
import org.greenpipe.workspace.states.RunState;
import org.greenpipe.workspace.util.Parameters;

public class Manager {
	public static final int OK_CODE_NUMBER = 202;

	/**
	 * Get current available cores
	 * @param attributes
	 * @return
	 */
	public static int getAvailableCores(AzureAttributes attributes) {
		String response = null;

		try {
			String subscriptionID = attributes.getSubscription();
			String keyStorePath = attributes.getKeyStore();
			String versionTime = attributes.getVersion();
			String url = String.format(attributes.getUrl() + "/%s", subscriptionID);
			String keyStorePassword = Parameters.PASSWORD;
			response = Connection.processGetRequest(new URL(url), keyStorePath, keyStorePassword, versionTime);              
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Property property = PropertyParser.parseAzureInfo(response);

		int totalCores = property.getMaxCoreCount();
		int usedCores = property.getCurrentCoreCount();
		int availableCores = totalCores - usedCores;
		return availableCores;	
	}

	/**
	 * Start single VM through Restful API
	 * @param vmName
	 * @param attributes
	 * @return
	 * @throws UnrecoverableKeyException
	 * @throws KeyManagementException
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static RunState startSingleVM(String vmName, AzureAttributes attributes) {

		String url = String.format(attributes.getUrl() + "/%s/services/hostedservices/%s/deployments/%s/roleinstances/%s/Operations", 
				attributes.getSubscription(), attributes.getService(), attributes.getMaster(), vmName);

		String startRequestBody = "<StartRoleOperation xmlns=\"http://schemas.microsoft.com/windowsazure\" " 
				+ "xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\"><OperationType>StartRoleOperation</OperationType></StartRoleOperation>";

		Property property = null;
		try {
			property = Connection.processPostRequest(new URL(url), startRequestBody.getBytes(), "application/xml", 
					attributes.getKeyStore(), Parameters.PASSWORD);
		} catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Start VM: " + vmName + ", " + property.getResponseCode() + ", " + property.getResponseMessage());
		if(property.getResponseCode() == OK_CODE_NUMBER) {
			return RunState.START_SUCCESSFUL;
		} else {
			return RunState.START_FAILED;
		}
	}

	/**
	 * Stop single VM through Restful API
	 * @param vmName
	 * @param attributes
	 * @return
	 * @throws UnrecoverableKeyException
	 * @throws KeyManagementException
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static RunState stopSingleVM(String vmName, AzureAttributes attributes) {

		String url = String.format(attributes.getUrl() + "/%s/services/hostedservices/%s/deployments/%s/roleinstances/%s/Operations", 
				attributes.getSubscription(), attributes.getService(), attributes.getMaster(), vmName);

		String stopRequestBody = "<ShutdownRoleOperation xmlns=\"http://schemas.microsoft.com/windowsazure\" " 
				+ "xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\"><OperationType>ShutdownRoleOperation</OperationType>" 
				+ "<PostShutdownAction>%s</PostShutdownAction></ShutdownRoleOperation>";
		stopRequestBody = String.format(stopRequestBody, "StoppedDeallocated");

		Property property = null;
		try {
			property = Connection.processPostRequest(new URL(url), stopRequestBody.getBytes(), "application/xml", 
					attributes.getKeyStore(), Parameters.PASSWORD);
		} catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Stop VM: " + vmName + ", " + property.getResponseCode() + ", " + property.getResponseMessage());
		if(property.getResponseCode() == OK_CODE_NUMBER) {
			return RunState.STOP_SUCCESSFUL;
		} else {
			return RunState.STOP_FAILED;
		}
	}

	/**
	 * Restart single VM through Restful API
	 * @param vmName
	 * @param attributes
	 * @return
	 * @throws UnrecoverableKeyException
	 * @throws KeyManagementException
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static RunState restartSingleVM(String vmName, AzureAttributes attributes) {

		String url = String.format("https://management.core.windows.net/%s/services/hostedservices/%s/deployments/%s/roleinstances/%s/Operations", 
				attributes.getSubscription(), attributes.getService(), attributes.getMaster(), vmName);

		String restartRequestBody = "<RestartRoleOperation xmlns=\"http://schemas.microsoft.com/windowsazure\" " 
				+ "xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\"><OperationType>RestartRoleOperation</OperationType></RestartRoleOperation>";

		Property property = null;
		try {
			property = Connection.processPostRequest(new URL(url), restartRequestBody.getBytes(), "application/xml", 
					attributes.getKeyStore(), Parameters.PASSWORD);
		} catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Restart VM: " + property.getResponseCode() + ", " + property.getResponseMessage());
		if(property.getResponseCode() == OK_CODE_NUMBER) {
			return RunState.RESTART_SUCCESSFUL;
		} else {
			return RunState.RESTART_FAILED;
		}
	}

	/**
	 * Start a number of VMs through Restful API
	 * @param vmNameList
	 * @param attributes
	 * @return
	 */
	public static RunState startVMs(List<String> vmNameList, AzureAttributes attributes) {
		String url = String.format(attributes.getUrl() + "/%s/services/hostedservices/%s/deployments/%s/roles/Operations", 
				attributes.getSubscription(), attributes.getService(), attributes.getMaster());

		String startRequestBody = "<StartRolesOperation xmlns=\"http://schemas.microsoft.com/windowsazure\" " 
				+ "xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\"><OperationType>StartRolesOperation</OperationType>" 
				+ "<Roles>";
		for(String vmName : vmNameList) {
			startRequestBody = startRequestBody + "<Name>" + vmName + "</Name>";
		}
		startRequestBody = startRequestBody + "</Roles></StartRolesOperation>";

		Property property = null;
		try {
			property = Connection.processPostRequest(new URL(url), startRequestBody.getBytes(), "application/xml", 
					attributes.getKeyStore(), Parameters.PASSWORD);
		} catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Start VMs: " + property.getResponseCode() + ", " + property.getResponseMessage());
		if(property.getResponseCode() == OK_CODE_NUMBER) {
			return RunState.START_SUCCESSFUL;
		} else {
			return RunState.START_FAILED;
		}	
	}

	/**
	 * Stop a number of VMs through Restful API
	 * @param vmNameList
	 * @param attributes
	 * @return
	 */
	public static RunState stopVMs(List<String> vmNameList, AzureAttributes attributes) {
		String url = String.format(attributes.getUrl() + "/%s/services/hostedservices/%s/deployments/%s/roles/Operations", 
				attributes.getSubscription(), attributes.getService(), attributes.getMaster());

		String stopRequestBody = "<ShutdownRolesOperation xmlns=\"http://schemas.microsoft.com/windowsazure\" " 
				+ "xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\"><OperationType>ShutdownRolesOperation</OperationType>" 
				+ "<Roles>";
		for(String vmName : vmNameList) {
			stopRequestBody = stopRequestBody + "<Name>" + vmName + "</Name>";
		}
		stopRequestBody = stopRequestBody + "</Roles><PostShutdownAction>%s</PostShutdownAction></ShutdownRolesOperation>";
		stopRequestBody = String.format(stopRequestBody, "StoppedDeallocated");
		
		Property property = null;
		try {
			property = Connection.processPostRequest(new URL(url), stopRequestBody.getBytes(), "application/xml", 
					attributes.getKeyStore(), Parameters.PASSWORD);
		} catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Stop VMs: " + property.getResponseCode() + ", " + property.getResponseMessage());
		if(property.getResponseCode() == OK_CODE_NUMBER) {
			return RunState.STOP_SUCCESSFUL;
		} else {
			return RunState.STOP_FAILED;
		}	
	}

}
