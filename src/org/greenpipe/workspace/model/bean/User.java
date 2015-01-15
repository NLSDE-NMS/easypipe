package org.greenpipe.workspace.model.bean;

// Generated 2014-10-27 17:08:14 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * User generated by hbm2java
 */
public class User implements java.io.Serializable {

	private Integer id;
	private String username;
	private String email;
	private String password;
	private int deleted;
	private int active;
	private Date createTime;
	private Date updateTime;
	private Set workspaces = new HashSet(0);

	public User() {
	}

	public User(String username, String email, String password, int deleted,
			int active, Date createTime, Date updateTime) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.deleted = deleted;
		this.active = active;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	public User(String username, String email, String password, int deleted,
			int active, Date createTime, Date updateTime, Set workspaces) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.deleted = deleted;
		this.active = active;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.workspaces = workspaces;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getDeleted() {
		return this.deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	public int getActive() {
		return this.active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Set getWorkspaces() {
		return this.workspaces;
	}

	public void setWorkspaces(Set workspaces) {
		this.workspaces = workspaces;
	}

}