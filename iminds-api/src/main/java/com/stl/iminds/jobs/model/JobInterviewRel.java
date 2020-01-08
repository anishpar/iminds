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
@Table(name = "TBLMJOBINTERVIEWREL")
public class JobInterviewRel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "SEQ_JOBINTERVIEWREL", sequenceName = "SEQ_JOBINTERVIEWREL", initialValue=1, allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_JOBINTERVIEWREL")
	private Long jobinterviewrelid;
	
	private Long interviewtypeid;
	
	private Long intervieworder;
	
	@ManyToOne
    @JoinColumn(name="jobOpeningid", nullable=false)
	private JobOpening jobOpening;

	/**
	 * @return the jobinterviewrelid
	 */
	public Long getJobinterviewrelid() {
		return jobinterviewrelid;
	}

	/**
	 * @param jobinterviewrelid the jobinterviewrelid to set
	 */
	public void setJobinterviewrelid(Long jobinterviewrelid) {
		this.jobinterviewrelid = jobinterviewrelid;
	}

	/**
	 * @return the interviewtypeid
	 */
	public Long getInterviewtypeid() {
		return interviewtypeid;
	}

	/**
	 * @param interviewtypeid the interviewtypeid to set
	 */
	public void setInterviewtypeid(Long interviewtypeid) {
		this.interviewtypeid = interviewtypeid;
	}

	/**
	 * @return the order
	 */
	public Long getInterviewOrder() {
		return intervieworder;
	}

	/**
	 * @param order the order to set
	 */
	public void setInterviewOrder(Long intervieworder) {
		this.intervieworder = intervieworder;
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
		builder.append("JobInterviewRel [jobinterviewrelid=");
		builder.append(jobinterviewrelid);
		builder.append(", interviewtypeid=");
		builder.append(interviewtypeid);
		builder.append(", intervieworder=");
		builder.append(intervieworder);
		builder.append("]");
		return builder.toString();
	}
}
