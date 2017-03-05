package com.ssh.xep.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tooltype", catalog = "xep")
public class ToolTypes implements Serializable {

	private static final long serialVersionUID = 7833103177618986871L;

	private int id;
	private String typeName;
	private int addedUserId;
	private String showColor;
	private String description;
	private int toolNum;

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "Describtion")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "TypeName")
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Column(name = "AddedUserID")
	public int getAddedUserId() {
		return addedUserId;
	}

	public void setAddedUserId(int addedUserId) {
		this.addedUserId = addedUserId;
	}

	@Column(name = "ShowColor")
	public String getShowColor() {
		return showColor;
	}

	public void setShowColor(String showColor) {
		this.showColor = showColor;
	}

	@Column(name = "ToolNum")
	public int getToolNum() {
		return toolNum;
	}

	public void setToolNum(int toolNum) {
		this.toolNum = toolNum;
	}

}
