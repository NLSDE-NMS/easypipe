package org.greenpipe.workspace.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.greenpipe.workspace.attributes.bean.Attributes;
import org.greenpipe.workspace.attributes.parser.AttributesXmlParser;
import org.greenpipe.workspace.attributes.parser.AttributesXmlParserFactory;
import org.greenpipe.workspace.handler.ChefEngine;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ConfigXmlParser {

	public static List<ChefEngine> parseConfigFile(String configFilePath) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		Document document = null;

		try {
			db = dbf.newDocumentBuilder();
			document = db.parse(configFilePath);
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

		Element root = document.getDocumentElement();

		/**
		 * Add chef engines
		 */
		List<ChefEngine> chefEngineList = new ArrayList<ChefEngine>();
		Element serversElement = CommonXmlParserUtility.getElementByTagName(root, "servers");
		NodeList serverNodeList = CommonXmlParserUtility.getNodeListByTagName(serversElement, "server");
		for(int i = 0; i < serverNodeList.getLength(); i++) {
			ChefEngine chefEngine = new ChefEngine();
			Element serverElement = (Element) serverNodeList.item(i);

			// Set id
			String id = CommonXmlParserUtility.getTextByTagName(serverElement, "id");
			chefEngine.setId(Integer.parseInt(id));

			// Set provider
			String provider = CommonXmlParserUtility.getTextByTagName(serverElement, "provider");
			chefEngine.setProvider(provider);
			
			// Set hostname
			String hostname = CommonXmlParserUtility.getTextByTagName(serverElement, "hostname");
			chefEngine.setHostname(hostname);
			
			// Set username
			String username = CommonXmlParserUtility.getTextByTagName(serverElement, "username");
			chefEngine.setUsername(username);
					
			// Set password
			String password = CommonXmlParserUtility.getTextByTagName(serverElement, "password");
			chefEngine.setPassword(password);
				
			// Set attributes
			Element attributesElement = CommonXmlParserUtility.getElementByTagName(serverElement, "attributes");
			AttributesXmlParser attributesXmlParser = AttributesXmlParserFactory.getAttributesXmlParser(provider);
			Attributes attributes = attributesXmlParser.parseXml(attributesElement);
			chefEngine.setAttributes(attributes);
			
			// Add chefEngine
			chefEngineList.add(chefEngine);
		}
		
		return chefEngineList;
	}
	
}
