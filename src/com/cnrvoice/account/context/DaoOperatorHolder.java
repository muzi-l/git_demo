package com.cnrvoice.account.context;

public class DaoOperatorHolder
{
	private static final ThreadLocal<String> daoOperatorHolder = new ThreadLocal<String>();
	
	public static void setOperatorKey(String key)
	{
		daoOperatorHolder.set(key);
	}
	
	public static String getOperatorKey()
	{
		return daoOperatorHolder.get();
	}
	
	public static void clearOperatorKey()
	{
		daoOperatorHolder.remove();
	}
}
