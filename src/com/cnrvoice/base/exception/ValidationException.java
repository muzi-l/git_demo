/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-10-9 上午9:44:22
 * @Description:
 * 
 */
package com.cnrvoice.base.exception;

import java.util.List;

public class ValidationException extends RuntimeException
{
	
	private static final long serialVersionUID = 8161732381877474658L;
	
	// 异常编码
	private List<String> codeList;
	
	private List<String> contentList;
	
	private List<Object[]> paramList;
	
	/**
	 * 根据code和message构造函数
	 * 
	 * @param codeList
	 */
	public ValidationException(List<String> codeList)
	{
		super();
		this.codeList = codeList;
	}
	
	/**
	 * 根据codeList和contentList构造函数
	 * 
	 * @param codeList
	 * @param contentList
	 */
	public ValidationException(List<String> codeList, List<String> contentList)
	{
		super();
		this.codeList = codeList;
		this.contentList = contentList;
	}
	
	/**
	 * 根据codeList、paramList和contentList构造函数
	 * 
	 * @param codeList
	 * @param paramList
	 * @param contentList
	 */
	public ValidationException(List<String> codeList, List<Object[]> paramList,
			List<String> contentList)
	{
		super();
		this.codeList = codeList;
		this.paramList = paramList;
		this.contentList = contentList;
	}
	
	public List<String> getCodeList()
	{
		return codeList;
	}
	
	public void setCodeList(List<String> codeList)
	{
		this.codeList = codeList;
	}
	
	public List<String> getContentList()
	{
		return contentList;
	}
	
	public void setContentList(List<String> contentList)
	{
		this.contentList = contentList;
	}
	
	public List<Object[]> getParamList()
	{
		return paramList;
	}
	
	public void setParamList(List<Object[]> paramList)
	{
		this.paramList = paramList;
	}
}
