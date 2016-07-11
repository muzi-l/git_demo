/**
 * Copyright (c) 2012 powered by CNRVoice
 * 
 * @author: CNR
 * @date: 2012-4-27 下午1:59:42
 * @Description:
 * 
 */
package com.cnrvoice.base.hibernate.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Query
{
	Class<?> entity();
}
