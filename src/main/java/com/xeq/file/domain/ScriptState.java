package com.xeq.file.domain;

public class ScriptState {
	
	private String stepName;
	private String state;
	
	public void setStepName(String stepName) {
		this.stepName = stepName;
	}
	public String getStepName() {
		return stepName;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getState() {
		return state;
	}
	public ScriptState() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "ScriptState [stepName=" + stepName + ", state=" + state + "]";
	}
	

}
