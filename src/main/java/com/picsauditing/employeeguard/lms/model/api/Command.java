package com.picsauditing.employeeguard.lms.model.api;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Command {

	ADD_USER("addUser"),
	UPDATE_USER("updateUser"),
	GET_COURSE_LINK("getCourseLink"),
	DEACTIVATE_USER("deactivateUser"),
	DELETE_USER("deleteUser"),
	GET_USERS_BY_IDS("getUsersByIds"),
	GET_ALL_USER_IDS("getAllUserIds"),
	ASSIGN_EMPLOYEE_TO_TRAINING("assignEmployeeToTraining"),
	GET_LAUNCH_LINK("getLaunchLink");

  String command;

  Command(String command) {
    this.command = command;
  }

  @Override
  public String toString() {
    return this.command;
  }

	@JsonValue
	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}
}
