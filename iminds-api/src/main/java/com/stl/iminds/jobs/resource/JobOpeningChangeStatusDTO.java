package com.stl.iminds.jobs.resource;

import java.io.Serializable;

public class JobOpeningChangeStatusDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String jobOpeningid;
	private String status;
	/**
	 * @return the jobOpeningid
	 */
	public String getJobOpeningid() {
		return jobOpeningid;
	}
	/**
	 * @param jobOpeningid the jobOpeningid to set
	 */
	public void setJobOpeningid(String jobOpeningid) {
		this.jobOpeningid = jobOpeningid;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("JobOpeningChangeStatusDTO [jobOpeningid=");
		builder.append(jobOpeningid);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}
	
}
