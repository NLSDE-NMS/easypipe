package org.greenpipe.workspace.restful;

import java.util.List;

import org.greenpipe.workspace.attributes.bean.Attributes;
import org.greenpipe.workspace.states.RunState;

public class EC2RestfulAPI implements RestfulAPI {
	private static RestfulAPI restful = new EC2RestfulAPI();
	
	private EC2RestfulAPI() {}
	
	public static RestfulAPI getSingleInstance() {
		return restful;
	}

	@Override
	public boolean hasEnoughCores(String vmSize, int vmNumber,
			Attributes attributes) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public RunState startSingleVM(String vmName, Attributes attributes) {
		return null;
		// TODO Auto-generated method stub
	}

	@Override
	public RunState stopSingleVM(String vmName, Attributes attributes) {
		return null;
		// TODO Auto-generated method stub
	}

	@Override
	public RunState restartSingleVM(String vmName, Attributes attributes) {
		return null;
		// TODO Auto-generated method stub
	}

	@Override
	public RunState startVMs(List<String> vmNameList, Attributes attributes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RunState stopVMs(List<String> vmNameList, Attributes attributes) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
