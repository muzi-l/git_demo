package com.cnrvoice.cheba.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cnrvoice.base.hibernate.po.generic.anno.GenericAnnoUuidHibernatePo;

@Entity
@Table(name = "cheba_question_bank")
public class Questionbank extends GenericAnnoUuidHibernatePo
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String programUuid;
	private String questionTitle;
	private String difficultyType;
	private String questionContent;
	private String answer_A;
	private String answer_B;
	private String answer_C;
	private String answer_D;
	private String correctAnswer;
	
	private Boolean isDeleted;
	@Transient
	private String programNameStr;
	@Transient
	private Integer index; // 栏目的排序号
	
	public String getProgramUuid()
	{
		return programUuid;
	}
	
	public void setProgramUuid(String programUuid)
	{
		this.programUuid = programUuid;
	}
	
	public String getQuestionTitle()
	{
		return questionTitle;
	}
	
	public void setQuestionTitle(String questionTitle)
	{
		this.questionTitle = questionTitle;
	}
	
	public String getDifficultyType()
	{
		return difficultyType;
	}
	
	public void setDifficultyType(String difficultyType)
	{
		this.difficultyType = difficultyType;
	}
	
	public String getQuestionContent()
	{
		return questionContent;
	}
	
	public void setQuestionContent(String questionContent)
	{
		this.questionContent = questionContent;
	}
	
	public String getAnswer_A()
	{
		return answer_A;
	}
	
	public void setAnswer_A(String answer_A)
	{
		this.answer_A = answer_A;
	}
	
	public String getAnswer_B()
	{
		return answer_B;
	}
	
	public void setAnswer_B(String answer_B)
	{
		this.answer_B = answer_B;
	}
	
	public String getAnswer_C()
	{
		return answer_C;
	}
	
	public void setAnswer_C(String answer_C)
	{
		this.answer_C = answer_C;
	}
	
	public String getAnswer_D()
	{
		return answer_D;
	}
	
	public void setAnswer_D(String answer_D)
	{
		this.answer_D = answer_D;
	}
	
	public String getCorrectAnswer()
	{
		return correctAnswer;
	}
	
	public void setCorrectAnswer(String correctAnswer)
	{
		this.correctAnswer = correctAnswer;
	}
	
	public Boolean getIsDeleted()
	{
		return isDeleted;
	}
	
	public void setIsDeleted(Boolean isDeleted)
	{
		this.isDeleted = isDeleted;
	}
	
	public String getProgramNameStr()
	{
		return programNameStr;
	}
	
	public void setProgramNameStr(String programNameStr)
	{
		this.programNameStr = programNameStr;
	}
	
	public Integer getIndex()
	{
		return index;
	}
	
	public void setIndex(Integer index)
	{
		this.index = index;
	}
	
}
