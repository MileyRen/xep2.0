package com.xeq.file.domain;

public class FringeNode {
	
	private int key;
	private String category;
	private String loc;
	private String text;
	
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getLoc() {
		return loc;
	}
	public void setLoc(String loc) {
		this.loc = loc;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public FringeNode() {
		// TODO Auto-generated constructor stub
	}
	public FringeNode(int key, String category, String loc, String text) {
		this.key = key;
		this.category = category;
		this.loc = loc;
		this.text = text;
	}
	
}
