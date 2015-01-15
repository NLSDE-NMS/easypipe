package org.greenpipe.workspace.vm.common;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

public class RemoteProcess {
	private Connection connection;
	private String ipAddress;
	private String username;
	private String password;

	public RemoteProcess() {}

	public RemoteProcess(String ipAddress, String username, String password) {
		this.ipAddress = ipAddress;
		this.username = username;
		this.password = password;
	}

	public boolean connect() throws IOException {
		connection = new Connection(ipAddress);
		connection.connect();
		return connection.authenticateWithPassword(username, password);
	}

	public int executeCommand(String command) {
		int returnValue = -1;
		try {
			if(connect()) {
				System.out.println("\nconnect successfully\n");
				Session session = connection.openSession();
				session.execCommand(command);
				
				InputStream stdOut = new StreamGobbler(session.getStdout());
				InputStream stdErr = new StreamGobbler(session.getStderr());
				printInfo(stdOut);
				printInfo(stdErr);

				if (session != null) {
					System.out.println("RemoteProcess session is not null");
					returnValue = session.getExitStatus();
				} else {
					System.out.println("RemoteProcess session is null");
					returnValue = 0;
				}
				session.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnValue;
	}

	public void printInfo(InputStream inputStream) {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));  
		String str;
		try {
			while(true) {  
				str = bufferedReader.readLine();
				if(str != null) {
					System.out.println(str);  
				}
				else {
					break;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
}
