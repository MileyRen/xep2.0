package com.gene.utils;

import java.util.HashSet;
import java.util.Set;

/**
 * Tooltype entity. @author MyEclipse Persistence Tools
 */

public class Tooltype implements java.io.Serializable {

	// Fields

	private Integer id;
	private User user;
	private String typeName;
	private String showColor;
	private String describtion;
	private Integer toolNum;
	private Set tools = new HashSet(0);

	// Constructors

	/** default constructor */
	public Tooltype() {
	}

	/** minimal constructor */
	public Tooltype(User user, String typeName) {
		this.user = user;
		this.typeName = typeName;
	}

	/** full constructor */
	public Tooltype(User user, String typeName, String showColor,
			String describtion, Integer toolNum, Set tools) {
		this.user = user;
		this.typeName = typeName;
		this.showColor = showColor;
		this.describtion = describtion;
		this.toolNum = toolNum;
		this.tools = tools;
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

	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getShowColor() {
		return this.showColor;
	}

	public void setShowColor(String showColor) {
		this.showColor = showColor;
	}

	public String getDescribtion() {
		return this.describtion;
	}

	public void setDescribtion(String describtion) {
		this.describtion = describtion;
	}

	public Integer getToolNum() {
		return this.toolNum;
	}

	public void setToolNum(Integer toolNum) {
		this.toolNum = toolNum;
	}

	public Set getTools() {
		return this.tools;
	}

	public void setTools(Set tools) {
		this.tools = tools;
	}

}