package com.picsauditing.employeeguard.lms;

import com.picsauditing.employeeguard.lms.configuration.SecurityConfiguration;
import org.apache.log4j.LogManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages="com.picsauditing.employeeguard")
@EnableAutoConfiguration
@EnableWebMvc
@EnableJpaRepositories("com.picsauditing.employeeguard.lms.dao")
@Import(SecurityConfiguration.class)
public class LmsStandalone extends SpringBootServletInitializer {

	final static org.apache.log4j.Logger logger = LogManager.getLogger(LmsStandalone.class);
	private static ConfigurableApplicationContext context;
/*
	@Bean
  public EntityManagerFactory entityManagerFactory() {
  }
*/


	public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet) {
		ServletRegistrationBean registration = new ServletRegistrationBean(
				dispatcherServlet);
		registration.addUrlMappings("/*");
		return registration;
	}

	@Bean
	public ViewResolver getViewResolver() {
		InternalResourceViewResolver ivr = new InternalResourceViewResolver();
		ivr.setPrefix("/WEB-INF/jsp/");
		ivr.setSuffix(".jsp");
		return  ivr;
	}


	public static void main(String[] args) {
		context = SpringApplication.run(applicationClass, args);
	}

	public LmsStandalone() {}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(applicationClass);
	}

	private static Class<LmsStandalone> applicationClass = LmsStandalone.class;
}


