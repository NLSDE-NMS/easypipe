package org.greenpipe.workspace.handler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.greenpipe.workspace.model.bean.Workspace;
import org.greenpipe.workspace.states.WorkspaceState;
import org.greenpipe.workspace.util.Parameters;
import org.greenpipe.workspace.util.WorkspaceUpdater;

public class HandlerContainer {
	private static HandlerContainer handlerContainer = new HandlerContainer();
	private HashMap<Integer, Handler> handlers;
	Queue<Workspace> createQueue;
	
	private HandlerContainer() {
		handlers = new HashMap<Integer, Handler>();
		createQueue = new ConcurrentLinkedQueue<Workspace>();
	}
	
	public static HandlerContainer getSingleInstance() {
		return handlerContainer;
	}

	/**
	 * Set worker thread of handler for each chef engine
	 * @param chefEngineList
	 */
	public void initializeHandlers(List<ChefEngine> chefEngineList) {
		ExecutorService executor = Executors.newCachedThreadPool();
		for(ChefEngine chefEngine : chefEngineList) {
			Handler handler = new Handler(chefEngine);
			executor.execute(handler);
			handlers.put(chefEngine.getId(), handler);
			System.out.println("Worker thread of handler for chef engine " + chefEngine.getHostname() + " started");
		}
		executor.shutdown();
	}
	
	public Handler getHandler(int id) {
		if(handlers.containsKey(id)) {
			return handlers.get(id);
		} else {
			return null;
		}
	}
	
	/**
	 * Add new workspace into create-queue
	 * @param workspace
	 */
	public synchronized void addToCreateQueue(Workspace workspace) {
		createQueue.add(workspace);
		WorkspaceUpdater.updateWorkspace(workspace, WorkspaceState.CREATE_QUEUED.name(), 
				WorkspaceState.CREATE_QUEUED.name(), new Date(System.currentTimeMillis()));
	}
	
	/**
	 * Add workspace into the bootstrap-queue of corresponding handler
	 * @param workspace
	 */
	public void addToBootstrapQueue(Workspace workspace) {
		handlers.get(workspace.getServer()).addToBootstrapQueue(workspace);
	}
	
	/**
	 * Add workspace into the delete-queue of corresponding handler
	 * @param workspace
	 */
	public void addToDeleteQueue(Workspace workspace) {
		handlers.get(workspace.getServer()).addToDeleteQueue(workspace);
	}
	
	/**
	 * Add workspace into the list-queue of corresponding handler
	 * @param workspace
	 */
	public void addToListQueue(Workspace workspace) {
		handlers.get(workspace.getServer()).addToListQueue(workspace);
	}
	
	/**
	 * Add workspace into the start-queue of corresponding handler
	 * @param workspace
	 */
	public void addToStartQueue(Workspace workspace) {
		handlers.get(workspace.getServer()).addToStartQueue(workspace);
	}
	
	/**
	 * Add workspace into the stop-queue of corresponding handler
	 * @param workspace
	 */
	public void addToStopQueue(Workspace workspace) {
		handlers.get(workspace.getServer()).addToStopQueue(workspace);
	}
	
	/**
	 * Add workspace into the restart-queue of corresponding handler
	 * @param workspace
	 */
	public void addToRestartQueue(Workspace workspace) {
		handlers.get(workspace.getServer()).addToRestartQueue(workspace);
	}
	
}
