package org.greenpipe.workspace.entry;

import java.util.List;

import org.greenpipe.workspace.handler.ChefEngine;
import org.greenpipe.workspace.handler.HandlerContainer;
import org.greenpipe.workspace.util.ConfigXmlParser;

public class Initialization {

	/**
	 * Initialize handlers according to the configuration file
	 * @param configFilePath
	 */
	public static void initHandlers(String configFilePath) {
		// Get chef engines from configuration file
		List<ChefEngine> chefEngineList = ConfigXmlParser.parseConfigFile(configFilePath);
		
		// Initialize handlers
		HandlerContainer handlerContainer = HandlerContainer.getSingleInstance();
		handlerContainer.initializeHandlers(chefEngineList);
	}

}
