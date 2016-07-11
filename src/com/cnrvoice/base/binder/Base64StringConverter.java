/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-5-7 下午4:53:22
 * @Description:
 * 
 */
package com.cnrvoice.base.binder;

import org.apache.commons.codec.binary.Base64;
import org.springframework.core.convert.converter.Converter;

public class Base64StringConverter implements Converter<String, String>
{
	@Override
	public String convert(String source)
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
