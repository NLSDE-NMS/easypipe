package org.greenpipe.workspace.handler;

import org.greenpipe.workspace.attributes.bean.Attributes;

public class ChefEngine {
	private int id;
	private String provider;
	private String hostname;
	private String username;
	private String password;
	private Attributes attributes;
	
	public ChefEngine() {}

	public ChefEngine(int id, String provider, String hostname,
			String username, String password, Attributes attributes) {
		this.id = id;
		this.provider = provider;
		this.hostname = hostname;
		this.username = username;
		this.password = password;
		this.attributes = attributes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Attributes getAttributes() {
		return attributes;
	}

	public void setAttributes(Attributes attributes) {
		this.attributes = attributes;
	}
	
}
