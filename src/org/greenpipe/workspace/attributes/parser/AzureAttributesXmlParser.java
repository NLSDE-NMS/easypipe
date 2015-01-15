package org.greenpipe.workspace.attributes.parser;

import org.greenpipe.workspace.attributes.bean.Attributes;
import org.greenpipe.workspace.attributes.bean.AzureAttributes;
import org.greenpipe.workspace.util.CommonXmlParserUtility;
import org.w3c.dom.Element;

public class AzureAttributesXmlParser implements AttributesXmlParser {

	private static AttributesXmlParser parser = new AzureAttributesXmlParser();

	private AzureAttributesXmlParser() {}

	public static AttributesXmlParser getSingleInstance() {
		return parser;
	}

	@Override
	public Attributes parseXml(Element element) {
		// TODO Auto-generated method stub
		AzureAttributes attributes = new AzureAttributes();
		
		// Set image
		String image = CommonXmlParserUtility.getTextByTagName(element, "image");
		attributes.setImage(image);
		
		// Set service
		String service = CommonXmlParserUtility.getTextByTagName(element, "service");
		attributes.setService(service);
		
		// Set subnet
		String subnet = CommonXmlParserUtility.getTextByTagName(element, "subnet");
		attributes.setSubnet(subnet);
		
		// Set storage
		String storage = CommonXmlParserUtility.getTextByTagName(element, "storage");
		attributes.setStorage(storage);
		
		// Set availability
		String availability = CommonXmlParserUtility.getTextByTagName(element, "availability");
		attributes.setAvailability(availability);
		
		// Set affinity
		String affinity = CommonXmlParserUtility.getTextByTagName(element, "affinity");
		attributes.setAffinity(affinity);	
		
		// Set domain
		String domain = CommonXmlParserUtility.getTextByTagName(element, "domain");
		attributes.setDomain(domain);
		
		// Set subscription
		String subscription = CommonXmlParserUtility.getTextByTagName(element, "subscription");
		attributes.setSubscription(subscription);
		
		// Set keyStore
		String keyStore = CommonXmlParserUtility.getTextByTagName(element, "keyStore");
		attributes.setKeyStore(keyStore);
		
		// Set version
		String version = CommonXmlParserUtility.getTextByTagName(element, "version");
		attributes.setVersion(version);
		
		// Set URL
		String url = CommonXmlParserUtility.getTextByTagName(element, "url");
		attributes.setUrl(url);
		
		// Set master
		String master = CommonXmlParserUtility.getTextByTagName(element, "master");
		attributes.setMaster(master);
		
		return attributes;
	}

}
