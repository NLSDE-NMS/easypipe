package org.greenpipe.workspace.vm.drm;

import java.util.HashMap;

public class DRMFactory {

	public static final HashMap<String, DRM> drms = new HashMap<String, DRM>();

	static {
		drms.put("hadoop", Hadoop.getSingleInstance());
		drms.put("torque", Torque.getSingleInstance());
	}

	public static DRM getDRM(String key) {
		if(drms.containsKey(key)) {
			return drms.get(key);
		} else {
			return null;
		}
	}
	
}
