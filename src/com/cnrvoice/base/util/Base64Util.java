/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-5-7 下午5:29:07
 * @Description:
 * 
 */
package com.cnrvoice.base.util;

import org.apache.commons.codec.binary.Base64;

public class Base64Util
{
	public static String getDecodeString(String source)
	{
		String result = source;
		
		if (Base64.isBase64(source))
		{
			byte[] array = Base64.decodeBase64(source);
			result = new String(array);
		}
		
		return result;
	}
}
