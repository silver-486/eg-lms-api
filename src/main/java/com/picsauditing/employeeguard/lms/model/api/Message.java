package com.picsauditing.employeeguard.lms.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public class Message {

  private Long id;

  @JsonProperty("payload")
  private Set<Payload> payloads;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Set<Payload> getPayloads() {
    return payloads;
  }

  public void setPayloads(Set<Payload> payloads) {
    this.payloads = payloads;
  }
}
