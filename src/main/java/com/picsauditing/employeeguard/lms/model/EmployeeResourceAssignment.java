package com.picsauditing.employeeguard.lms.model;

public class EmployeeResourceAssignment {

	private String trainingResourceId;
	private TrainingResourceType trainingResourceType;
	private String userId;
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
