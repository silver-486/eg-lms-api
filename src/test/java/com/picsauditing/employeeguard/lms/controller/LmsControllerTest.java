package com.picsauditing.employeeguard.lms.controller;

import com.jayway.restassured.RestAssured;
import com.picsauditing.employeeguard.lms.JSONHelper;
import com.picsauditing.employeeguard.lms.LmsStandalone;
import com.picsauditing.employeeguard.lms.dao.UserRepository;
import com.picsauditing.employeeguard.lms.model.User;
import com.picsauditing.employeeguard.lms.model.api.Message;
import com.picsauditing.employeeguard.lms.model.api.Payload;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedWebApplicationContext;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.jayway.restassured.RestAssured.when;
import static com.picsauditing.employeeguard.lms.model.api.Command.ADD_USER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LmsStandalone.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class LmsControllerTest {

  @Autowired
  EmbeddedWebApplicationContext server;

  @Autowired
  UserRepository repository;

  User user1;

  User user2;

  @Value("${local.server.port}")
  int port;

  RestTemplate template = new TestRestTemplate();

  @Test
  public void testRequest() throws Exception {
    HttpHeaders headers = template.getForEntity("http://localhost:8080/testLms", String.class).getHeaders();
//    assertThat(headers.getLocation().toString(), containsString("myotherhost"));
  }

  @Before
  public void setUp() {
    user1 = new User(1, "mike");
    user1 = new User(2, "john");

    repository.deleteAll();
    repository.save(Arrays.asList(user1, user2));

    RestAssured.port = port;
  }


  @Test
  public void testUpdate() throws Exception {

  }

  private Message mockerMessageTest() {
    Message message = new Message();
    message.setId((long) 1);
    Payload payload = new Payload();

    User user1 = new User(1, "mike");

    payload.setCommand(ADD_USER);
    payload.setData(JSONHelper.toJSON(user1));


    Set<Payload> payloads = new HashSet<>();
    payloads.add(payload);
    message.setPayloads(payloads);
    return message;
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