package com.stl.iminds.commons.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "TBLSINTERVIEWTYPE")
public class InterviewType implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "SEQ_INTERVIEWTYPE", sequenceName = "SEQ_INTERVIEWTYPE", initialValue=1, allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_INTERVIEWTYPE")
	private Long interviewtypeid;
	
	private String name;
	
	private String description;

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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InterviewType [interviewtypeid=");
		builder.append(interviewtypeid);
		builder.append(", name=");
		builder.append(name);
		builder.append(", description=");
		builder.append(description);
		builder.append("]");
		return builder.toString();
	}
	
	
}
