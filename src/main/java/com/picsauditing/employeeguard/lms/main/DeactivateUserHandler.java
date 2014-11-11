package com.picsauditing.employeeguard.lms.main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.picsauditing.employeeguard.lms.model.api.Command;

public class DeactivateUserHandler extends CommandHandler {

	@Override
	public void handleCommand(CommandWrapper commandWrapper) throws JsonProcessingException {
		Command command = commandWrapper.getCommand();
		if (Command.DEACTIVATE_USER != command) {
			if (next == null) {
				return;
			}

			next.handleCommand(commandWrapper);
		} else {
			Mocker mocker = new Mocker();
			commandWrapper.addPayload(mocker.buildPayload(command, mocker.randomId(), mocker.mockUserIdOnly()));
		}
	}
}
