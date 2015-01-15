package org.greenpipe.workspace.restful.azure;

public class Property {
	private String subscriptionID;
	private String subscriptionName;
	private String subscriptionStatus;
	private String accountAdminliveEmailId;
	private String serviceAdminliveEmailId;
	private int maxCoreCount;
	private int maxStorageAccounts;
	private int maxHostedSurvices;
	private int currentCoreCount;
	private int currentHostedServices;
	private int currentStorageAcounts;
	private int maxVirtualNetworkSites;
	private int currentVirtualNetworkSites;
	private int maxLocalNetworkSites;
	private int maxDnsServers;
	private String responseMessage;
	private int responseCode;
	
	public Property() {}

	public Property(String subscriptionID, String subscriptionName,
			String subscriptionStatus, String accountAdminliveEmailId,
			String serviceAdminliveEmailId, int maxCoreCount,
			int maxStorageAccounts, int maxHostedSurvices,
			int currentCoreCount, int currentHostedServices,
			int currentStorageAcounts, int maxVirtualNetworkSites,
			int currentVirtualNetworkSites, int maxLocalNetworkSites,
			int maxDnsServers, String responseMessage, int responseCode) {
		this.subscriptionID = subscriptionID;
		this.subscriptionName = subscriptionName;
		this.subscriptionStatus = subscriptionStatus;
		this.accountAdminliveEmailId = accountAdminliveEmailId;
		this.serviceAdminliveEmailId = serviceAdminliveEmailId;
		this.maxCoreCount = maxCoreCount;
		this.maxStorageAccounts = maxStorageAccounts;
		this.maxHostedSurvices = maxHostedSurvices;
		this.currentCoreCount = currentCoreCount;
		this.currentHostedServices = currentHostedServices;
		this.currentStorageAcounts = currentStorageAcounts;
		this.maxVirtualNetworkSites = maxVirtualNetworkSites;
		this.currentVirtualNetworkSites = currentVirtualNetworkSites;
		this.maxLocalNetworkSites = maxLocalNetworkSites;
		this.maxDnsServers = maxDnsServers;
		this.responseMessage = responseMessage;
		this.responseCode = responseCode;
	}

	public String getSubscriptionID() {
		return subscriptionID;
	}

	public void setSubscriptionID(String subscriptionID) {
		this.subscriptionID = subscriptionID;
	}

	public String getSubscriptionName() {
		return subscriptionName;
	}

	public void setSubscriptionName(String subscriptionName) {
		this.subscriptionName = subscriptionName;
	}

	public String getSubscriptionStatus() {
		return subscriptionStatus;
	}

	public void setSubscriptionStatus(String subscriptionStatus) {
		this.subscriptionStatus = subscriptionStatus;
	}

	public String getAccountAdminliveEmailId() {
		return accountAdminliveEmailId;
	}

	public void setAccountAdminliveEmailId(String accountAdminliveEmailId) {
		this.accountAdminliveEmailId = accountAdminliveEmailId;
	}

	public String getServiceAdminliveEmailId() {
		return serviceAdminliveEmailId;
	}

	public void setServiceAdminliveEmailId(String serviceAdminliveEmailId) {
		this.serviceAdminliveEmailId = serviceAdminliveEmailId;
	}

	public int getMaxCoreCount() {
		return maxCoreCount;
	}

	public void setMaxCoreCount(int maxCoreCount) {
		this.maxCoreCount = maxCoreCount;
	}

	public int getMaxStorageAccounts() {
		return maxStorageAccounts;
	}

	public void setMaxStorageAccounts(int maxStorageAccounts) {
		this.maxStorageAccounts = maxStorageAccounts;
	}

	public int getMaxHostedSurvices() {
		return maxHostedSurvices;
	}

	public void setMaxHostedSurvices(int maxHostedSurvices) {
		this.maxHostedSurvices = maxHostedSurvices;
	}

	public int getCurrentCoreCount() {
		return currentCoreCount;
	}

	public void setCurrentCoreCount(int currentCoreCount) {
		this.currentCoreCount = currentCoreCount;
	}

	public int getCurrentHostedServices() {
		return currentHostedServices;
	}

	public void setCurrentHostedServices(int currentHostedServices) {
		this.currentHostedServices = currentHostedServices;
	}

	public int getCurrentStorageAcounts() {
		return currentStorageAcounts;
	}

	public void setCurrentStorageAcounts(int currentStorageAcounts) {
		this.currentStorageAcounts = currentStorageAcounts;
	}

	public int getMaxVirtualNetworkSites() {
		return maxVirtualNetworkSites;
	}

	public void setMaxVirtualNetworkSites(int maxVirtualNetworkSites) {
		this.maxVirtualNetworkSites = maxVirtualNetworkSites;
	}

	public int getCurrentVirtualNetworkSites() {
		return currentVirtualNetworkSites;
	}

	public void setCurrentVirtualNetworkSites(int currentVirtualNetworkSites) {
		this.currentVirtualNetworkSites = currentVirtualNetworkSites;
	}

	public int getMaxLocalNetworkSites() {
		return maxLocalNetworkSites;
	}

	public void setMaxLocalNetworkSites(int maxLocalNetworkSites) {
		this.maxLocalNetworkSites = maxLocalNetworkSites;
	}

	public int getMaxDnsServers() {
		return maxDnsServers;
	}

	public void setMaxDnsServers(int maxDnsServers) {
		this.maxDnsServers = maxDnsServers;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	
}
