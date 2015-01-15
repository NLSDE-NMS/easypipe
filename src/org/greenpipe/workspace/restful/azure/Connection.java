package org.greenpipe.workspace.restful.azure;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

public class Connection {

	private static KeyStore getKeyStore(String keyStoreName, String password) 
			throws IOException {
		KeyStore ks = null;
		FileInputStream fis = null;
		try {
			ks = KeyStore.getInstance("JKS");
			char[] passwordArray = password.toCharArray();
			fis = new java.io.FileInputStream(keyStoreName);
			ks.load(fis, passwordArray);
			fis.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if (fis != null) {
				fis.close();
			}
		}
		return ks;
	}

	private static SSLSocketFactory getSSLSocketFactory(String keyStoreName, String password) 
			throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, 
			KeyManagementException, IOException {
		KeyStore ks = getKeyStore(keyStoreName, password);
		KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
		keyManagerFactory.init(ks, password.toCharArray());

		SSLContext context = SSLContext.getInstance("TLS");
		context.init(keyManagerFactory.getKeyManagers(), null, new SecureRandom());

		return context.getSocketFactory();
	}

	// Source - http://www.mkyong.com/java/how-to-convert-inputstream-to-string-in-java/
	private static String getStringFromInputStream(InputStream is) {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		String line;
		try {
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}

	public static String processGetRequest(URL url, String keyStore, String keyStorePassword, String version_time) 
			throws UnrecoverableKeyException, KeyManagementException, KeyStoreException, 
			NoSuchAlgorithmException, IOException {
		SSLSocketFactory sslFactory = getSSLSocketFactory(keyStore, keyStorePassword);
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
		con.setSSLSocketFactory(sslFactory);
		con.setRequestMethod("GET");
		con.addRequestProperty("x-ms-version", version_time);
		InputStream responseStream = (InputStream) con.getContent();
		String response = getStringFromInputStream(responseStream);
		responseStream.close();
		con.disconnect();
		return response;
	}

	public static Property processPostRequest(URL url, byte[] data, String contentType, String keyStore, 
			String keyStorePassword) throws UnrecoverableKeyException, KeyManagementException, 
			KeyStoreException, NoSuchAlgorithmException, IOException {
		
		SSLSocketFactory sslFactory = getSSLSocketFactory(keyStore, keyStorePassword);
		HttpsURLConnection con = null;
		con = (HttpsURLConnection) url.openConnection();
		con.setSSLSocketFactory(sslFactory);
		con.setDoOutput(true);
		con.setRequestMethod("POST");
		con.addRequestProperty("x-ms-version", "2014-02-01");
		con.setRequestProperty("Content-Length", String.valueOf(data.length));
		con.setRequestProperty("Content-Type", contentType);

		DataOutputStream  requestStream = new DataOutputStream (con.getOutputStream());
		requestStream.write(data);
		requestStream.flush();
		requestStream.close();
		
		Property property = new Property();
		property.setResponseMessage(con.getResponseMessage());
		property.setResponseCode(con.getResponseCode());
		
		con.disconnect();
		return property;  
	}  

}
