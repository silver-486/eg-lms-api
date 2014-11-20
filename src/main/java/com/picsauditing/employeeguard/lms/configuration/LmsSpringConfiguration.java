package com.picsauditing.employeeguard.lms.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.velocity.VelocityAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

/**
 * Created by vladislav.tolkach on 11/20/2014.
 */
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages="com.picsauditing.employeeguard")
@EnableAutoConfiguration(exclude = VelocityAutoConfiguration.class)
@EntityScan(basePackages = "com.picsauditing.employeeguard.lms.model")
@EnableJpaRepositories("com.picsauditing.employeeguard.lms.dao")
@EnableWebMvc
@Import(SecurityConfiguration.class)
public class LmsSpringConfiguration extends WebMvcConfigurerAdapter {

    @Bean(name = "defaultViewResolver")
    public ViewResolver getViewResolver() {
        InternalResourceViewResolver ivr = new InternalResourceViewResolver();
//        /ivr.setViewClass(JstlView.class);
        ivr.setPrefix("/WEB-INF/jsp/");
        ivr.setSuffix(".jsp");
        return  ivr;
    }

}
