package com.stl.iminds.candidate.resource;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

public class CandidateResultDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long candidateresultid;
	
	private Long candidateId;
	
	private Long jobOpeningId;
	
	private Long interviewtypeId;
	
	private String result;
	
	private String recording;
	
	private String formatType;
	/**
	 * @return the candidateresultid
	 */
	public Long getCandidateresultid() {
		return candidateresultid;
	}
	/**
	 * @param candidateresultid the candidateresultid to set
	 */
	public void setCandidateresultid(Long candidateresultid) {
		this.candidateresultid = candidateresultid;
	}
	/**
	 * @return the candidateId
	 */
	public Long getCandidateId() {
		return candidateId;
	}
	/**
	 * @param candidateId the candidateId to set
	 */
	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}
	/**
	 * @return the jobOpeningId
	 */
	public Long getJobOpeningId() {
		return jobOpeningId;
	}
	/**
	 * @param jobOpeningId the jobOpeningId to set
	 */
	public void setJobOpeningId(Long jobOpeningId) {
		this.jobOpeningId = jobOpeningId;
	}
	/**
	 * @return the interviewtypeId
	 */
	public Long getInterviewtypeId() {
		return interviewtypeId;
	}
	/**
	 * @param interviewtypeId the interviewtypeId to set
	 */
	public void setInterviewtypeId(Long interviewtypeId) {
		this.interviewtypeId = interviewtypeId;
	}
	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}
	/**
	 * @return the recording
	 */
	public String getRecording() {
		return recording;
	}
	/**
	 * @param recording the recording to set
	 */
	public void setRecording(String recording) {
		this.recording = recording;
	}
	/**
	 * @return the formatType
	 */
	public String getFormatType() {
		return formatType;
	}
	/**
	 * @param formatType the formatType to set
	 */
	public void setFormatType(String formatType) {
		this.formatType = formatType;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CandidateResult [candidateresultid=");
		builder.append(candidateresultid);
		builder.append(", candidateId=");
		builder.append(candidateId);
		builder.append(", jobOpeningId=");
		builder.append(jobOpeningId);
		builder.append(", interviewtypeId=");
		builder.append(interviewtypeId);
		builder.append(", result=");
		builder.append(result);
		builder.append(", recording=");
		builder.append(recording);
		builder.append(", formatType=");
		builder.append(formatType);
		builder.append("]");
		return builder.toString();
	}
	
}
