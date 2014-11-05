package com.picsauditing.employeeguard.lms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.picsauditing.employeeguard.lms.model.api.MessageResponse;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;

import java.io.IOException;

public class MessageResponseHandler implements ResponseHandler<MessageResponse> {

  @Override
  public MessageResponse handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
    ObjectMapper mapper = new ObjectMapper();
    int statusCode = httpResponse.getStatusLine().getStatusCode();
    MessageResponse messageResponse = mapper.readValue(httpResponse.getEntity().getContent(), MessageResponse.class);
    messageResponse.setHttpStatusCode(statusCode);
    return messageResponse;
  }
}
