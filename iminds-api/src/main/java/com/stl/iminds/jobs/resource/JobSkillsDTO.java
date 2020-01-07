package com.stl.iminds.jobs.resource;

import java.io.Serializable;

public class JobSkillsDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long jobskillid;
	private String type;
	private String name;
	private String description;
	private Long minimumExp;
	/**
	 * @return the jobskillid
	 */
	public Long getJobskillid() {
		return jobskillid;
	}
	/**
	 * @param jobskillid the jobskillid to set
	 */
	public void setJobskillid(Long jobskillid) {
		this.jobskillid = jobskillid;
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
	/**
	 * @return the minimumExp
	 */
	public Long getMinimumExp() {
		return minimumExp;
	}
	/**
	 * @param minimumExp the minimumExp to set
	 */
	public void setMinimumExp(Long minimumExp) {
		this.minimumExp = minimumExp;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("JobSkillsDTO [jobskillid=");
		builder.append(jobskillid);
		builder.append(", type=");
		builder.append(type);
		builder.append(", name=");
		builder.append(name);
		builder.append(", description=");
		builder.append(description);
		builder.append(", minimumExp=");
		builder.append(minimumExp);
		builder.append("]");
		return builder.toString();
	}
	
	
}
