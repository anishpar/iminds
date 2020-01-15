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

import com.stl.iminds.jobs.model.JobOpening;

@Entity
@Table(name = "TBLMCANDIDATESKILLS")
public class CandidateSkills implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "SEQ_CANDIDATESKILLS", sequenceName = "SEQ_CANDIDATESKILLS", initialValue=1, allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CANDIDATESKILLS")
	private Long candidateskillid;
	
	private Long jobSkillId;
	
	private String description;
	
	private Double experience;

	@ManyToOne
    @JoinColumn(name="candidateId", nullable=false)
	private Candidates candidate;

	/**
	 * @return the candidateskillid
	 */
	public Long getCandidateskillid() {
		return candidateskillid;
	}

	/**
	 * @param candidateskillid the candidateskillid to set
	 */
	public void setCandidateskillid(Long candidateskillid) {
		this.candidateskillid = candidateskillid;
	}

	/**
	 * @return the jobSkillId
	 */
	public Long getJobSkillId() {
		return jobSkillId;
	}

	/**
	 * @param jobSkillId the jobSkillId to set
	 */
	public void setJobSkillId(Long jobSkillId) {
		this.jobSkillId = jobSkillId;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the experience
	 */
	public Double getExperience() {
		return experience;
	}

	/**
	 * @param experience the experience to set
	 */
	public void setExperience(Double experience) {
		this.experience = experience;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CandidateSkills [candidateskillid=");
		builder.append(candidateskillid);
		builder.append(", jobSkillId=");
		builder.append(jobSkillId);
		builder.append(", description=");
		builder.append(description);
		builder.append(", experience=");
		builder.append(experience);
		builder.append("]");
		return builder.toString();
	}
}
