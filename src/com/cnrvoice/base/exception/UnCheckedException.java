/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-3-21 下午5:39:30
 * @Description:
 * 
 */
package com.cnrvoice.base.exception;

public class UnCheckedException extends RuntimeException
{
	private static final long serialVersionUID = 5238814353352216719L;
	
	// 异常编码
	private String code;
	
	private String content;
	
	private Object[] params;
	
	/**
	 * 根据code构造函数
	 * 
	 * @param code
	 */
	public UnCheckedException(String code)
	{
		super();
		this.code = code;
	}
	
	/**
	 * 根据code和params构造函数
	 * 
	 * @param code
	 */
	public UnCheckedException(String code, Object[] params)
	{
		super();
		this.code = code;
		this.params = params;
	}
	
	/**
	 * 根据code和content构造函数
	 * 
	 * @param code
	 * @param content
	 */
	public UnCheckedException(String code, String content)
	{
		super();
		this.code = code;
		this.content = content;
	}
	
	/**
	 * 根据code、content和cause构造函数
	 * 
	 * @param code
	 * @param content
	 * @param cause
	 */
	public UnCheckedException(String code, Object[] params, Throwable cause)
	{
		super(cause);
		this.code = code;
		this.params = params;
	}
	
	public String getCode()
	{
		return code;
	}
	
	public void setCode(String code)
	{
		this.code = code;
	}
	
	public String getContent()
	{
		return content;
	}
	
	public void setContent(String content)
	{
		this.content = content;
	}
	
	public Object[] getParams()
	{
		return params;
	}
	
	public void setParams(Object[] params)
	{
		this.params = params;
	}
}
