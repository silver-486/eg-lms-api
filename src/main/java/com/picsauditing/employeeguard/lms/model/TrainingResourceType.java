package com.picsauditing.employeeguard.lms.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

public enum TrainingResourceType {

  @JsonProperty("Course")
  COURSE,
  @JsonProperty("TrainingPath")
  TRAINING_PATH

}
