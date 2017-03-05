package com.gene.utils;

import java.sql.Timestamp;

/**
 * Tool entity. @author MyEclipse Persistence Tools
 */

public class Tool implements java.io.Serializable {

	// Fields

	private Integer id;
	private User user;
	private Tooltype tooltype;
	private String toolName;
	private Integer isShared;
	private Integer savedResults;
	private Timestamp addedTime;
	private String describtion;
	private String parameters;

	// Constructors

	/** default constructor */
	public Tool() {
	}

	/** minimal constructor */
	public Tool(String toolName) {
		this.toolName = toolName;
	}

	/** full constructor */
	public Tool(User user, Tooltype tooltype, String toolName,
			Integer isShared, Integer savedResults, Timestamp addedTime,
			String describtion, String parameters) {
		this.user = user;
		this.tooltype = tooltype;
		this.toolName = toolName;
		this.isShared = isShared;
		this.savedResults = savedResults;
		this.addedTime = addedTime;
		this.describtion = describtion;
		this.parameters = parameters;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Tooltype getTooltype() {
		return this.tooltype;
	}

	public void setTooltype(Tooltype tooltype) {
		this.tooltype = tooltype;
	}

	public String getToolName() {
		return this.toolName;
	}

	public void setToolName(String toolName) {
		this.toolName = toolName;
	}

	public Integer getIsShared() {
		return this.isShared;
	}

	public void setIsShared(Integer isShared) {
		this.isShared = isShared;
	}

	public Integer getSavedResults() {
		return this.savedResults;
	}

	public void setSavedResults(Integer savedResults) {
		this.savedResults = savedResults;
	}

	public Timestamp getAddedTime() {
		return this.addedTime;
	}

	public void setAddedTime(Timestamp addedTime) {
		this.addedTime = addedTime;
	}

	public String getDescribtion() {
		return this.describtion;
	}

	public void setDescribtion(String describtion) {
		this.describtion = describtion;
	}

	public String getParameters() {
		return this.parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

}