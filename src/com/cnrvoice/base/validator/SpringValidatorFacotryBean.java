package com.cnrvoice.base.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.cnrvoice.base.exception.ValidationException;

public class SpringValidatorFacotryBean extends LocalValidatorFactoryBean
{
	
	@Override
	protected void processConstraintViolations(
			Set<ConstraintViolation<Object>> violations, Errors errors)
	{
		super.processConstraintViolations(violations, errors);
		
		if (errors.hasErrors())
		{
			List<String> codeList = new ArrayList<String>();
			List<Object[]> argList = new ArrayList<Object[]>();
			List<String> contentList = new ArrayList<String>();
			for (FieldError error : errors.getFieldErrors())
			{
				codeList.add(error.getCodes()[0]);
				argList.add(error.getArguments());
				contentList.add(error.getDefaultMessage());
			}
			throw new ValidationException(codeList, argList, contentList);
		}
	}
	
}
