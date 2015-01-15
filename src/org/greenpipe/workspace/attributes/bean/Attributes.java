package org.greenpipe.workspace.attributes.bean;

import java.util.List;

import org.greenpipe.workspace.model.bean.Workspace;

/**
 * Attributes for launch VMs on specific Clouds
 * @author jinchao
 *
 */
public interface Attributes {
	
	public String getImage();
	
	public String getDomain();
	
}
