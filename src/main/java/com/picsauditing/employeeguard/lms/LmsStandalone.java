package com.picsauditing.employeeguard.lms;

import com.picsauditing.employeeguard.lms.configuration.LmsSpringConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class LmsStandalone extends SpringBootServletInitializer {

	final static Logger logger = LoggerFactory.getLogger(LmsStandalone.class);

	private static ConfigurableApplicationContext context;

	public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet) {
		ServletRegistrationBean registration = new ServletRegistrationBean(	dispatcherServlet);
		registration.addUrlMappings("/**");
		return registration;
	}

	public static void main(String[] args) {
		context = SpringApplication.run(appConfigurationClass, args);
		String[] beanNames = context.getBeanDefinitionNames();
		for (String beanName: beanNames )
			logger.debug("bean in context: {}", beanName);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(appConfigurationClass);
	}

	private static Class<LmsSpringConfiguration> appConfigurationClass = LmsSpringConfiguration.class;
}


