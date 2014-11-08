package com.picsauditing.employeeguard.lms.model.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageResponse {

  private long refId;

  private long statusCode;

  @JsonProperty("status")
  private Set<Status> statuses = new HashSet<>();

  private int httpStatusCode;


  public long getRefId() {
    return refId;
  }

  public void setRefId(long refId) {
    this.refId = refId;
  }

  public long getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(long statusCode) {
    this.statusCode = statusCode;
  }

  public Set<Status> getStatuses() {
    return statuses;
  }

  public void setStatuses(Set<Status> statuses) {
    this.statuses = statuses;
  }

  public int getHttpStatusCode() {
    return httpStatusCode;
  }

  public void setHttpStatusCode(int httpStatusCode) {
    this.httpStatusCode = httpStatusCode;
  }
}
