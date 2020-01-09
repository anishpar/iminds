package com.stl.iminds.candidate.resource;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

public class CandidateJobRelDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long candidatejobrelid;
	
	private Long candidateId;
	
	private Long jobOpeningId;
	
	/**
	 * @return the candidatejobrelid
	 */
	public Long getCandidatejobrelid() {
		return candidatejobrelid;
	}
	/**
	 * @param candidatejobrelid the candidatejobrelid to set
	 */
	public void setCandidatejobrelid(Long candidatejobrelid) {
		this.candidatejobrelid = candidatejobrelid;
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
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CandidateJobRel [candidatejobrelid=");
		builder.append(candidatejobrelid);
		builder.append(", candidateId=");
		builder.append(candidateId);
		builder.append(", jobOpeningId=");
		builder.append(jobOpeningId);
		builder.append("]");
		return builder.toString();
	}
}
