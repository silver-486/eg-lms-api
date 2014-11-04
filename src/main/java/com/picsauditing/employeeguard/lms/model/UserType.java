package com.picsauditing.employeeguard.lms.model;

import com.google.gson.annotations.SerializedName;

public enum  UserType {

	@SerializedName("employee")
	EMPLOYEE,
	@SerializedName("lms_admin")
	LMS_ADMIN,
	@SerializedName("pics_admin")
	PICS_ADMIN

}
