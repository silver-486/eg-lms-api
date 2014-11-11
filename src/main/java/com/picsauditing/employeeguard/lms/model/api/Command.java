package com.picsauditing.employeeguard.lms.model.api;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Command {

	ADD_USER("addUser"),
	UPDATE_USER("updateUser"),
	DEACTIVATE_USER("deactivateUser"),
	DELETE_USER("deleteUser"),
	GET_USERS_BY_IDS("getUsersByIds"),
	GET_ALL_USER_IDS("getAllUserIds"),
	GET_COURSE_LINK("getCourseLink"),
	ASSIGN_EMPLOYEE_TO_TRAINING("assignEmployeeToTraining"),
	GET_LAUNCH_LINK("getLaunchLink"),
	ADD_PICS_ACCOUNT("addPicsAccount"),
	UPDATE_PICS_ACCOUNT("updatePicsAccount"),
	DEACTIVATE_PICS_ACCOUNT("deactivatePicsAccount"),
	DELETE_PICS_ACCOUNT("deletePicsAccount"),
	FIND_PICS_ACCOUNT_BY_IDS("findPicsAccountByIds"),
	GET_ALL_PICS_ACCOUNT_IDS("getAllPicsAccountIds");

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
