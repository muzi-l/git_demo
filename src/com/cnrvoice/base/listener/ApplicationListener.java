package com.cnrvoice.base.listener;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ApplicationListener implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
        ServletContext application = sce.getServletContext();
        application.setAttribute("jsverson", new SimpleDateFormat("yyyyMMddHH").format(new Date()));
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}
