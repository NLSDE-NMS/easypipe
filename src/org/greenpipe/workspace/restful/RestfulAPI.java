package org.greenpipe.workspace.restful;

import java.io.IOException;
import java.net.MalformedURLException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.List;

import org.greenpipe.workspace.attributes.bean.Attributes;
import org.greenpipe.workspace.states.RunState;

public interface RestfulAPI {
	
	public boolean hasEnoughCores(String vmSize, int vmNumber, Attributes attributes);
	
	public RunState startSingleVM(String vmName, Attributes attributes);
	
	public RunState stopSingleVM(String vmName, Attributes attributes);
	
	public RunState restartSingleVM(String vmName, Attributes attributes);
	
	public RunState startVMs(List<String> vmNameList, Attributes attributes);
	
	public RunState stopVMs(List<String> vmNameList, Attributes attributes);
	
}
