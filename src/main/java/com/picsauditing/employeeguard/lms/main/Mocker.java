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
  private long seqId = 0;

  public Mocker() {
  }

  public EmployeeResourceAssignment mockEmployeeResourceAssignment() {
    EmployeeResourceAssignment employeeResourceAssignment = new EmployeeResourceAssignment();
    employeeResourceAssignment.setEmployeeId(34);
    employeeResourceAssignment.setTrainingResourceId(randomId());
    employeeResourceAssignment.setTrainingResourceType(TrainingResourceType.COURSE);
    employeeResourceAssignment.setUserId(randomId());
    return employeeResourceAssignment;
  }

  public EmployeeTrainingStatus mockEmployeeTrainingStatus() {
    EmployeeTrainingStatus employeeTrainingStatus = new EmployeeTrainingStatus();
    employeeTrainingStatus.setCompletionDate(new Date());
    employeeTrainingStatus.setPercentComplete(90);
    employeeTrainingStatus.setPicsUserId(34);
    employeeTrainingStatus.setStatus("In Progress");
    employeeTrainingStatus.setTrainingResourceId("def456");
    employeeTrainingStatus.setTrainingResourceType(TrainingResourceType.COURSE);
    employeeTrainingStatus.setUserId(randomId());
    return employeeTrainingStatus;
  }

  public LaunchLink mockLaunchLink() {
    LaunchLink launchLink = new LaunchLink();
    launchLink.setTrainingResourceId("def456");
    launchLink.setLaunchLink("https://www.picsorganizer.com/lms/courses/def456");
    return launchLink;
  }

  public TrainingResource mockTrainingPath() {
    TrainingResource trainingResource = new TrainingResource();
    trainingResource.setId("abc123");
    trainingResource.setTitle("Welding II Training Path");
    trainingResource.setTrainingResourceType(TrainingResourceType.TRAINING_PATH);
    return trainingResource;
  }

  public TrainingResource mockTrainingCourse() {
    TrainingResource trainingResource = new TrainingResource();
    trainingResource.setId("def456");
    trainingResource.setTitle("2014 HR Training");
    trainingResource.setTrainingResourceType(TrainingResourceType.COURSE);
    return trainingResource;
  }

  public Message mockerMessageTest() throws JsonProcessingException {
    Message message = new Message();
    message.setId(randomId());



    User user1 = mockUser();
    User user2 = mockUser();

    Payload payload1 = mockPayload(user1);
    Payload payload2 = mockPayload(user2);

    Set<Payload> payloads = new HashSet<>();
    payloads.add(payload1);
    payloads.add(payload2);

    message.setPayloads(payloads);
    return message;
  }

  private Payload mockPayload(User user1) throws JsonProcessingException {
    Payload payload = new Payload();
    payload.setCommand(ADD_USER);
    payload.setId(randomId());
    payload.setData(JSONHelper.toJSONJackson(user1));
    return payload;
  }

  public long seqId() {
    return ++seqId;
  }

  public Long randomId() {
    int min = 0;
    int max = 10000;
    Long r = (long) (Math.random() * (max - min) + min);
    return r;
  }


  public User mockUser() {
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
