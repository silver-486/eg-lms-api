package com.picsauditing.employeeguard.lms.model.api;

public class SyncHelper {

  public static PayloadResponse connectToPayload(Status status) {
    PayloadResponse payloadResponse = new PayloadResponse();
    //todo retrieve payload from data store

    payloadResponse.setData(status.getData());

    return payloadResponse;
  }

}
