package com.picsauditing.employeeguard.lms.main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.picsauditing.employeeguard.lms.JSONHelper;
import com.picsauditing.employeeguard.lms.model.*;
import com.picsauditing.employeeguard.lms.model.api.Message;
import com.picsauditing.employeeguard.lms.model.api.Payload;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import static com.picsauditing.employeeguard.lms.model.api.Command.ADD_USER;

@Service
public class Mocker {
  public Mocker() {
  }

  public  EmployeeResourceAssignment mockEmployeeResourceAssignment() {
    EmployeeResourceAssignment employeeResourceAssignment = new EmployeeResourceAssignment();
    employeeResourceAssignment.setEmployeeId(34);
    employeeResourceAssignment.setTrainingResourceId("def456");
    employeeResourceAssignment.setTrainingResourceType(TrainingResourceType.COURSE);
    employeeResourceAssignment.setUserId("12345678");
    return employeeResourceAssignment;
  }

  public  EmployeeTrainingStatus mockEmployeeTrainingStatus() {
    EmployeeTrainingStatus employeeTrainingStatus = new EmployeeTrainingStatus();
    employeeTrainingStatus.setCompletionDate(new Date());
    employeeTrainingStatus.setPercentComplete(90);
    employeeTrainingStatus.setPicsUserId(34);
    employeeTrainingStatus.setStatus("In Progress");
    employeeTrainingStatus.setTrainingResourceId("def456");
    employeeTrainingStatus.setTrainingResourceType(TrainingResourceType.COURSE);
    employeeTrainingStatus.setUserId("12345678");
    return employeeTrainingStatus;
  }

  public  LaunchLink mockLaunchLink() {
    LaunchLink launchLink = new LaunchLink();
    launchLink.setTrainingResourceId("def456");
    launchLink.setLaunchLink("https://www.picsorganizer.com/lms/courses/def456");
    return launchLink;
  }

  public  TrainingResource mockTrainingPath() {
    TrainingResource trainingResource = new TrainingResource();
    trainingResource.setId("abc123");
    trainingResource.setTitle("Welding II Training Path");
    trainingResource.setTrainingResourceType(TrainingResourceType.TRAINING_PATH);
    return trainingResource;
  }

  public  TrainingResource mockTrainingCourse() {
    TrainingResource trainingResource = new TrainingResource();
    trainingResource.setId("def456");
    trainingResource.setTitle("2014 HR Training");
    trainingResource.setTrainingResourceType(TrainingResourceType.COURSE);
    return trainingResource;
  }

  public Message mockerMessageTest() throws JsonProcessingException {
    Message message = new Message();
    message.setId(1);
    Payload payload = new Payload();
    User user1 = new User(1, "mike");
    payload.setCommand(ADD_USER);
//    payload.setData(toJSON(user1));
    payload.setData(JSONHelper.toJSONJackson(user1));

    Set<Payload> payloads = new HashSet<>();
    payloads.add(payload);
    message.setPayloads(payloads);
    return message;
  }



  public  User mockUser() {
    User user = new User();
    user.setEmail("test@tester.com");
    user.setFirstName("The");
    user.setLastName("Tester");
    user.setId(12345678);
    user.setLocale(Locale.US);
    user.setAccountIds("123,456");
    user.setUsername("the.tester");
    user.setType(UserType.EMPLOYEE);
    return user;
  }


}
