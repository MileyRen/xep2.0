package com.xeq.file.domain;

public class NodeDataArray {
	
	private int key;
	private String loc;
	private String text;
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
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
	
	public NodeDataArray() {
		// TODO Auto-generated constructor stub
	}
	public NodeDataArray(int key, String loc, String text) {
		super();
		this.key = key;
		this.loc = loc;
		this.text = text;
	}
	

}
