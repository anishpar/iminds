package com.stl.iminds.commons.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "TBLSQUESTIONS")
public class Questions implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "SEQ_QUESTIONS", sequenceName = "SEQ_QUESTIONS", initialValue=1, allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_QUESTIONS")
	private Long questionid;
	private String question;
	private String dataType;
	private String validationRegExp;
	
	/**
	 * @return the questionid
	 */
	public Long getQuestionid() {
		return questionid;
	}
	/**
	 * @param questionid the questionid to set
	 */
	public void setQuestionid(Long questionid) {
		this.questionid = questionid;
	}
	/**
	 * @return the question
	 */
	public String getQuestion() {
		return question;
	}
	/**
	 * @param question the question to set
	 */
	public void setQuestion(String question) {
		this.question = question;
	}
	/**
	 * @return the dataType
	 */
	public String getDataType() {
		return dataType;
	}
	/**
	 * @param dataType the dataType to set
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	/**
	 * @return the validationRegExp
	 */
	public String getValidationRegExp() {
		return validationRegExp;
	}
	/**
	 * @param validationRegExp the validationRegExp to set
	 */
	public void setValidationRegExp(String validationRegExp) {
		this.validationRegExp = validationRegExp;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Questions [questionid=");
		builder.append(questionid);
		builder.append(", question=");
		builder.append(question);
		builder.append(", dataType=");
		builder.append(dataType);
		builder.append(", validationRegExp=");
		builder.append(validationRegExp);
		builder.append("]");
		return builder.toString();
	}
	
}
