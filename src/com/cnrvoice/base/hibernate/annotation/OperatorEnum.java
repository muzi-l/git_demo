/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-4-27 下午3:13:47
 * @Description:
 * 
 */
package com.cnrvoice.base.hibernate.annotation;

public enum OperatorEnum
{
	/** equal,等于,"=" */
	EQ,
	
	/** not equal,不等于,"<>"/"!=" */
	NE,
	
	/** greater than,大于,">" */
	GT,
	
	/** greater than or equal,大于等于,">=" */
	GE,
	
	/** less than,小于,"<" */
	LT,
	
	/** less than or equal,小于等于,"<=" */
	LE,
	
	/** like,后模糊匹配,"like ..%" */
	LIKE,
	
	/** in,在...内,"in (...)" */
	IN
}
