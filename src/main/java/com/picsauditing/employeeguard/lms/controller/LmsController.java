package com.picsauditing.employeeguard.lms.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.GsonBuilder;
import com.picsauditing.employeeguard.lms.JSONHelper;
import com.picsauditing.employeeguard.lms.main.Mocker;
import com.picsauditing.employeeguard.lms.model.User;
import com.picsauditing.employeeguard.lms.model.api.*;
import com.picsauditing.employeeguard.lms.service.LmsService;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.lang.reflect.Modifier;
import java.util.*;

import static com.picsauditing.employeeguard.lms.model.api.Command.ADD_USER;

@RestController
class LmsController {

  @Autowired
  LmsService lmsService;

  @Autowired
  Mocker mocker;

  //entry point for testing
  @RequestMapping("/testLms")
  public ResponseEntity<MessageResponse> callLms() throws Exception {

    Message message = mocker.mockerMessageTest();

    String url = "http://localhost:8080/postMsg";
    MessageResponse messageResponse = postMessage(url, message);

    return new ResponseEntity<>(messageResponse, HttpStatus.OK);
  }


  @RequestMapping("/postMsg")
  public ResponseEntity<Message> sendMessage(@RequestBody Message msg) {

    print(msg);

    MessageResponse responseMessage = new MessageResponse();
    Response response = new ResponseImpl();
    response.setId(1);
    response.setStatus("ok");

    Set<Response> responses = new HashSet<>();
    responseMessage.setResponses(responses);

    //lmsService.test();

    return new ResponseEntity<Message>(msg, HttpStatus.OK);
  }

  private void print(Message msg) {
    System.out.println("msg.id: "+msg.getId());
    for (Payload payload : msg.getPayloads()) {
      System.out.println(payload);
    }
  }

  public MessageResponse postMessage(String url, Message message) throws Exception {
    CloseableHttpClient httpclient = HttpClientBuilder.create().build();

    HttpPost httpost = new HttpPost(url);

    String jsonMsg = JSONHelper.toJSONJackson(message);

    System.out.println("postMessage: "+jsonMsg);

    StringEntity stringEntity = new StringEntity(jsonMsg);

    httpost.setEntity(stringEntity);
    httpost.setHeader("Accept", "application/json");
    httpost.setHeader("Content-type", "application/json");

    ResponseHandler responseHandler = new MessageResponseHandler();
    MessageResponse messageResponse = (MessageResponse) httpclient.execute(httpost, responseHandler);

    return messageResponse;
  }

  protected String sendPostMsg(String url, String json) throws UnsupportedEncodingException {

    CloseableHttpClient httpclient = HttpClientBuilder.create().build();

    HttpPost httpost = new HttpPost(url);

    StringEntity se = new StringEntity(json);

    httpost.setEntity(se);
    httpost.setHeader("Accept", "application/json");
    httpost.setHeader("Content-type", "application/json");

    HttpResponse response;
    String result = null;
    try {
      ResponseHandler responseHandler = new BasicResponseHandler();
      response = (HttpResponse) httpclient.execute(httpost, responseHandler);

      HttpEntity entity = response.getEntity();

      if (entity != null) {

        // A Simple JSON Response Read
        InputStream instream = entity.getContent();
        result = convertStreamToString(instream);
        // now you have the string representation of the HTML request
        System.out.println("RESPONSE: " + result);
        instream.close();
        int statusCode = response.getStatusLine().getStatusCode();

        System.out.println("statusCode: "+ statusCode);
        if (statusCode == 200) {
        }

      }
      // Headers
      org.apache.http.Header[] headers = response.getAllHeaders();
      for (Header header : headers) {
        System.out.println(header);
      }
    } catch (IOException e1) {
      e1.printStackTrace();
    }
    return result;
  }

  private static String convertStreamToString(InputStream is) {

    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
    StringBuilder sb = new StringBuilder();

    String line = null;
    try {
      while ((line = reader.readLine()) != null) {
        sb.append(line).append("\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        is.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return sb.toString();
  }

}
