package com.picsauditing.employeeguard.lms.main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.picsauditing.employeeguard.lms.model.api.Command;

public class GetAllUsersIdsCommandHandler extends CommandHandler {

	@Override
	public void handleCommand(CommandWrapper commandWrapper) throws JsonProcessingException {
		Command command = commandWrapper.getCommand();
		if (Command.GET_ALL_USER_IDS != command) {
			if (next == null) {
				return;
			}

			next.handleCommand(commandWrapper);
		} else {
			commandWrapper.addPayload(mocker.buildPayload(command, mocker.randomId()));
		}
	}
}
