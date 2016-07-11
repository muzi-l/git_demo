package com.cnrvoice.cheba.entity.vo;

import java.io.Serializable;

public class QuestionVo implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7693500354321295744L;
	
	private String uuid;
	
	private String programUuid;
	
	private String questionTitle;
	
	private Double questionBonus;
	
	private String difficultyType;
	
	private String questionContent;
	
	private String answer_A;
	
	private String answer_B;
	
	private String answer_C;
	
	private String answer_D;
	
	private int answerTime;
	
	private String compereName;
	
	private String currAnswererUuid;
	
	private String currAnswererName;

	public String getUuid()
	{
		return uuid;
	}

	public void setUuid(String uuid)
	{
		this.uuid = uuid;
	}

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

	public Double getQuestionBonus()
	{
		return questionBonus;
	}

	public void setQuestionBonus(Double questionBonus)
	{
		this.questionBonus = questionBonus;
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

	public int getAnswerTime()
	{
		return answerTime;
	}

	public void setAnswerTime(int answerTime)
	{
		this.answerTime = answerTime;
	}

	public String getCompereName()
	{
		return compereName;
	}

	public void setCompereName(String compereName)
	{
		this.compereName = compereName;
	}

	public String getCurrAnswererUuid()
	{
		return currAnswererUuid;
	}

	public void setCurrAnswererUuid(String currAnswererUuid)
	{
		this.currAnswererUuid = currAnswererUuid;
	}

	public String getCurrAnswererName()
	{
		return currAnswererName;
	}

	public void setCurrAnswererName(String currAnswererName)
	{
		this.currAnswererName = currAnswererName;
	}
	
}
