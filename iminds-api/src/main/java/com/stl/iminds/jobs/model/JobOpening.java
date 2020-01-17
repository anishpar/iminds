package com.stl.iminds.jobs.model;

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
@Table(name = "TBLMJOBOPENING")
public class JobOpening implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "SEQ_JOBOPENING", sequenceName = "SEQ_JOBOPENING", initialValue=1, allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_JOBOPENING")
	private Long jobOpeningid;
	
	private String title;
	
	private String jobStatus;
	
	private String hiringLead;
	
	private String department;
	
	private String employeeType;
	
	private Double minimumExp;
	
	@Lob
	private String jobDescription;
	
	private String location;
	
	private String city ;
	
	private String state;
	
	private String country;
	
	private String compensation;
	
	private Date creationDate;
	
	private Date lastModifiedDate;
	
	private String approvalStatus;

	@OneToMany(mappedBy="jobOpening", cascade = CascadeType.ALL,orphanRemoval = true)
	private List<JobSkills> jobSkills;
	
	@OneToMany(mappedBy="jobOpening", cascade = CascadeType.ALL,orphanRemoval = true)
	private List<JobQuestionsRel> jobQuestionsRels;
	
	@OneToMany(mappedBy="jobOpening", cascade = CascadeType.ALL,orphanRemoval = true)
	private List<JobInterviewRel> jobInterviewRels;
	
	/**
	 * @return the jobOpeningid
	 */
	public Long getJobOpeningid() {
		return jobOpeningid;
	}

	/**
	 * @param jobOpeningid the jobOpeningid to set
	 */
	public void setJobOpeningid(Long jobOpeningid) {
		this.jobOpeningid = jobOpeningid;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the jobStatus
	 */
	public String getJobStatus() {
		return jobStatus;
	}

	/**
	 * @param jobStatus the jobStatus to set
	 */
	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	/**
	 * @return the hiringLead
	 */
	public String getHiringLead() {
		return hiringLead;
	}

	/**
	 * @param hiringLead the hiringLead to set
	 */
	public void setHiringLead(String hiringLead) {
		this.hiringLead = hiringLead;
	}

	/**
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * @param department the department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
	}

	/**
	 * @return the employeeType
	 */
	public String getEmployeeType() {
		return employeeType;
	}

	/**
	 * @param employeeType the employeeType to set
	 */
	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}

	/**
	 * @return the minimumExp
	 */
	public Double getMinimumExp() {
		return minimumExp;
	}

	/**
	 * @param minimumExp the minimumExp to set
	 */
	public void setMinimumExp(Double minimumExp) {
		this.minimumExp = minimumExp;
	}

	/**
	 * @return the jobDescription
	 */
	public String getJobDescription() {
		return jobDescription;
	}

	/**
	 * @param jobDescription the jobDescription to set
	 */
	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the compensation
	 */
	public String getCompensation() {
		return compensation;
	}

	/**
	 * @param compensation the compensation to set
	 */
	public void setCompensation(String compensation) {
		this.compensation = compensation;
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
	 * @return the jobSkills
	 */
	public List<JobSkills> getJobSkills() {
		return jobSkills;
	}

	/**
	 * @param jobSkills the jobSkills to set
	 */
	public void setJobSkills(List<JobSkills> jobSkills) {
		this.jobSkills = jobSkills;
	}
	
	/**
	 * @return the jobQuestionsRels
	 */
	public List<JobQuestionsRel> getJobQuestionsRels() {
		return jobQuestionsRels;
	}

	/**
	 * @param jobQuestionsRels the jobQuestionsRels to set
	 */
	public void setJobQuestionsRels(List<JobQuestionsRel> jobQuestionsRels) {
		this.jobQuestionsRels = jobQuestionsRels;
	}

	/**
	 * @return the jobInterviewRels
	 */
	public List<JobInterviewRel> getJobInterviewRels() {
		return jobInterviewRels;
	}

	/**
	 * @param jobInterviewRels the jobInterviewRels to set
	 */
	public void setJobInterviewRels(List<JobInterviewRel> jobInterviewRels) {
		this.jobInterviewRels = jobInterviewRels;
	}
	
	/**
	 * @return the approvalStatus
	 */
	public String getApprovalStatus() {
		return approvalStatus;
	}

	/**
	 * @param approvalStatus the approvalStatus to set
	 */
	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("JobOpening [jobOpeningid=");
		builder.append(jobOpeningid);
		builder.append(", title=");
		builder.append(title);
		builder.append(", jobStatus=");
		builder.append(jobStatus);
		builder.append(", hiringLead=");
		builder.append(hiringLead);
		builder.append(", department=");
		builder.append(department);
		builder.append(", employeeType=");
		builder.append(employeeType);
		builder.append(", minimumExp=");
		builder.append(minimumExp);
		builder.append(", jobDescription=");
		builder.append(jobDescription);
		builder.append(", location=");
		builder.append(location);
		builder.append(", city=");
		builder.append(city);
		builder.append(", state=");
		builder.append(state);
		builder.append(", country=");
		builder.append(country);
		builder.append(", compensation=");
		builder.append(compensation);
		builder.append(", creationDate=");
		builder.append(creationDate);
		builder.append(", lastModifiedDate=");
		builder.append(lastModifiedDate);
		builder.append(", approvalStatus=");
		builder.append(approvalStatus);
		builder.append(", jobSkills=");
		builder.append(jobSkills);
		builder.append(", jobQuestionsRels=");
		builder.append(jobQuestionsRels);
		builder.append(", jobInterviewRels=");
		builder.append(jobInterviewRels);
		builder.append("]");
		return builder.toString();
	}
	
	
}
