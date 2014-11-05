package com.picsauditing.employeeguard.lms.main;

import com.google.gson.GsonBuilder;
import com.picsauditing.employeeguard.lms.model.*;

import java.lang.reflect.Modifier;


public class Main {

  Mocker mocker;

  public void run() {
    mocker = new Mocker();
    System.out.println(printUserJson());
    System.out.println(printTrainingPathJson());
    System.out.println(printTrainingCourseJson());
    System.out.println(printLaunchLinkJson());
    System.out.println(printEmployeeTrainingStatusJson());
    System.out.println(printEmployeeResourceAssignmentJson());
  }

  public String printEmployeeResourceAssignmentJson() {
    EmployeeResourceAssignment employeeResourceAssignment = mocker.mockEmployeeResourceAssignment();

    return toJSON(employeeResourceAssignment);
  }

  private String toJSON(Object obj) {
    return new GsonBuilder().setPrettyPrinting().excludeFieldsWithModifiers(Modifier.STATIC, Modifier.TRANSIENT).create().toJson(obj);
  }

  public String printEmployeeTrainingStatusJson() {
    EmployeeTrainingStatus employeeTrainingStatus = mocker.mockEmployeeTrainingStatus();

    return new GsonBuilder().setPrettyPrinting().excludeFieldsWithModifiers(Modifier.STATIC, Modifier.TRANSIENT).create().toJson(employeeTrainingStatus);
  }


  public String printLaunchLinkJson() {
    LaunchLink launchLink = mocker.mockLaunchLink();

    return new GsonBuilder().setPrettyPrinting().excludeFieldsWithModifiers(Modifier.STATIC, Modifier.TRANSIENT).create().toJson(launchLink);
  }

  public String printTrainingPathJson() {
    TrainingResource trainingResource = mocker.mockTrainingPath();

    return new GsonBuilder().setPrettyPrinting().excludeFieldsWithModifiers(Modifier.STATIC, Modifier.TRANSIENT).create().toJson(trainingResource);
  }

  public String printTrainingCourseJson() {
    TrainingResource trainingResource = mocker.mockTrainingCourse();

    return new GsonBuilder().setPrettyPrinting().excludeFieldsWithModifiers(Modifier.STATIC, Modifier.TRANSIENT).create().toJson(trainingResource);
  }

  public String printUserJson() {
    User user = mocker.mockUser();

    return new GsonBuilder().setPrettyPrinting().excludeFieldsWithModifiers(Modifier.STATIC, Modifier.TRANSIENT).create().toJson(user);
  }

  public static void main(String[] args) {
    new Main().run();
  }
}
