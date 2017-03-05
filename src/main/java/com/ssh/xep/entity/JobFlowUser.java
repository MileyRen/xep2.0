package com.ssh.xep.entity;

public class JobFlowUser {
	private int id;
	private int userId;
	private String userName;
	private int flowBasicInfoId;
	private String flowBasicInfoName;
	private String bpmn;
	private String processInfo;
	private long bgTime;
	private long edTime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getFlowBasicInfoId() {
		return flowBasicInfoId;
	}
	public void setFlowBasicInfoId(int flowBasicInfoId) {
		this.flowBasicInfoId = flowBasicInfoId;
	}
	public String getFlowBasicInfoName() {
		return flowBasicInfoName;
	}
	public void setFlowBasicInfoName(String flowBasicInfoName) {
		this.flowBasicInfoName = flowBasicInfoName;
	}
	public String getBpmn() {
		return bpmn;
	}
	public void setBpmn(String bpmn) {
		this.bpmn = bpmn;
	}
	public String getProcessInfo() {
		return processInfo;
	}
	public void setProcessInfo(String processInfo) {
		this.processInfo = processInfo;
	}
	public long getBgTime() {
		return bgTime;
	}
	public void setBgTime(long bgTime) {
		this.bgTime = bgTime;
	}
	public long getEdTime() {
		return edTime;
	}
	public void setEdTime(long edTime) {
		this.edTime = edTime;
	}
}
