package com.xeq.file.domain;

import org.springframework.stereotype.Component;

@Component
public class JobSearch {

	private String createTime;
	private long fTime;
	private long tTime;
	private String sort;
	private String sortByTime;
	private String sortDA;
	private String jobstate;

	public JobSearch() {
	}

	public JobSearch(String createTime, long fTime, long tTime, String sort, String sortByTime, String sortDA,
			String jobstate) {
		super();
		this.createTime = createTime;
		this.fTime = fTime;
		this.tTime = tTime;
		this.sort = sort;
		this.sortByTime = sortByTime;
		this.sortDA = sortDA;
		this.jobstate = jobstate;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public long getfTime() {
		return fTime;
	}

	public void setfTime(long fTime) {
		this.fTime = fTime;
	}

	public long gettTime() {
		return tTime;
	}

	public void settTime(long tTime) {
		this.tTime = tTime;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getSortByTime() {
		return sortByTime;
	}

	public void setSortByTime(String sortByTime) {
		this.sortByTime = sortByTime;
	}

	public String getSortDA() {
		return sortDA;
	}

	public void setSortDA(String sortDA) {
		this.sortDA = sortDA;
	}

	public String getJobstate() {
		return jobstate;
	}

	public void setJobstate(String jobstate) {
		this.jobstate = jobstate;
	}

}
