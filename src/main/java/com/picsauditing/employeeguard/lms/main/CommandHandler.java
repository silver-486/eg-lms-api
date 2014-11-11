package com.picsauditing.employeeguard.lms.main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.picsauditing.employeeguard.lms.model.api.Command;

abstract class CommandHandler {

	protected CommandHandler next;

	public CommandHandler setNext(CommandHandler next) {
		this.next = next;
		return this;
	}

	public abstract void handleCommand(CommandWrapper commandWrapper) throws JsonProcessingException;
}
