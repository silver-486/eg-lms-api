package com.picsauditing.employeeguard.lms.main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.picsauditing.employeeguard.lms.model.api.Command;
import org.springframework.beans.factory.annotation.Autowired;

public class GetUsersByIdHandler extends CommandHandler {

	@Override
	public void handleCommand(CommandWrapper commandWrapper) throws JsonProcessingException {
		Command command = commandWrapper.getCommand();
		if (Command.GET_USERS_BY_IDS != command) {
			if (next == null) {
				return;
			}

			next.handleCommand(commandWrapper);
		} else {
			commandWrapper.addPayload(mocker.buildPayload(command, mocker.randomId(), mocker.mockIds()));
		}
	}
}
