package org.greenpipe.workspace.restful.azure;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.greenpipe.workspace.util.CommonXmlParserUtility;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class PropertyParser {
	
	public static Property parseAzureInfo(String azureInfo) {
		InputStream inputStream = CommonXmlParserUtility.StringToInputStream(azureInfo);
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		Document document = null;

		try {
			db = dbf.newDocumentBuilder();
			document = db.parse(inputStream);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Property property= new Property();

		Element root = document.getDocumentElement();

		// Set SubscriptionID
		String SubscriptionID = CommonXmlParserUtility.getTextByTagName(root, "SubscriptionID");
		property.setSubscriptionID(SubscriptionID);

		// Set SubscriptionName
		String SubscriptionName = CommonXmlParserUtility.getTextByTagName(root, "SubscriptionName");
		property.setSubscriptionName(SubscriptionName);

		// Set SubscriptionStatus
		String SubscriptionStatus = CommonXmlParserUtility.getTextByTagName(root, "SubscriptionStatus");
		property.setSubscriptionStatus(SubscriptionStatus);

		// Set AccountAdminLiveEmailId
		String AccountAdminLiveEmailId = CommonXmlParserUtility.getTextByTagName(root, "AccountAdminLiveEmailId");
		property.setAccountAdminliveEmailId(AccountAdminLiveEmailId);

		// Set AccountAdminLiveEmailId
		String ServiceAdminLiveEmailId = CommonXmlParserUtility.getTextByTagName(root, "ServiceAdminLiveEmailId");
		property.setServiceAdminliveEmailId(ServiceAdminLiveEmailId);

		// Set MaxCoreCount
		String MaxCoreCount = CommonXmlParserUtility.getTextByTagName(root, "MaxCoreCount");
		property.setMaxCoreCount(Integer.parseInt(MaxCoreCount));

		// Set MaxStorageAccounts
		String MaxStorageAccounts = CommonXmlParserUtility.getTextByTagName(root, "MaxStorageAccounts");
		property.setMaxStorageAccounts(Integer.parseInt(MaxStorageAccounts));

		// Set MaxHostedServices
		String MaxHostedServices = CommonXmlParserUtility.getTextByTagName(root, "MaxHostedServices");
		property.setMaxHostedSurvices(Integer.parseInt(MaxHostedServices));

		// Set CurrentCoreCount
		String CurrentCoreCount = CommonXmlParserUtility.getTextByTagName(root, "CurrentCoreCount");
		property.setCurrentCoreCount(Integer.parseInt(CurrentCoreCount));

		// Set CurrentHostedServices
		String CurrentHostedServices = CommonXmlParserUtility.getTextByTagName(root, "CurrentHostedServices");
		property.setCurrentHostedServices(Integer.parseInt(CurrentHostedServices));

		// Set CurrentStorageAccounts
		String CurrentStorageAccounts = CommonXmlParserUtility.getTextByTagName(root, "CurrentStorageAccounts");
		property.setCurrentStorageAcounts(Integer.parseInt(CurrentStorageAccounts));

		// Set MaxVirtualNetworkSites
		String MaxVirtualNetworkSites = CommonXmlParserUtility.getTextByTagName(root, "MaxVirtualNetworkSites");
		property.setMaxVirtualNetworkSites(Integer.parseInt(MaxVirtualNetworkSites));

		// Set CurrentVirtualNetworkSites
		String CurrentVirtualNetworkSites = CommonXmlParserUtility.getTextByTagName(root, "CurrentVirtualNetworkSites");
		property.setCurrentVirtualNetworkSites(Integer.parseInt(CurrentVirtualNetworkSites));

		// Set MaxLocalNetworkSites
		String MaxLocalNetworkSites = CommonXmlParserUtility.getTextByTagName(root, "MaxLocalNetworkSites");
		property.setMaxLocalNetworkSites(Integer.parseInt(MaxLocalNetworkSites));

		// Set MaxDnsServers
		String MaxDnsServers = CommonXmlParserUtility.getTextByTagName(root, "MaxDnsServers");
		property.setMaxDnsServers(Integer.parseInt(MaxDnsServers));

		return property;
	}

}
