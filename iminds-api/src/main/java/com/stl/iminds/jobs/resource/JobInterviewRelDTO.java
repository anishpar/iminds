package com.stl.iminds.jobs.resource;

import java.io.Serializable;

public class JobInterviewRelDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long jobinterviewrelid;
	private String interviewTypeName;
	private Long interviewTypeId;
	private Long interviewOrder;
	private Long jobOpeningid;

	/**
	 * @return the jobinterviewrelid
	 */
	public Long getJobinterviewrelid() {
		return jobinterviewrelid;
	}

	/**
	 * @param jobinterviewrelid the jobinterviewrelid to set
	 */
	public void setJobinterviewrelid(Long jobinterviewrelid) {
		this.jobinterviewrelid = jobinterviewrelid;
	}

	/**
	 * @return the interviewTypeName
	 */
	public String getInterviewTypeName() {
		return interviewTypeName;
	}

	/**
	 * @param interviewtypeid the interviewTypeName to set
	 */
	public void setInterviewTypeName(String interviewTypeName) {
		this.interviewTypeName = interviewTypeName;
	}

	/**
	 * @return the interviewTypeId
	 */
	public Long getInterviewTypeId() {
		return interviewTypeId;
	}

	/**
	 * @param interviewTypeId the interviewTypeId to set
	 */
	public void setInterviewTypeId(Long interviewTypeId) {
		this.interviewTypeId = interviewTypeId;
	}

	/**
	 * @return the order
	 */
	public Long getInterviewOrder() {
		return interviewOrder;
	}

	/**
	 * @param order the order to set
	 */
	public void setInterviewOrder(Long interviewOrder) {
		this.interviewOrder = interviewOrder;
	}

	/**
	 * @return the jobOpeningid
	 */
	public Long getJobOpeningid() {
		return jobOpeningid;
	}

	/**
	 * @param jobOpeningid the jobOpeningid to set
	 */
	public void setJobOpeningid(Long jobOpeningid) {
		this.jobOpeningid = jobOpeningid;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("JobInterviewRelDTO [jobinterviewrelid=");
		builder.append(jobinterviewrelid);
		builder.append(", interviewTypeName=");
		builder.append(interviewTypeName);
		builder.append(", interviewTypeId=");
		builder.append(interviewTypeId);
		builder.append(", interviewOrder=");
		builder.append(interviewOrder);
		builder.append(", jobOpeningid=");
		builder.append(jobOpeningid);
		builder.append("]");
		return builder.toString();
	}
}
