package com.picsauditing.employeeguard.lms.main;

import com.fasterxml.jackson.core.JsonProcessingException;

abstract class CommandHandler {

	Mocker mocker = new Mocker();

	protected CommandHandler next;

	public CommandHandler setNext(CommandHandler next) {
		this.next = next;
		return this;
	}

	public abstract void handleCommand(CommandWrapper commandWrapper) throws JsonProcessingException;
}
