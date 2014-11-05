package com.picsauditing.employeeguard.lms.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class EmployeeTrainingStatus {

	@SerializedName("Id")
	private String trainingResourceId;

	@SerializedName("Type")
	private TrainingResourceType trainingResourceType;

	@SerializedName("UserId")
	private String userId;

	@SerializedName("EmployeeId")
	private int picsUserId;

	@SerializedName("PercentComplete")
	private float percentComplete;

	@SerializedName("Status")
	private String status;

	@SerializedName("CompletionDate")
	private Date completionDate;

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

	public float getPercentComplete() {
		return percentComplete;
	}

	public void setPercentComplete(float percentComplete) {
		this.percentComplete = percentComplete;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(Date completionDate) {
		this.completionDate = completionDate;
	}
}
