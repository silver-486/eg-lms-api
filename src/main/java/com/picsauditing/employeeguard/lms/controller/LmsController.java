package com.picsauditing.employeeguard.lms.controller;

import com.picsauditing.employeeguard.lms.JSONHelper;
import com.picsauditing.employeeguard.lms.main.Mocker;
import com.picsauditing.employeeguard.lms.model.api.Message;
import com.picsauditing.employeeguard.lms.model.api.MessageResponse;
import com.picsauditing.employeeguard.lms.model.api.Payload;
import com.picsauditing.employeeguard.lms.service.LmsService;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class LmsController {

  @Autowired
  LmsService lmsService;

  @Autowired
  Mocker mocker;

  //http://localhost:8080/testLmsApi
  //entry point for testing
  @RequestMapping("/testLmsApi")
  public ResponseEntity<MessageResponse> testLmsApi() throws Exception {

    Message message = mocker.mockerMessageTest();

    String url = "http://localhost:8080/lmsApi";
    MessageResponse messageResponse = httpClientPostMessage(url, message);

    return new ResponseEntity<>(messageResponse, HttpStatus.OK);
  }


  private MessageResponse httpClientPostMessage(String url, Message message) throws Exception {
    CloseableHttpClient httpclient = HttpClientBuilder.create().build();

    HttpPost httpost = new HttpPost(url);

    String jsonMsg = JSONHelper.toJSONJackson(message);

    System.out.println("httpClientPostMessage: " + jsonMsg);

    StringEntity stringEntity = new StringEntity(jsonMsg);

    httpost.setEntity(stringEntity);
    httpost.setHeader("Accept", "application/json");
    httpost.setHeader("Content-type", "application/json");

    MessageResponseHandler responseHandler = new MessageResponseHandler();
    return httpclient.execute(httpost, responseHandler);
  }

  @RequestMapping("/lmsApi")
  public ResponseEntity<MessageResponse> receiveMessage(@RequestBody Message message) {

//    print(message);

    MessageResponse messageResponse = lmsService.processMessage(message);
    messageResponse.setStatusCode(0);

    return new ResponseEntity<>(messageResponse, HttpStatus.OK);
  }


  private void print(Message msg) {
    System.out.println("msg.id: " + msg.getId());
    for (Payload payload : msg.getPayloads()) {
      System.out.println(payload);
    }
  }
}
