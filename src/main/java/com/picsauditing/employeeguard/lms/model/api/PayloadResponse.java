package com.picsauditing.employeeguard.lms.model.api;

public class PayloadResponse extends Payload {

  String responseData;

  public String getResponseData() {
    return responseData;
  }

  public void setResponseData(String responseData) {
    this.responseData = responseData;
  }

  public void mergePayload(Payload payload) {
    this.setId(payload.getId());
    this.setCommand(payload.getCommand());
    this.setData(payload.getData());
  }
}
