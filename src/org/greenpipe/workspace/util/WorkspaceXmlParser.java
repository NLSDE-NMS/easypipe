package org.greenpipe.workspace.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.greenpipe.workspace.model.bean.Cookbook;
import org.greenpipe.workspace.model.bean.User;
import org.greenpipe.workspace.model.bean.Workspace;
import org.greenpipe.workspace.model.bean.WorkspaceCookbookAssociation;
import org.greenpipe.workspace.model.dao.CookbookHome;
import org.greenpipe.workspace.states.WorkspaceState;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

public class WorkspaceXmlParser {

	/**
	 * Parse workspace xml, for create purpose
	 * TODO There are some exceptions need to be handled
	 */
	public static Workspace parseWorkspaceXML(String workspaceXML) {
		// Put workspace xml into input stream
		InputStream inputStream = CommonXmlParserUtility.StringToInputStream(workspaceXML);

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

		Element root = document.getDocumentElement();

		Workspace workspace = new Workspace();

		// Set provider
		String provider = CommonXmlParserUtility.getTextByTagName(root, "provider");
		workspace.setProvider(provider);

		// Set VM size
		String vmSize = CommonXmlParserUtility.getTextByTagName(root, "vm_size");
		workspace.setVmSize(vmSize);

		// Set VM number
		String vmNumber = CommonXmlParserUtility.getTextByTagName(root, "vm_number");
		workspace.setVmNumber(Integer.parseInt(vmNumber));

		// Set description
		String description = CommonXmlParserUtility.getTextByTagName(root, "description");
		workspace.setDescription(description);

		// Set run_list
		Set workspaceCookbookAssociations = new HashSet(0);
		Element runListElement = CommonXmlParserUtility.getElementByTagName(root, "run_list");
		NodeList packageList = CommonXmlParserUtility.getNodeListByTagName(runListElement, "package");
		for (int i = 0; i < packageList.getLength(); i++) {
			Element packageElement = (Element) packageList.item(i);

			String name = CommonXmlParserUtility.getTextByTagName(packageElement, "name");
			String version = CommonXmlParserUtility.getTextByTagName(packageElement, "version");

			Cookbook cookbook = new Cookbook();
			cookbook.setName(name);
			
			// Because it is a demo, so ignore the version
			//cookbook.setVersion(version);
			
			CookbookHome cookbookHome = new CookbookHome();
			List<Cookbook> cookbooks = cookbookHome.findByExample(cookbook);

			WorkspaceCookbookAssociation association = null;

			if (cookbooks.size() > 0) {
				association = new WorkspaceCookbookAssociation();
				association.setWorkspace(workspace);
				association.setCookbook(cookbooks.get(0));
			} else {
				// TODO Can not find target cookbook, throw error information
				// The cookbook mechanism is not completed, so ignore the missing of cookbooks
				/*
				workspace.setState(WorkspaceState.PARSE_FAILED.toString());
				workspace.setMessage("Incorrect name or version of cookbook: " + name);
				System.out.println("Incorrect name or version of cookbook: " + name);
				break;
				*/
			}
			if (association != null)
				workspaceCookbookAssociations.add(association);
		}
		workspace.setWorkspaceCookbookAssociations(workspaceCookbookAssociations);
		
		if(workspace.getState() == null || !workspace.getState().equals(WorkspaceState.PARSE_FAILED.name()))
			workspace.setState(WorkspaceState.PARSE_SUCCESSFUL.name());

		return workspace;
	}
	
	/**
	 * Parse workspace xml, for bootstrap purpose
	 * TODO There are some exceptions need to be handled
	 */
	public static void parseWorkspaceXML(Workspace workspace, String workspaceXML) {
		// Put workspace xml into input stream
		InputStream inputStream = CommonXmlParserUtility.StringToInputStream(workspaceXML);

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

		Element root = document.getDocumentElement();

		// Set run_list
		Set workspaceCookbookAssociations = new HashSet(0);
		Element runListElement = CommonXmlParserUtility.getElementByTagName(root, "run_list");
		NodeList packageList = CommonXmlParserUtility.getNodeListByTagName(runListElement, "package");
		for (int i = 0; i < packageList.getLength(); i++) {
			Element packageElement = (Element) packageList.item(i);

			String name = CommonXmlParserUtility.getTextByTagName(packageElement, "name");
			String version = CommonXmlParserUtility.getTextByTagName(packageElement, "version");

			Cookbook cookbook = new Cookbook();
			cookbook.setName(name);
			
			// Because it is a demo, so ignore the version
			//cookbook.setVersion(version);
			
			CookbookHome cookbookHome = new CookbookHome();
			List<Cookbook> cookbooks = cookbookHome.findByExample(cookbook);

			WorkspaceCookbookAssociation association = null;

			if (cookbooks.size() > 0) {
				association = new WorkspaceCookbookAssociation();
				association.setWorkspace(workspace);
				association.setCookbook(cookbooks.get(0));
			} else {
				// TODO Can not find target cookbook, throw error information
				// The cookbook mechanism is not completed, so ignore the missing of cookbooks
				/*
				workspace.setState(WorkspaceState.PARSE_FAILED.name());
				workspace.setMessage("Incorrect name or version of cookbook: " + name);
				System.out.println("Incorrect name or version of cookbook: " + name);
				break;
				*/
			}
			if (association != null)
				workspaceCookbookAssociations.add(association);
		}
		workspace.setWorkspaceCookbookAssociations(workspaceCookbookAssociations);
		
		if(workspace.getState() == null || !workspace.getState().equals(WorkspaceState.PARSE_FAILED.name()))
			workspace.setState(WorkspaceState.PARSE_SUCCESSFUL.name());

	}

}
