/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-4-11 下午8:18:18
 * @Description:
 * 
 */
package com.cnrvoice.base.binder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public class DateConverter implements Converter<String, Date>
{
	@Override
	public Date convert(String source)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		Date date = null;
		try
		{
			date = dateFormat.parse(source);
		}
		catch (ParseException e)
		{
			date = null;
		}
		return date;
	}
}
