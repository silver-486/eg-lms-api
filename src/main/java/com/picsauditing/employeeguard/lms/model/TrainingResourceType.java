package com.picsauditing.employeeguard.lms.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TrainingResourceType {

	@JsonProperty("Course")
	COURSE,
	@JsonProperty("TrainingPath")
	TRAINING_PATH

}
