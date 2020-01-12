package com.stl.iminds.candidate.resource;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CandidatesDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long candidateid;
	
	private String name;
	
	private String email;
	
	private String mobile;
	
	private String resume;
	
	private String status;
	
	private String rating;
	
	private Date creationDate;
	
	private Date lastModifiedDate;
	
	private String jobOpening;
	
	private List<CandidateSkillsDTO> candidateSkills;
	
	private List<CandidateQuestionsDTO> candidateQuestions;
	
	private List<CandidateJobRelDTO> candidateJobRel;
	
	private List<CandidateResultDTO> candidateResults;
	
	private List<CandidateHistoryDTO> candidateHistory;
	
	private String jobSkillCriteria;
	
	/**
	 * @return the candidateid
	 */
	public Long getCandidateid() {
		return candidateid;
	}
	/**
	 * @param candidateid the candidateid to set
	 */
	public void setCandidateid(Long candidateid) {
		this.candidateid = candidateid;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * @return the resume
	 */
	public String getResume() {
		return resume;
	}
	/**
	 * @param resume the resume to set
	 */
	public void setResume(String resume) {
		this.resume = resume;
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
	/**
	 * @return the rating
	 */
	public String getRating() {
		return rating;
	}
	/**
	 * @param rating the rating to set
	 */
	public void setRating(String rating) {
		this.rating = rating;
	}
	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}
	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	/**
	 * @return the lastModifiedDate
	 */
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	/**
	 * @param lastModifiedDate the lastModifiedDate to set
	 */
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	
	/**
	 * @return the jobOpening
	 */
	public String getJobOpening() {
		return jobOpening;
	}
	/**
	 * @param jobOpening the jobOpening to set
	 */
	public void setJobOpening(String jobOpening) {
		this.jobOpening = jobOpening;
	}
	/**
	 * @return the candidateSkills
	 */
	public List<CandidateSkillsDTO> getCandidateSkills() {
		return candidateSkills;
	}
	/**
	 * @param candidateSkills the candidateSkills to set
	 */
	public void setCandidateSkills(List<CandidateSkillsDTO> candidateSkills) {
		this.candidateSkills = candidateSkills;
	}
	/**
	 * @return the candidateQuestions
	 */
	public List<CandidateQuestionsDTO> getCandidateQuestions() {
		return candidateQuestions;
	}
	/**
	 * @param candidateQuestions the candidateQuestions to set
	 */
	public void setCandidateQuestions(List<CandidateQuestionsDTO> candidateQuestions) {
		this.candidateQuestions = candidateQuestions;
	}
	/**
	 * @return the candidateJobRel
	 */
	public List<CandidateJobRelDTO> getCandidateJobRel() {
		return candidateJobRel;
	}
	/**
	 * @param candidateJobRel the candidateJobRel to set
	 */
	public void setCandidateJobRel(List<CandidateJobRelDTO> candidateJobRel) {
		this.candidateJobRel = candidateJobRel;
	}
	/**
	 * @return the candidateResults
	 */
	public List<CandidateResultDTO> getCandidateResults() {
		return candidateResults;
	}
	/**
	 * @param candidateResults the candidateResults to set
	 */
	public void setCandidateResults(List<CandidateResultDTO> candidateResults) {
		this.candidateResults = candidateResults;
	}
	/**
	 * @return the candidateHistory
	 */
	public List<CandidateHistoryDTO> getCandidateHistory() {
		return candidateHistory;
	}
	/**
	 * @param candidateHistory the candidateHistory to set
	 */
	public void setCandidateHistory(List<CandidateHistoryDTO> candidateHistory) {
		this.candidateHistory = candidateHistory;
	}
	
	public String getJobSkillCriteria() {
		return jobSkillCriteria;
	}
	public void setJobSkillCriteria(String jobSkillCriteria) {
		this.jobSkillCriteria = jobSkillCriteria;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CandidatesDTO [candidateid=");
		builder.append(candidateid);
		builder.append(", name=");
		builder.append(name);
		builder.append(", email=");
		builder.append(email);
		builder.append(", mobile=");
		builder.append(mobile);
		builder.append(", resume=");
		builder.append(resume);
		builder.append(", status=");
		builder.append(status);
		builder.append(", rating=");
		builder.append(rating);
		builder.append(", creationDate=");
		builder.append(creationDate);
		builder.append(", lastModifiedDate=");
		builder.append(lastModifiedDate);
		builder.append(", jobOpening=");
		builder.append(jobOpening);
		builder.append(", candidateSkills=");
		builder.append(candidateSkills);
		builder.append(", candidateQuestions=");
		builder.append(candidateQuestions);
		builder.append(", candidateJobRel=");
		builder.append(candidateJobRel);
		builder.append(", candidateResults=");
		builder.append(candidateResults);
		builder.append(", candidateHistory=");
		builder.append(candidateHistory);
		builder.append(", jobSkillCriteria=");
		builder.append(jobSkillCriteria);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
