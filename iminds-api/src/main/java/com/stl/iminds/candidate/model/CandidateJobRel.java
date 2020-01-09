package com.stl.iminds.candidate.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "TBLMCANDIDATEJOBREL")
public class CandidateJobRel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "SEQ_CANDIDATEJOBREL", sequenceName = "SEQ_CANDIDATEJOBREL", initialValue=1, allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CANDIDATEJOBREL")
	private Long candidatejobrelid;
	
	private Long jobOpeningId;
	
	@ManyToOne
    @JoinColumn(name="candidateId", nullable=false)
	private Candidates candidate;
	
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
	 * @return the candidate
	 */
	public Candidates getCandidate() {
		return candidate;
	}
	/**
	 * @param candidate the candidate to set
	 */
	public void setCandidate(Candidates candidate) {
		this.candidate = candidate;
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
		builder.append(", jobOpeningId=");
		builder.append(jobOpeningId);
		builder.append("]");
		return builder.toString();
	}
}
