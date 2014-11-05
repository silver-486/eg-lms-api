package com.picsauditing.employeeguard.lms.model.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageResponse implements Response {

	private int id;
	private String status;
	private Set<Response> responses;
	private int httpStatusCode;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Set<Response> getResponses() {
		return responses;
	}

	public void setResponses(Set<Response> responses) {
		this.responses = responses;
	}

	public int getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(int httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}
}
