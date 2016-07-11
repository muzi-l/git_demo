package com.cnrvoice.cheba.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cnrvoice.base.hibernate.po.generic.anno.GenericAnnoUuidHibernatePo;

@Entity
@Table(name = "cheba_program")
public class Program extends GenericAnnoUuidHibernatePo
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String status;
	
	private String programName;
	
	private String programPeriods;
	
	private Date programTime;
	
	private Double pass_1_bonus; // 第一关奖金
	
	private Integer pass_1_bonusAllotPopulation; // 第一关奖金分配人数
	
	private Double pass_2_bonus; // 第二关奖金
	
	private Integer pass_2_bonusAllotPopulation; // 第二关奖金分配人数
	
	private Double pass_3_bonus; // 第三关奖金
	
	private Integer pass_3_bonusAllotPopulation; // 第三关奖金分配人数
	
	private Integer answerTime; // 答题时间
	
	private String compereName;
	
	private String answer_1_Uuid;
	
	private String answer_1_Name;
	
	private String answer_2_Uuid;
	
	private String answer_2_Name;
	
	private String answer_3_Uuid;
	
	private String answer_3_Name;
	
	@Transient
	private String questionbankCount;
	
	public String getStatus()
	{
		return status;
	}
	
	public void setStatus(String status)
	{
		this.status = status;
	}
	
	public String getProgramName()
	{
		return programName;
	}
	
	public void setProgramName(String programName)
	{
		this.programName = programName;
	}
	
	public String getProgramPeriods()
	{
		return programPeriods;
	}
	
	public void setProgramPeriods(String programPeriods)
	{
		this.programPeriods = programPeriods;
	}
	
	public Date getProgramTime()
	{
		return programTime;
	}
	
	public void setProgramTime(Date programTime)
	{
		this.programTime = programTime;
	}
	
	public Double getPass_1_bonus()
	{
		return pass_1_bonus;
	}
	
	public void setPass_1_bonus(Double pass_1_bonus)
	{
		this.pass_1_bonus = pass_1_bonus;
	}
	
	public Integer getPass_1_bonusAllotPopulation()
	{
		return pass_1_bonusAllotPopulation;
	}
	
	public void setPass_1_bonusAllotPopulation(
			Integer pass_1_bonusAllotPopulation)
	{
		this.pass_1_bonusAllotPopulation = pass_1_bonusAllotPopulation;
	}
	
	public Double getPass_2_bonus()
	{
		return pass_2_bonus;
	}
	
	public void setPass_2_bonus(Double pass_2_bonus)
	{
		this.pass_2_bonus = pass_2_bonus;
	}
	
	public Integer getPass_2_bonusAllotPopulation()
	{
		return pass_2_bonusAllotPopulation;
	}
	
	public void setPass_2_bonusAllotPopulation(
			Integer pass_2_bonusAllotPopulation)
	{
		this.pass_2_bonusAllotPopulation = pass_2_bonusAllotPopulation;
	}
	
	public Double getPass_3_bonus()
	{
		return pass_3_bonus;
	}
	
	public void setPass_3_bonus(Double pass_3_bonus)
	{
		this.pass_3_bonus = pass_3_bonus;
	}
	
	public Integer getPass_3_bonusAllotPopulation()
	{
		return pass_3_bonusAllotPopulation;
	}
	
	public void setPass_3_bonusAllotPopulation(
			Integer pass_3_bonusAllotPopulation)
	{
		this.pass_3_bonusAllotPopulation = pass_3_bonusAllotPopulation;
	}
	
	public Integer getAnswerTime()
	{
		return answerTime;
	}
	
	public void setAnswerTime(Integer answerTime)
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
	
	public String getAnswer_1_Uuid()
	{
		return answer_1_Uuid;
	}
	
	public void setAnswer_1_Uuid(String answer_1_Uuid)
	{
		this.answer_1_Uuid = answer_1_Uuid;
	}
	
	public String getAnswer_1_Name()
	{
		return answer_1_Name;
	}
	
	public void setAnswer_1_Name(String answer_1_Name)
	{
		this.answer_1_Name = answer_1_Name;
	}
	
	public String getAnswer_2_Uuid()
	{
		return answer_2_Uuid;
	}
	
	public void setAnswer_2_Uuid(String answer_2_Uuid)
	{
		this.answer_2_Uuid = answer_2_Uuid;
	}
	
	public String getAnswer_2_Name()
	{
		return answer_2_Name;
	}
	
	public void setAnswer_2_Name(String answer_2_Name)
	{
		this.answer_2_Name = answer_2_Name;
	}
	
	public String getAnswer_3_Uuid()
	{
		return answer_3_Uuid;
	}
	
	public void setAnswer_3_Uuid(String answer_3_Uuid)
	{
		this.answer_3_Uuid = answer_3_Uuid;
	}
	
	public String getAnswer_3_Name()
	{
		return answer_3_Name;
	}
	
	public void setAnswer_3_Name(String answer_3_Name)
	{
		this.answer_3_Name = answer_3_Name;
	}
	
	public String getQuestionbankCount()
	{
		return questionbankCount;
	}
	
	public void setQuestionbankCount(String questionbankCount)
	{
		this.questionbankCount = questionbankCount;
	}
	
}
