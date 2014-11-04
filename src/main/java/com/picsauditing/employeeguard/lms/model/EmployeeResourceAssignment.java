package com.picsauditing.employeeguard.lms.model;

import com.google.gson.annotations.SerializedName;

public class EmployeeResourceAssignment {

	@SerializedName("TrainingResourceId")
	private String trainingResourceId;
	@SerializedName("Type")
	private TrainingResourceType trainingResourceType;
	@SerializedName("UserId")
	private String userId;
	@SerializedName("EmployeeId")
	private int picsUserId;

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

	public int getPicsUserId() {
		return picsUserId;
	}

	public void setPicsUserId(int picsUserId) {
		this.picsUserId = picsUserId;
	}
}
