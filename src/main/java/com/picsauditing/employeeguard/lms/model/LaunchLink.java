package com.picsauditing.employeeguard.lms.model;

import com.google.gson.annotations.SerializedName;

public class LaunchLink {

	@SerializedName("TrainingResourceId")
	private String trainingResourceId;
	@SerializedName("LaunchUrl")
	private String launchLink;

	public String getTrainingResourceId() {
		return trainingResourceId;
	}

	public void setTrainingResourceId(String trainingResourceId) {
		this.trainingResourceId = trainingResourceId;
	}

	public String getLaunchLink() {
		return launchLink;
	}

	public void setLaunchLink(String launchLink) {
		this.launchLink = launchLink;
	}
}
