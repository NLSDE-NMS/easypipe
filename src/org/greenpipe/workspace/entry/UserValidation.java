package org.greenpipe.workspace.entry;

import java.util.List;

import org.greenpipe.workspace.model.bean.User;
import org.greenpipe.workspace.model.dao.UserHome;

public class UserValidation {
	
	public static User validateUser(String username, String password) {
		// new User() has initial values
		User requestUser = new User();
		if (username.contains("@")) {
			requestUser.setEmail(username);
		} else {
			requestUser.setUsername(username);
		}
		requestUser.setDeleted(0);
		requestUser.setActive(1);
		
		User targetUser = null;
		UserHome userHome = new UserHome();
		List<User> users = userHome.findByExample(requestUser);
		if (users.size() > 0) {
			targetUser = (User) users.get(0);
		} else {
			System.out.println("Cloud not find user: " + username);
		}
		
		if (targetUser != null && (targetUser.getPassword()).equals(password)) {
			return targetUser;
		} else {
			return null;
		}
	}
	
}
