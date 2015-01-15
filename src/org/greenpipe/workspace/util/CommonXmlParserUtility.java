package org.greenpipe.workspace.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class CommonXmlParserUtility {

	public static InputStream StringToInputStream(String str) {
		return ( new ByteArrayInputStream(str.getBytes()) );
	}
	
	public static Element getElementByTagName(Element element, String tag) {
		NodeList nodeList = element.getElementsByTagName(tag);
		if (nodeList != null && nodeList.getLength() > 0) {
			return (Element) nodeList.item(0);
		} else {
			return null;
		}
	}

	public static NodeList getNodeListByTagName(Element element, String tag) {
		return element.getElementsByTagName(tag);
	}
	
	public static String getTextByTagName(Element element, String tag) {
		NodeList nodeList = element.getElementsByTagName(tag);
		Text text = null;
		if (nodeList != null && nodeList.getLength() > 0) {
			Element childElement = (Element) nodeList.item(0);
			text = (Text) childElement.getFirstChild();
		}
		
		if(text != null) {
			return text.getNodeValue();
		}
		
		return null;
	}
	
}
