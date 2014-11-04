package com.picsauditing.employeeguard.lms.model.api;

import java.util.Set;

public class Message {

	private int id;
	private Set<Payload> payloads;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Set<Payload> getPayloads() {
		return payloads;
	}

	public void setPayloads(Set<Payload> payloads) {
		this.payloads = payloads;
	}
}
