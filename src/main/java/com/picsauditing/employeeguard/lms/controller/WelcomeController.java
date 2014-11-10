package com.picsauditing.employeeguard.lms.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.Map;

@Controller
public class WelcomeController {

  @Value("${application.message:Hello World}")
  private String message = "Hello World";

  @RequestMapping("/")
  public String welcome(Map<String, Object> model) {
    model.put("time", new Date());
    model.put("message", this.message);
    return "welcome";
  }

  @RequestMapping("/index")
  public String foo(Map<String, Object> model) {
    return "index";
  }

}