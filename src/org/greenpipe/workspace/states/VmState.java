package org.greenpipe.workspace.states;

public enum VmState {
	// Create and Bootstrap
	INITIALIZING, BOOTSTRAPPING, SUCCESSFUL, FAILED,
	
	// Delete
	DELETING, DELETED,
	
	// Start
    STARTING, STARTED,
	
    // Stop
	STOPPING, STOPED,
	
	// Restart
	RESTARTING, RESTARTED

}
