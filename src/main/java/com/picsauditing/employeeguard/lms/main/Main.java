package com.picsauditing.employeeguard.lms.main;

import com.google.gson.GsonBuilder;
import com.picsauditing.employeeguard.lms.model.*;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;

public class Main {

	public static void main(String[] args) {
		System.out.println(printUserJson());
		System.out.println(printTrainingPathJson());
		System.out.println(printTrainingCourseJson());
		System.out.println(printLaunchLinkJson());
		System.out.println(printEmployeeTrainingStatusJson());
		System.out.println(printEmployeeResourceAssignmentJson());
	}

	private static String printEmployeeResourceAssignmentJson() {
		EmployeeResourceAssignment employeeResourceAssignment = new EmployeeResourceAssignment();
		employeeResourceAssignment.setPicsUserId(34);
		employeeResourceAssignment.setTrainingResourceId("def456");
		employeeResourceAssignment.setTrainingResourceType(TrainingResourceType.COURSE);
		employeeResourceAssignment.setUserId("12345678");

		return new GsonBuilder().setPrettyPrinting().excludeFieldsWithModifiers(Modifier.STATIC, Modifier.TRANSIENT).create().toJson(employeeResourceAssignment);
	}

	private static String printEmployeeTrainingStatusJson() {
		EmployeeTrainingStatus employeeTrainingStatus = new EmployeeTrainingStatus();
		employeeTrainingStatus.setCompletionDate(new Date());
		employeeTrainingStatus.setPercentComplete(90);
		employeeTrainingStatus.setPicsUserId(34);
		employeeTrainingStatus.setStatus("In Progress");
		employeeTrainingStatus.setTrainingResourceId("def456");
		employeeTrainingStatus.setTrainingResourceType(TrainingResourceType.COURSE);
		employeeTrainingStatus.setUserId("12345678");

		return new GsonBuilder().setPrettyPrinting().excludeFieldsWithModifiers(Modifier.STATIC, Modifier.TRANSIENT).create().toJson(employeeTrainingStatus);
	}

	private static String printLaunchLinkJson() {
		LaunchLink launchLink = new LaunchLink();
		launchLink.setTrainingResourceId("def456");
		launchLink.setLaunchLink("https://www.picsorganizer.com/lms/courses/def456");

		return new GsonBuilder().setPrettyPrinting().excludeFieldsWithModifiers(Modifier.STATIC, Modifier.TRANSIENT).create().toJson(launchLink);
	}

	private static String printTrainingPathJson() {
		TrainingResource trainingResource = new TrainingResource();
		trainingResource.setId("abc123");
		trainingResource.setTitle("Welding II Training Path");
		trainingResource.setTrainingResourceType(TrainingResourceType.TRAINING_PATH);

		return new GsonBuilder().setPrettyPrinting().excludeFieldsWithModifiers(Modifier.STATIC, Modifier.TRANSIENT).create().toJson(trainingResource);
	}

	private static String printTrainingCourseJson() {
		TrainingResource trainingResource = new TrainingResource();
		trainingResource.setId("def456");
		trainingResource.setTitle("2014 HR Training");
		trainingResource.setTrainingResourceType(TrainingResourceType.COURSE);

		return new GsonBuilder().setPrettyPrinting().excludeFieldsWithModifiers(Modifier.STATIC, Modifier.TRANSIENT).create().toJson(trainingResource);
	}

	private static String printUserJson() {
		User user = new User();
		user.setEmail("test@tester.com");
		user.setFirstName("The");
		user.setLastName("Tester");
		user.setId(12345678);
		user.setLocale(Locale.US);
		user.setPicsUserID("34");
		user.setAccountIds("123,456");
		user.setUsername("the.tester");
		user.setType(UserType.EMPLOYEE);

		return new GsonBuilder().setPrettyPrinting().excludeFieldsWithModifiers(Modifier.STATIC, Modifier.TRANSIENT).create().toJson(user);
	}

}
