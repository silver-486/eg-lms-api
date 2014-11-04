package com.picsauditing.employeeguard.lms.model.api;

import java.util.Set;

public class MessageResponse implements Response {

	private int id;
	private String status;
	private Set<Response> responses;

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
}
