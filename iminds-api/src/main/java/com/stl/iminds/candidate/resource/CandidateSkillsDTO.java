package com.stl.iminds.candidate.resource;

import java.io.Serializable;

public class CandidateSkillsDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long candidateskillid;
	
	private Long candidateId;
	
	private Long jobSkillId;
	
	private String description;
	
	private Long experience;

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
	public Long getExperience() {
		return experience;
	}

	/**
	 * @param experience the experience to set
	 */
	public void setExperience(Long experience) {
		this.experience = experience;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CandidateSkills [candidateskillid=");
		builder.append(candidateskillid);
		builder.append(", candidateId=");
		builder.append(candidateId);
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
