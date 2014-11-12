package com.picsauditing.employeeguard.lms.main;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
abstract class CommandHandler {

	@Autowired
	Mocker mocker;

	protected CommandHandler next;

	public CommandHandler setNext(CommandHandler next) {
		this.next = next;
		return this;
	}

	public abstract void handleCommand(CommandWrapper commandWrapper) throws JsonProcessingException;
}
