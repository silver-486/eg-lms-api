package com.picsauditing.employeeguard.lms;

import com.picsauditing.employeeguard.lms.configuration.SecurityConfiguration;
import org.apache.log4j.LogManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan
@EnableAutoConfiguration
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


	public static void main(String[] args) {
		context = SpringApplication.run(applicationClass, args);
	}

	public LmsStandalone() {

	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(applicationClass);
	}

	private static Class<LmsStandalone> applicationClass = LmsStandalone.class;
}


