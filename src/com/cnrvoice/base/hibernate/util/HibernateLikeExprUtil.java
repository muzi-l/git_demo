/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2013-6-7 下午12:08:21
 * @Description:
 * 
 */
package com.cnrvoice.base.hibernate.util;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.LikeExpression;
import org.hibernate.criterion.MatchMode;

public class HibernateLikeExprUtil
{
	/**
	 * 默认转义符号
	 */
	private static final Character DefaultEscapeChar = '/';
	
	/**
	 * 默认忽略大小写
	 */
	private static final boolean DefaultIgnoreCase = false;
	
	/**
	 * 创建带匹配模式的Like查询的Criterion
	 * @param propertyName
	 * @param value
	 * @param matchMode
	 * @param escapeChar
	 * @param ignoreCase
	 * @return
	 */
	public static LikeExpression createMatchLikeExpr(String propertyName, String value, MatchMode matchMode, Character escapeChar, boolean ignoreCase)
	{
		return new LikeExpr(propertyName, escapeSQLLike(value, escapeChar), matchMode, escapeChar, ignoreCase);
	}
	
	/**
	 * 创建前匹配后模糊查询的Criterion
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public static LikeExpression createStartMatchLikeExpr(String propertyName, String value)
	{
		return new LikeExpr(propertyName, escapeSQLLike(value, DefaultEscapeChar), MatchMode.START, DefaultEscapeChar, DefaultIgnoreCase);
	}
	
	/**
	 * 创建前模糊后匹配查询的Criterion
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public static LikeExpression createEndMatchLikeExpression(String propertyName, String value)
	{
		return new LikeExpr(propertyName, escapeSQLLike(value, DefaultEscapeChar), MatchMode.END, DefaultEscapeChar, DefaultIgnoreCase);
	}
	
	/**
	 * 创建前模糊后模糊查询的Criterion
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public static LikeExpression createAnyWhereMatchLikeExpr(String propertyName, String value)
	{
		return new LikeExpr(propertyName, escapeSQLLike(value, DefaultEscapeChar), MatchMode.ANYWHERE, DefaultEscapeChar, DefaultIgnoreCase);
	}
	
	private static String escapeSQLLike(String value, Character escapeChar) {
		String str = StringUtils.replace(value, "_", escapeChar + "_");
		str = StringUtils.replace(str, "%", escapeChar + "%");
		str = StringUtils.replace(str, "[", escapeChar + "[");
		str = StringUtils.replace(str, "]", escapeChar + "]");
		return str;
	}
	
	private static class LikeExpr extends LikeExpression{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 6050747955893713606L;

		protected LikeExpr(String propertyName, String value, Character escapeChar, boolean ignoreCase) {
			super(propertyName, value, escapeChar, ignoreCase);
		}
		
		protected LikeExpr(String propertyName,String value,MatchMode matchMode,Character escapeChar,boolean ignoreCase) {
			super( propertyName, matchMode.toMatchString( value ), escapeChar, ignoreCase );
		}
	}
}
