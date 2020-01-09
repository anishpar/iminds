package com.stl.iminds.candidate.resource;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

public class CandidateQuestionsDTO implements Serializable { 
	
	private static final long serialVersionUID = 1L;
	
	private Long candidatequeid;
	
	private Long candidateId;
	
	private Long questionId;
	
	private String answer;
	
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
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CandidateQuestions [candidatequeid=");
		builder.append(candidatequeid);
		builder.append(", candidateId=");
		builder.append(candidateId);
		builder.append(", questionId=");
		builder.append(questionId);
		builder.append(", answer=");
		builder.append(answer);
		builder.append("]");
		return builder.toString();
	}
	
}
