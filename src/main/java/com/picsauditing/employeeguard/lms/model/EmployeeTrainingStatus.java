package com.picsauditing.employeeguard.lms.model;

import java.util.Date;

public class EmployeeTrainingStatus {

	private String trainingResourceId;
	private TrainingResourceType trainingResourceType;
	private String userId;
	private int picsUserId;
	private float percentComplete;
	private String status;
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
