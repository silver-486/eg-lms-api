package com.picsauditing.employeeguard.lms.model.api;

public class Status {

  public static int OK;
  public static int FAIL;

  long refId;
  int statusCode;

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
}
