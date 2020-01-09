package com.stl.iminds.candidate.resource;

import java.io.Serializable;
import java.util.Date;

public class CandidateHistoryDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long candidatehistoryid;
	
	private Long candidateId;
	
	private Date creationDate;
	
	private String event;
	
	private String oldValue; 
	
	private String newValue; 
	
	private String description;
	
	/**
	 * @return the candidatehistoryid
	 */
	public Long getCandidatehistoryid() {
		return candidatehistoryid;
	}
	/**
	 * @param candidatehistoryid the candidatehistoryid to set
	 */
	public void setCandidatehistoryid(Long candidatehistoryid) {
		this.candidatehistoryid = candidatehistoryid;
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
	 * @return the event
	 */
	public String getEvent() {
		return event;
	}
	/**
	 * @param event the event to set
	 */
	public void setEvent(String event) {
		this.event = event;
	}
	/**
	 * @return the oldValue
	 */
	public String getOldValue() {
		return oldValue;
	}
	/**
	 * @param oldValue the oldValue to set
	 */
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}
	/**
	 * @return the newValue
	 */
	public String getNewValue() {
		return newValue;
	}
	/**
	 * @param newValue the newValue to set
	 */
	public void setNewValue(String newValue) {
		this.newValue = newValue;
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
		builder.append("CandidateHistory [candidatehistoryid=");
		builder.append(candidatehistoryid);
		builder.append(", candidateId=");
		builder.append(candidateId);
		builder.append(", creationDate=");
		builder.append(creationDate);
		builder.append(", event=");
		builder.append(event);
		builder.append(", oldValue=");
		builder.append(oldValue);
		builder.append(", newValue=");
		builder.append(newValue);
		builder.append(", description=");
		builder.append(description);
		builder.append("]");
		return builder.toString();
	}
	
}
