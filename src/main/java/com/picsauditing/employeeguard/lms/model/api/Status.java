package com.picsauditing.employeeguard.lms.model.api;

public class Status {

  public static int OK = 0;
  public static int FAIl = 1;

  long refId;
  int statusCode;
  String data;

  public long getRefId() {
    return refId;
  }

  public void setRefId(long refId) {
    this.refId = refId;
  }

  public int getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(int statusCode) {
    this.statusCode = statusCode;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }
}
