package com.picsauditing.employeeguard.lms.model;

import com.google.gson.annotations.SerializedName;

public class EmployeeResourceAssignment {

	@SerializedName("trainingResourceId")
	private String trainingResourceId;

	@SerializedName("type")
	private TrainingResourceType trainingResourceType;

	@SerializedName("userId")
	private String userId;

	@SerializedName("employeeId")
	private int employeeId;

	public String getTrainingResourceId() {
		return trainingResourceId;
	}

	public void setTrainingResourceId(String trainingResourceId) {
		this.trainingResourceId = trainingResourceId;
	}

	public TrainingResourceType getTrainingResourceType() {
		return trainingResourceType;
	}

	public void setTrainingResourceType(TrainingResourceType trainingResourceType) {
		this.trainingResourceType = trainingResourceType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
}
