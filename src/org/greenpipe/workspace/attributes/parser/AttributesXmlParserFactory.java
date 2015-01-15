package org.greenpipe.workspace.attributes.parser;

import java.util.HashMap;

public class AttributesXmlParserFactory {
	private static HashMap<String, AttributesXmlParser> parsers = new HashMap<String, AttributesXmlParser>();
	
	static {
		parsers.put("azure", AzureAttributesXmlParser.getSingleInstance());
		parsers.put("ec2", EC2AttributesXmlParser.getSingleInstance());
		parsers.put("openstack", OpenstackAttributesXmlParser.getSingleInstance());
	}
	
	public static AttributesXmlParser getAttributesXmlParser(String key) {
		if(parsers.containsKey(key)) {
			return parsers.get(key);
		} else {
			return null;
		}
	}
}
