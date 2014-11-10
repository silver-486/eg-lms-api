package com.picsauditing.employeeguard.lms.controller;

import com.jayway.restassured.RestAssured;
import com.picsauditing.employeeguard.lms.LmsStandalone;
import com.picsauditing.employeeguard.lms.dao.UserRepository;
import com.picsauditing.employeeguard.lms.main.Mocker;
import com.picsauditing.employeeguard.lms.model.User;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedWebApplicationContext;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static com.jayway.restassured.RestAssured.when;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LmsStandalone.class)
@WebAppConfiguration
@IntegrationTest
public class LmsControllerTest {

  @Autowired
  EmbeddedWebApplicationContext server;

  @Autowired
  UserRepository repository;

  @Autowired
  Mocker mocker;

  User user1;

  User user2;

  @Value("${local.server.port}")
  int port;

  RestTemplate template = new TestRestTemplate();

  @Test
  public void testRequest() throws Exception {
//    HttpHeaders headers = template.getForEntity("http://localhost:8080/testLmsApi", String.class).getHeaders();
    org.springframework.http.HttpStatus httpStatus = template.getForEntity("http://localhost:8080/testLmsApi", String.class).getStatusCode();
    System.out.println("httpStatus: "+httpStatus);

    Assert.assertEquals(org.springframework.http.HttpStatus.OK, httpStatus);
  }

  @Before
  public void setUp() {
    user1 = new User(1, "mike");
    user2 = new User(2, "john");

    repository.deleteAll();
    repository.save(Arrays.asList(user1, user2));

    RestAssured.port = port;
  }


  @Test
  public void testUpdate() throws Exception {

  }


  @Test
  public void canFetchUser() {
    Long userId = user1.getId();

    when().
      get("/user/{id}", userId).
      then().
      statusCode(HttpStatus.SC_OK).
      body("name", Matchers.is("mike")).
      body("id", Matchers.is(userId));
  }

  @Test
  public void canFetchAll() {
    when().
      get("/user").
      then().
      statusCode(HttpStatus.SC_OK).
      body("name", Matchers.hasItems("mike", "john"));
  }

  @Test
  public void canDeleteUser() {
    Long user1Id = user1.getId();

    when()
      .delete("/user/{id}", user1Id).
      then().
      statusCode(HttpStatus.SC_NO_CONTENT);
  }
}