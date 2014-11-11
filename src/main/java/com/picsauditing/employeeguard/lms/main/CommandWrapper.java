package com.picsauditing.employeeguard.lms.main;

import com.picsauditing.employeeguard.lms.model.api.Command;
import com.picsauditing.employeeguard.lms.model.api.Payload;

import java.util.ArrayList;
import java.util.List;

public class CommandWrapper {

	private final Command command;
	private List<Payload> payloads = new ArrayList<>();

	public CommandWrapper(Command command) {
		this.command = command;
	}

	public Command getCommand() {
		return command;
	}

	public void addPayload(Payload payload) {
		payloads.add(payload);
	}

	public List<Payload> getPayloads() {
		return payloads;
	}
}
