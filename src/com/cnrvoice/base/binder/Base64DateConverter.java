/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-5-7 下午4:57:09
 * @Description:
 * 
 */
package com.cnrvoice.base.binder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.springframework.core.convert.converter.Converter;

public class Base64DateConverter implements Converter<String, Date>
{
	@Override
	public Date convert(String source)
	{
		String result = source;
		
		if (Base64.isBase64(source))
		{
			byte[] array = Base64.decodeBase64(source);
			result = new String(array);
		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		Date date = null;
		try
		{
			date = dateFormat.parse(result);
		}
		catch (ParseException e)
		{
			date = null;
		}
		return date;
	}
}
