package com.cnrvoice.cheba.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.cnrvoice.base.hibernate.po.generic.anno.GenericAnnoUuidHibernatePo;

@Entity
@Table(name = "cheba_answer_history")
public class AnswerHistory extends GenericAnnoUuidHibernatePo
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2920362611926680020L;

	private String programUuid;
	
	private String questionUuid;
	
	private String correctAnswer;
	
	private String answer_A_count;
	
	private String answer_B_count;
	
	private String answer_C_count;
	
	private String answer_D_count;

	public String getProgramUuid()
	{
		return programUuid;
	}

	public void setProgramUuid(String programUuid)
	{
		this.programUuid = programUuid;
	}

	public String getQuestionUuid()
	{
		return questionUuid;
	}

	public void setQuestionUuid(String questionUuid)
	{
		this.questionUuid = questionUuid;
	}

	public String getCorrectAnswer()
	{
		return correctAnswer;
	}

	public void setCorrectAnswer(String correctAnswer)
	{
		this.correctAnswer = correctAnswer;
	}

	public String getAnswer_A_count()
	{
		return answer_A_count;
	}

	public void setAnswer_A_count(String answer_A_count)
	{
		this.answer_A_count = answer_A_count;
	}

	public String getAnswer_B_count()
	{
		return answer_B_count;
	}

	public void setAnswer_B_count(String answer_B_count)
	{
		this.answer_B_count = answer_B_count;
	}

	public String getAnswer_C_count()
	{
		return answer_C_count;
	}

	public void setAnswer_C_count(String answer_C_count)
	{
		this.answer_C_count = answer_C_count;
	}

	public String getAnswer_D_count()
	{
		return answer_D_count;
	}

	public void setAnswer_D_count(String answer_D_count)
	{
		this.answer_D_count = answer_D_count;
	}
	
}
