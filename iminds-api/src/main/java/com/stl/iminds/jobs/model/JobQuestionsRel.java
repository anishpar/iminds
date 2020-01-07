package com.stl.iminds.jobs.model;

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
@Table(name = "TBLMJOBQUESTIONSREL")
public class JobQuestionsRel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "SEQ_JOBQUESTIONSREL", sequenceName = "SEQ_JOBQUESTIONSREL", initialValue=1, allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_JOBQUESTIONSREL")
	private Long jobquesionrelId;
	
	private Long questionId;
	
	private String isMandatory;
	
	@ManyToOne
    @JoinColumn(name="jobOpeningid", nullable=false)
	private JobOpening jobOpening;

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
	 * @return the jobOpening
	 */
	public JobOpening getJobOpening() {
		return jobOpening;
	}

	/**
	 * @param jobOpening the jobOpening to set
	 */
	public void setJobOpening(JobOpening jobOpening) {
		this.jobOpening = jobOpening;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("JobQuestionsRel [jobquesionrelId=");
		builder.append(jobquesionrelId);
		builder.append(", questionId=");
		builder.append(questionId);
		builder.append(", isMandatory=");
		builder.append(isMandatory);
		builder.append(", jobOpening=");
		builder.append(jobOpening);
		builder.append("]");
		return builder.toString();
	}
	
	
}
