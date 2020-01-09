package com.stl.iminds.candidate.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "TBLMCANDIDATES")
public class Candidates implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "SEQ_CANDIDATES", sequenceName = "SEQ_CANDIDATES", initialValue=1, allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CANDIDATES")
	private Long candidateid;
	
	private String candidatename;
	
	private String email;
	
	private String mobile;
	
	@Lob
	private String candidateresume;
	
	private String status;
	
	private String rating;
	
	private Date creationDate;
	
	private Date lastModifiedDate;
	
	@OneToMany(mappedBy="candidate", cascade = CascadeType.ALL,orphanRemoval = true)
	private List<CandidateSkills> candidateSkills;
	
	@OneToMany(mappedBy="candidate", cascade = CascadeType.ALL,orphanRemoval = true)
	private List<CandidateQuestions> candidateQuestions;
	
	@OneToMany(mappedBy="candidate", cascade = CascadeType.ALL,orphanRemoval = true)
	private List<CandidateJobRel> candidateJobRel;
	
	@OneToMany(mappedBy="candidate", cascade = CascadeType.ALL,orphanRemoval = true)
	private List<CandidateResult> candidateResults;
	
	@OneToMany(mappedBy="candidate", cascade = CascadeType.ALL,orphanRemoval = true)
	private List<CandidateHistory> candidateHistory;
	
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
	public String getCandidateName() {
		return candidatename;
	}
	/**
	 * @param name the name to set
	 */
	public void setCandidateName(String candidatename) {
		this.candidatename = candidatename;
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
	public String getCandidateResume() {
		return candidateresume;
	}
	/**
	 * @param resume the resume to set
	 */
	public void setCandidateResume(String candidateresume) {
		this.candidateresume = candidateresume;
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
	 * @return the candidateSkills
	 */
	public List<CandidateSkills> getCandidateSkills() {
		return candidateSkills;
	}
	/**
	 * @param candidateSkills the candidateSkills to set
	 */
	public void setCandidateSkills(List<CandidateSkills> candidateSkills) {
		this.candidateSkills = candidateSkills;
	}
	/**
	 * @return the candidateQuestions
	 */
	public List<CandidateQuestions> getCandidateQuestions() {
		return candidateQuestions;
	}
	/**
	 * @param candidateQuestions the candidateQuestions to set
	 */
	public void setCandidateQuestions(List<CandidateQuestions> candidateQuestions) {
		this.candidateQuestions = candidateQuestions;
	}
	/**
	 * @return the candidateJobRel
	 */
	public List<CandidateJobRel> getCandidateJobRel() {
		return candidateJobRel;
	}
	/**
	 * @param candidateJobRel the candidateJobRel to set
	 */
	public void setCandidateJobRel(List<CandidateJobRel> candidateJobRel) {
		this.candidateJobRel = candidateJobRel;
	}
	/**
	 * @return the candidateResults
	 */
	public List<CandidateResult> getCandidateResults() {
		return candidateResults;
	}
	/**
	 * @param candidateResults the candidateResults to set
	 */
	public void setCandidateResults(List<CandidateResult> candidateResults) {
		this.candidateResults = candidateResults;
	}
	/**
	 * @return the candidateHistory
	 */
	public List<CandidateHistory> getCandidateHistory() {
		return candidateHistory;
	}
	/**
	 * @param candidateHistory the candidateHistory to set
	 */
	public void setCandidateHistory(List<CandidateHistory> candidateHistory) {
		this.candidateHistory = candidateHistory;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Candidates [candidateid=");
		builder.append(candidateid);
		builder.append(", candidatename=");
		builder.append(candidatename);
		builder.append(", email=");
		builder.append(email);
		builder.append(", mobile=");
		builder.append(mobile);
		builder.append(", candidateresume=");
		builder.append(candidateresume);
		builder.append(", status=");
		builder.append(status);
		builder.append(", rating=");
		builder.append(rating);
		builder.append(", creationDate=");
		builder.append(creationDate);
		builder.append(", lastModifiedDate=");
		builder.append(lastModifiedDate);
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
		builder.append("]");
		return builder.toString();
	}
	
}
