package com.picsauditing.employeeguard.lms;

import com.picsauditing.employeeguard.lms.model.User;
import com.picsauditing.employeeguard.lms.model.UserRepository;
import com.picsauditing.employeeguard.lms.service.LmsService;
import org.apache.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class LmsStandalone extends SpringBootServletInitializer {

  final static org.apache.log4j.Logger logger = LogManager.getLogger(LmsStandalone.class);

  public static void main(String[] args) {
    ConfigurableApplicationContext context = SpringApplication.run(applicationClass);

    UserRepository repository = context.getBean(UserRepository.class);
    repository.save(new User(1, "Jack"));

    Iterable<User> Users = repository.findAll();
    System.out.println("Users found with findAll():");
    System.out.println("-------------------------------");
    for (User User : Users) {
      System.out.println(User);
    }
    System.out.println();
  }

  public LmsStandalone() {

  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(applicationClass);
  }

  private static Class<LmsStandalone> applicationClass = LmsStandalone.class;
}


