package test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.greenpipe.workspace.attributes.bean.Attributes;
import org.greenpipe.workspace.entry.Provisioner;
import org.greenpipe.workspace.handler.Handler;
import org.greenpipe.workspace.handler.HandlerContainer;
import org.greenpipe.workspace.model.bean.Cookbook;
import org.greenpipe.workspace.model.bean.CookbookDependency;
import org.greenpipe.workspace.model.bean.User;
import org.greenpipe.workspace.model.bean.Workspace;
import org.greenpipe.workspace.model.bean.WorkspaceCookbookAssociation;
import org.greenpipe.workspace.model.dao.CookbookHome;
import org.greenpipe.workspace.model.dao.UserHome;
import org.greenpipe.workspace.model.dao.WorkspaceCookbookAssociationHome;
import org.greenpipe.workspace.model.dao.WorkspaceHome;
import org.greenpipe.workspace.restful.AzureRestfulAPI;
import org.greenpipe.workspace.restful.azure.Manager;
import org.greenpipe.workspace.states.WorkspaceState;

public class DatabaseTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String workspaceXML_1 = 
				"<workspace>"
						+ "<provider>azure</provider>"
						+ "<vm_size>Small</vm_size>"
						+ "<vm_number>3</vm_number>"
						+ "<description>This is a demo for workspace</description>"
						+ "<run_list>"
//						+ "<package><name>hadoop</name><version>1.0.0</version></package>"
//						+ "<package><name>cufflinks</name><version>1.0.0</version></package>"
						+ "<package><name>torque</name><version>1.0.0</version></package>"
//						+ "<package><name>tophat</name><version>1.5.0</version></package>"
						+ "</run_list>"
						+ "</workspace>";

		String workspaceXML_2 = 
				"<workspace>"
						+ "<provider>azure</provider>"
						+ "<vm_size>Medium</vm_size>"
						+ "<vm_number>3</vm_number>"
						+ "<description>This is a galaxy workspace</description>"
						+ "<run_list>"
						//+ "<package><name>tophat</name><version>1.5.0</version></package>"
						//+ "<package><name>cufflinks</name><version>0.0.6</version></package>"
						//+ "<package><name>cuffmerge</name><version>0.0.5</version></package>"
						//+ "<package><name>cuffdiff</name><version>0.0.6</version></package>"
						+ "<package><name>torque</name><version>1.0.0</version></package>"
						+ "</run_list>"
						+ "</workspace>";

		String username = "tom@126.com";
		String password = "12345678";
		
		String username2 = "bluesky8640@126.com";
		String password2 = "jc7812942";

		Provisioner provisioner = new Provisioner();

//		String createStatus = provisioner.create(workspaceXML_1, username2, password2);
//		System.out.println("\n\n" + createStatus);

		//String bootstrapStatus = provisioner.bootstrap("99", workspaceXML_1, username, password);
	    //System.out.println("\n\n" + bootstrapStatus);

		String deleteStatus = provisioner.delete("83", username, password);
		System.out.println("\n\n" + deleteStatus);

		//String listStatus = provisioner.list("jinchao", "123456");
	    //System.out.println("\n\n" + listStatus);
		
		//String stopStatus = provisioner.start("83", username, password);
	   // System.out.println("\n\n" + stopStatus);
		
//		Handler handler = HandlerContainer.getSingleInstance().getHandler(1);
//		Attributes attributes = handler.getChefEngine().getAttributes();
//		
//		try {
//			System.out.println(AzureRestfulAPI.getSingleInstance().stopVM("azure-workcluster83-3-1", attributes));
//		} catch (UnrecoverableKeyException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (KeyManagementException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (KeyStoreException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (NoSuchAlgorithmException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
		
		
		
		
//		UserHome userHome = new UserHome();
//		User user = userHome.findById(2);
//		System.out.println(user.getId());
//		System.out.println(user.getEmail());
//		System.out.println(user.getUsername());
//		System.out.println(user.getPassword());
//		System.out.println("\n");
//
//		Workspace workspace = new Workspace();
//		workspace.setUser(user);
//		//workspace.setState("Workspace deployment successfully");
//		//workspace.setVmNumber(3);
//		//workspace.setServer(5);
//		//workspace.setVmNumber(2);
//
//		WorkspaceHome workspaceHome = new WorkspaceHome();
//		List<Workspace> workspaces = workspaceHome.findByExampleUser(workspace);
//		for(Workspace ws : workspaces) {
//			System.out.println(ws.getId());
//			System.out.println(ws.getUser().getId());
//			System.out.println(ws.getServer());
//			System.out.println(ws.getProvider());
//			System.out.println(ws.getVmNumber());
//			System.out.println(ws.getVmSize());
//			System.out.println(ws.getState());
//			System.out.println(ws.getMessage());
//			System.out.println("\n");
//		}
	}
}
