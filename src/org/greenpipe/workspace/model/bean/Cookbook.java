package org.greenpipe.workspace.model.bean;

// Generated 2014-10-27 17:08:14 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Cookbook generated by hbm2java
 */
public class Cookbook implements java.io.Serializable {

	private Integer id;
	private String version;
	private String name;
	private String category;
	private String description;
	private String maintainer;
	private Date createTime;
	private Date updateTime;
	private Set cookbookDependenciesForDestinationId = new HashSet(0);
	private Set cookbookDependenciesForSourceId = new HashSet(0);
	private Set workspaceCookbookAssociations = new HashSet(0);

	public Cookbook() {
	}

	public Cookbook(String name, String category, String description,
			String maintainer, Date createTime, Date updateTime) {
		this.name = name;
		this.category = category;
		this.description = description;
		this.maintainer = maintainer;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	public Cookbook(String name, String category, String description,
			String maintainer, Date createTime, Date updateTime,
			Set cookbookDependenciesForDestinationId,
			Set cookbookDependenciesForSourceId,
			Set workspaceCookbookAssociations) {
		this.name = name;
		this.category = category;
		this.description = description;
		this.maintainer = maintainer;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.cookbookDependenciesForDestinationId = cookbookDependenciesForDestinationId;
		this.cookbookDependenciesForSourceId = cookbookDependenciesForSourceId;
		this.workspaceCookbookAssociations = workspaceCookbookAssociations;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMaintainer() {
		return this.maintainer;
	}

	public void setMaintainer(String maintainer) {
		this.maintainer = maintainer;
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

	public Set getCookbookDependenciesForDestinationId() {
		return this.cookbookDependenciesForDestinationId;
	}

	public void setCookbookDependenciesForDestinationId(
			Set cookbookDependenciesForDestinationId) {
		this.cookbookDependenciesForDestinationId = cookbookDependenciesForDestinationId;
	}

	public Set getCookbookDependenciesForSourceId() {
		return this.cookbookDependenciesForSourceId;
	}

	public void setCookbookDependenciesForSourceId(
			Set cookbookDependenciesForSourceId) {
		this.cookbookDependenciesForSourceId = cookbookDependenciesForSourceId;
	}

	public Set getWorkspaceCookbookAssociations() {
		return this.workspaceCookbookAssociations;
	}

	public void setWorkspaceCookbookAssociations(
			Set workspaceCookbookAssociations) {
		this.workspaceCookbookAssociations = workspaceCookbookAssociations;
	}

}
