package com.picsauditing.employeeguard.lms.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class EmployeeTrainingStatus {

  @JsonProperty("Id")
  private String trainingResourceId;

  @JsonProperty("Type")
  private TrainingResourceType trainingResourceType;

  private Long userId;

  @JsonProperty("EmployeeId")
  private int picsUserId;

  @JsonProperty("percentComplete")
  private float percentComplete;

  private boolean completed;

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

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
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

  public boolean isCompleted() {
    return completed;
  }

  public void setCompleted(boolean completed) {
    this.completed = completed;
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
