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
@Table(name = "TBLMCANDIDATEQUESTIONS")
public class CandidateQuestions implements Serializable { 
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "SEQ_CANDIDATEQUESTIONS", sequenceName = "SEQ_CANDIDATEQUESTIONS", initialValue=1, allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CANDIDATEQUESTIONS")
	private Long candidatequeid;
	
	private Long questionId;
	
	private String answer;
	
	@ManyToOne
    @JoinColumn(name="candidateId", nullable=false)
	private Candidates candidate;
	
	/**
	 * @return the candidatequeid
	 */
	public Long getCandidatequeid() {
		return candidatequeid;
	}
	/**
	 * @param candidatequeid the candidatequeid to set
	 */
	public void setCandidatequeid(Long candidatequeid) {
		this.candidatequeid = candidatequeid;
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
	 * @return the answer
	 */
	public String getAnswer() {
		return answer;
	}
	/**
	 * @param answer the answer to set
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
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
		builder.append("CandidateQuestions [candidatequeid=");
		builder.append(candidatequeid);
		builder.append(", questionId=");
		builder.append(questionId);
		builder.append(", answer=");
		builder.append(answer);
		builder.append("]");
		return builder.toString();
	}
	
}
