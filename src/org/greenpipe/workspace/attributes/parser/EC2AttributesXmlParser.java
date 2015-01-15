package org.greenpipe.workspace.attributes.parser;

import org.greenpipe.workspace.attributes.bean.Attributes;
import org.w3c.dom.Element;

public class EC2AttributesXmlParser implements AttributesXmlParser {

	private static AttributesXmlParser parser = new EC2AttributesXmlParser();
	
	private EC2AttributesXmlParser() {}
	
	public static AttributesXmlParser getSingleInstance() {
		return parser;
	}
	
	@Override
	public Attributes parseXml(Element element) {
		// TODO Auto-generated method stub
		return null;
	}

}
