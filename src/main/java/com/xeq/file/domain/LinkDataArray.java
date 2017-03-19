package com.xeq.file.domain;

public class LinkDataArray {
	
	private int from;
	private int to;
	private String fromPort;
	private String toPort;
	public int getFrom() {
		return from;
	}
	public void setFrom(int from) {
		this.from = from;
	}
	public int getTo() {
		return to;
	}
	public void setTo(int to) {
		this.to = to;
	}
	public String getFromPort() {
		return fromPort;
	}
	public void setFromPort(String fromPort) {
		this.fromPort = fromPort;
	}
	public String getToPort() {
		return toPort;
	}
	public void setToPort(String toPort) {
		this.toPort = toPort;
	}
	
	public LinkDataArray() {
		// TODO Auto-generated constructor stub
	}
	public LinkDataArray(int from, int to, String fromPort, String toPort) {
		super();
		this.from = from;
		this.to = to;
		this.fromPort = fromPort;
		this.toPort = toPort;
	}
	
	

}
