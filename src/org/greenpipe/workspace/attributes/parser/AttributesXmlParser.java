package org.greenpipe.workspace.attributes.parser;

import org.greenpipe.workspace.attributes.bean.Attributes;
import org.w3c.dom.Element;

public interface AttributesXmlParser {
	
	public Attributes parseXml(Element element);
	
}
