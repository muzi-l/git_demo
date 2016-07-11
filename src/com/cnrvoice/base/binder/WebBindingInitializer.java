/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-3-22 下午5:28:45
 * @Description:
 * 
 */
package com.cnrvoice.base.binder;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.context.request.WebRequest;

public class WebBindingInitializer implements
		org.springframework.web.bind.support.WebBindingInitializer
{
	@Override
	public void initBinder(WebDataBinder binder, WebRequest request)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}
}
