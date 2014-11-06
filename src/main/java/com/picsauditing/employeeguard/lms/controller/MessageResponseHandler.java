package com.picsauditing.employeeguard.lms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.picsauditing.employeeguard.lms.model.api.MessageResponse;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MessageResponseHandler implements ResponseHandler<MessageResponse> {

  @Override
  public MessageResponse handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
    ObjectMapper mapper = new ObjectMapper();
    int statusCode = httpResponse.getStatusLine().getStatusCode();

    HttpEntity entity = httpResponse.getEntity();

    String json = convertStreamToString(entity.getContent());
    System.out.println("message response: " + json);


//    MessageResponse messageResponse = mapper.readValue(entity.getContent(), MessageResponse.class);
    MessageResponse messageResponse = mapper.readValue(json, MessageResponse.class);
    messageResponse.setHttpStatusCode(statusCode);

    org.apache.http.Header[] headers = httpResponse.getAllHeaders();


    return messageResponse;
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
