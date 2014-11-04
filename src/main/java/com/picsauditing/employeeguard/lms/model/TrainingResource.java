package com.picsauditing.employeeguard.lms.model;

/**
 * This class can represent either a Training Path or a Training Course, as defined by the TrainingResourceType
 */
public class TrainingResource {

	private String id;
	private TrainingResourceType trainingResourceType;
	private String title;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public TrainingResourceType getTrainingResourceType() {
		return trainingResourceType;
	}

	public void setTrainingResourceType(TrainingResourceType trainingResourceType) {
		this.trainingResourceType = trainingResourceType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
