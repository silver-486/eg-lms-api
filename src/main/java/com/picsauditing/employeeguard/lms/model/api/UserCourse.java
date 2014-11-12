package com.picsauditing.employeeguard.lms.model.api;

public class UserCourse {

  Long userId;
  Long CourseId;

  public UserCourse(Long userId, Long courseId) {
    this.userId = userId;
    CourseId = courseId;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Long getCourseId() {
    return CourseId;
  }

  public void setCourseId(Long courseId) {
    CourseId = courseId;
  }
}
