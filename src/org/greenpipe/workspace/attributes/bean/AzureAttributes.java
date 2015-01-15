package org.greenpipe.workspace.attributes.bean;

public class AzureAttributes implements Attributes {
	/**
	 * Attributes for azure
	 */
	private String image;
	private String service;
	private String subnet;
	private String storage;
	private String availability;
	private String affinity;
	private String domain;
	// Restful API
	private String subscription;
	private String keyStore;
	private String version;
	private String url;
	private String master;
	
	public AzureAttributes() {}

	public AzureAttributes(String image, String service, String subnet,
			String storage, String availability, String affinity,
			String domain, String subscription, String keyStore,
			String version, String url, String master) {
		this.image = image;
		this.service = service;
		this.subnet = subnet;
		this.storage = storage;
		this.availability = availability;
		this.affinity = affinity;
		this.domain = domain;
		this.subscription = subscription;
		this.keyStore = keyStore;
		this.version = version;
		this.url = url;
		this.master = master;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getSubnet() {
		return subnet;
	}

	public void setSubnet(String subnet) {
		this.subnet = subnet;
	}

	public String getStorage() {
		return storage;
	}

	public void setStorage(String storage) {
		this.storage = storage;
	}

	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	public String getAffinity() {
		return affinity;
	}

	public void setAffinity(String affinity) {
		this.affinity = affinity;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getSubscription() {
		return subscription;
	}

	public void setSubscription(String subscription) {
		this.subscription = subscription;
	}

	public String getKeyStore() {
		return keyStore;
	}

	public void setKeyStore(String keyStore) {
		this.keyStore = keyStore;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMaster() {
		return master;
	}

	public void setMaster(String master) {
		this.master = master;
	}
	
}
