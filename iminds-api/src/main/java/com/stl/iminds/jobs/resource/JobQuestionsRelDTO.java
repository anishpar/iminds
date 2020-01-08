package com.stl.iminds.jobs.resource;

import java.io.Serializable;

public class JobQuestionsRelDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long jobquesionrelId;
	private String questionName;
	private Long questionId;
	private String isMandatory;
	private Long jobOpeningid;

	/**
	 * @return the jobquesionrelId
	 */
	public Long getJobquesionrelId() {
		return jobquesionrelId;
	}

	/**
	 * @param jobquesionrelId the jobquesionrelId to set
	 */
	public void setJobquesionrelId(Long jobquesionrelId) {
		this.jobquesionrelId = jobquesionrelId;
	}

	/**
	 * @return the questionName
	 */
	public String getQuestionName() {
		return questionName;
	}

	/**
	 * @param questionName the questionName to set
	 */
	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}

	/**
	 * @return the questionId
	 */
	public Long getQuestionId() {
		return questionId;
	}

	/**
	 * @param questionId the questionId to set
	 */
	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	/**
	 * @return the isMandatory
	 */
	public String getIsMandatory() {
		return isMandatory;
	}

	/**
	 * @param isMandatory the isMandatory to set
	 */
	public void setIsMandatory(String isMandatory) {
		this.isMandatory = isMandatory;
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
		builder.append("JobQuestionsRelDTO [jobquesionrelId=");
		builder.append(jobquesionrelId);
		builder.append(", questionName=");
		builder.append(questionName);
		builder.append(", questionId=");
		builder.append(questionId);
		builder.append(", isMandatory=");
		builder.append(isMandatory);
		builder.append(", jobOpeningid=");
		builder.append(jobOpeningid);
		builder.append("]");
		return builder.toString();
	}
	
	
}
