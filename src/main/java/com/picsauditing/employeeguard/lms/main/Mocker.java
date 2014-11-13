package com.picsauditing.employeeguard.lms.main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.picsauditing.employeeguard.lms.JSONHelper;
import com.picsauditing.employeeguard.lms.model.*;
import com.picsauditing.employeeguard.lms.model.api.Command;
import com.picsauditing.employeeguard.lms.model.api.Message;
import com.picsauditing.employeeguard.lms.model.api.Payload;
import org.springframework.stereotype.Service;

import java.util.*;

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

		List<Payload> payloads = new ArrayList<>();
		payloads.add(payload1);
		payloads.add(payload2);

		message.setPayloads(payloads);
		return message;
	}

	private Payload mockPayload(User user1) throws JsonProcessingException {
		Payload payload = new Payload();
		payload.setCommand(ADD_USER);
		payload.setId(randomId());
		payload.setData(JSONHelper.toJSON(user1));
		return payload;
	}

	public Payload buildPayload(Command command, long id) throws JsonProcessingException {
		return buildPayload(command, id, null);
	}

	public Payload buildPayload(Command command, long id, Object object) throws JsonProcessingException {
		Payload payload = new Payload();
		payload.setCommand(command);
		payload.setId(id);
		if (object != null) {
			payload.setData(JSONHelper.toJSON(object));
		}

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
		user.setUserId(randomId());
		user.setEmail("test@tester.com");
		user.setFirstName("The");
		user.setLastName("Tester");
		user.setId(randomId());
		user.setLocale(Locale.US);
		user.setAccountIds("1234,5678");
		user.setUsername("tester");
		user.setType(UserType.EMPLOYEE);
		return user;
	}

	public Account mockPicsAccount() {
		Account account = new Account();
		account.setContactEmail("account.email@account.com");
		account.setContactFirstName("Account");
		account.setContactLastName("Contact");
		account.setContactLocale(Locale.GERMANY);
		account.setId(randomId());
		account.setName("PICS ACCOUNT");

		return account;
	}

	public Account mockPicsAccountIdOnly() {
		Account account = new Account();
		account.setId(randomId());
		account.setAccountId(randomId());
		return account;
	}

	public User mockUserIdOnly() {
		User user = new User();
		user.setId(randomId());
		user.setUserId(randomId());
		return user;
	}

	public List<Long> mockIds() {
		return Arrays.asList((long) 123, (long) 456, (long) 789);
	}

	public List<Long> mockIds(int count) {
		List<Long> ids = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			ids.add(randomId());
		}
		return ids;
	}

	public User addUserIdGUID(User user) {
		user.setUserId(randomId());
		return user;
	}

	public String randomGUID() {
		return UUID.randomUUID().toString();
	}
}
