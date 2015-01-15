package org.greenpipe.workspace.handler;

import java.util.Date;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.greenpipe.workspace.model.bean.Workspace;
import org.greenpipe.workspace.states.WorkspaceState;
import org.greenpipe.workspace.util.Parameters;
import org.greenpipe.workspace.util.WorkspaceUpdater;

public class Handler implements Runnable{
	private ChefEngine chefEngine;
	private Queue<Workspace> bootstrapQueue;
	private Queue<Workspace> deleteQueue;
	private Queue<Workspace> listQueue;
	private Queue<Workspace> startQueue;
	private Queue<Workspace> stopQueue;
	private Queue<Workspace> restartQueue;

	public Handler() {}

	public Handler(ChefEngine chefEngine) {
		this.chefEngine = chefEngine;
		bootstrapQueue = new ConcurrentLinkedQueue<Workspace>();
		deleteQueue = new ConcurrentLinkedQueue<Workspace>();
		listQueue = new ConcurrentLinkedQueue<Workspace>();
		startQueue = new ConcurrentLinkedQueue<Workspace>();
		stopQueue = new ConcurrentLinkedQueue<Workspace>();
		restartQueue = new ConcurrentLinkedQueue<Workspace>();
	}

	public ChefEngine getChefEngine() {
		return chefEngine;
	}
	
	/**
	 * Add workspace into bootstrap-queue
	 * @param workspace
	 */
	public synchronized void addToBootstrapQueue(Workspace workspace) {
		bootstrapQueue.add(workspace);
		WorkspaceUpdater.updateWorkspace(workspace, WorkspaceState.BOOTSTRAP_QUEUED.name(), 
				WorkspaceState.BOOTSTRAP_QUEUED.name(), new Date(System.currentTimeMillis()));
	}

	/**
	 * Add workspace into delete-queue
	 * @param workspace
	 */
	public synchronized void addToDeleteQueue(Workspace workspace) {
		deleteQueue.add(workspace);
		WorkspaceUpdater.updateWorkspace(workspace, WorkspaceState.DELETE_QUEUED.name(), 
				WorkspaceState.DELETE_QUEUED.name(), new Date(System.currentTimeMillis()));
	}

	/**
	 * Add workspace into list-queue
	 * TODO Action for 'list'
	 * @param workspace
	 */
	public synchronized void addToListQueue(Workspace workspace) {
		listQueue.add(workspace);
	}

	/**
	 * Add workspace into start-queue
	 * @param workspace
	 */
	public synchronized void addToStartQueue(Workspace workspace) {
		startQueue.add(workspace);
		WorkspaceUpdater.updateWorkspace(workspace, WorkspaceState.START_QUEUED.name(), 
				WorkspaceState.START_QUEUED.name(), new Date(System.currentTimeMillis()));
	}

	/**
	 * Add workspace into stop-queue
	 * @param workspace
	 */
	public synchronized void addToStopQueue(Workspace workspace) {
		stopQueue.add(workspace);
		WorkspaceUpdater.updateWorkspace(workspace, WorkspaceState.STOP_QUEUED.name(), 
				WorkspaceState.STOP_QUEUED.name(), new Date(System.currentTimeMillis()));
	}
	
	/**
	 * Add workspace into restart-queue
	 * @param workspace
	 */
	public synchronized void addToRestartQueue(Workspace workspace) {
		restartQueue.add(workspace);
		WorkspaceUpdater.updateWorkspace(workspace, WorkspaceState.RESTART_QUEUED.name(), 
				WorkspaceState.RESTART_QUEUED.name(), new Date(System.currentTimeMillis()));
	}

	/**
	 * Action with workspaces: create, bootstrap, delete, list, start, stop.
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		/*
		 *  TODO, A scheduling algorithm should be developed to
		 *  deal with the workspaces in each queue.
		 */
		while(true) {
			Workspace workspace = null;

			/*
			 * Create workspace 
			 */
			HandlerContainer handlerContainer = HandlerContainer.getSingleInstance();
			synchronized(handlerContainer.createQueue) {
				workspace = handlerContainer.createQueue.peek();
				if(workspace != null && workspace.getProvider().equals(chefEngine.getProvider())) {
					handlerContainer.createQueue.poll();
				} else {
					workspace = null;
				}
			}
			if(workspace != null) {
				Action.createWorkspace(workspace, chefEngine);
			}
			
			/*
			 *  Bootstrap workspace
			 */
			workspace = bootstrapQueue.poll();
			if(workspace != null) {
				Action.bootstrapWorkspace(workspace, chefEngine);
			}

			/*
			 *  Delete workspace
			 */
			workspace = deleteQueue.poll();
			if(workspace != null) {
				Action.deleteWorkspace(workspace, chefEngine);				
			}

			/*
			 *  List workspaces, TODO
			 */

			/*
			 *  Start workspace
			 */
			workspace = startQueue.poll();
			if(workspace != null) {
				Action.startWorkspace(workspace, chefEngine);
			}

			/*
			 *  Stop workspace
			 */
			workspace = stopQueue.poll();
			if(workspace != null) {
				Action.stopWorkspace(workspace, chefEngine);
			}
			
			/*
			 * Restart workspace
			 */
			workspace = restartQueue.poll();
			if(workspace != null) {
				Action.restartWorkspace(workspace, chefEngine);
			}

			/*
			 * Sleep for one second
			 */
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
