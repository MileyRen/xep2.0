package com.ssh.xep.entity;

import java.util.List;

/**
 * Created by qi_l on 2016/12/2.
 */
public class TreeList {
	private int id;
	private String name;
	private boolean deleted;
	private int folderId;
	private String type;
	private List<TreeList> children;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public int getFolderId() {
		return folderId;
	}

	public void setFolderId(int folderId) {
		this.folderId = folderId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String text) {
		this.name = text;
	}

	public List<TreeList> getChildren() {
		return children;
	}

	public void setChildren(List<TreeList> children) {
		this.children = children;
	}
}
