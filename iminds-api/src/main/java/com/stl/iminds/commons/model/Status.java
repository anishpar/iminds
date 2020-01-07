package com.stl.iminds.commons.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "TBLSSTATUS")
public class Status  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "SEQ_STATUS", sequenceName = "SEQ_STATUS", initialValue=1, allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_STATUS")
	private Long statusid;
	
	private String type;
	
	private String status;
	
	private String substatus;

	/**
	 * @return the statusid
	 */
	public Long getStatusid() {
		return statusid;
	}

	/**
	 * @param statusid the statusid to set
	 */
	public void setStatusid(Long statusid) {
		this.statusid = statusid;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
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
	 * @return the substatus
	 */
	public String getSubstatus() {
		return substatus;
	}

	/**
	 * @param substatus the substatus to set
	 */
	public void setSubstatus(String substatus) {
		this.substatus = substatus;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Status [statusid=");
		builder.append(statusid);
		builder.append(", type=");
		builder.append(type);
		builder.append(", status=");
		builder.append(status);
		builder.append(", substatus=");
		builder.append(substatus);
		builder.append("]");
		return builder.toString();
	}
	
	
}
