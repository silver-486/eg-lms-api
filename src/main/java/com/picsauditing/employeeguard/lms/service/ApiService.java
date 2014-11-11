package com.picsauditing.employeeguard.lms.service;

import com.picsauditing.employeeguard.lms.JSONHelper;
import com.picsauditing.employeeguard.lms.controller.MessageResponseHandler;
import com.picsauditing.employeeguard.lms.model.api.Message;
import com.picsauditing.employeeguard.lms.model.api.MessageResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Service;

@Service
public class ApiService {

  public MessageResponse sendMessage(Message message) throws Exception {
    String url = "http://localhost:8080/lmsApi";
    return httpClientPostMessage(url, message);
  }


  private MessageResponse httpClientPostMessage(String url, Message message) throws Exception {
    CloseableHttpClient httpclient = HttpClientBuilder.create().build();

    HttpPost httpost = new HttpPost(url);

    String jsonMsg = JSONHelper.toJSON(message);

    System.out.println("httpClientPostMessage: " + jsonMsg);

    StringEntity stringEntity = new StringEntity(jsonMsg);

    httpost.setEntity(stringEntity);
    httpost.setHeader("Accept", "application/json");
    httpost.setHeader("Content-type", "application/json");

    MessageResponseHandler responseHandler = new MessageResponseHandler();
    return httpclient.execute(httpost, responseHandler);
  }

}
