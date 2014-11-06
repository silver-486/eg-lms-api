package com.picsauditing.employeeguard.lms.service;

import com.picsauditing.employeeguard.lms.dao.UserRepository;
import com.picsauditing.employeeguard.lms.model.User;
import com.picsauditing.employeeguard.lms.model.api.Message;
import com.picsauditing.employeeguard.lms.model.api.MessageResponse;
import com.picsauditing.employeeguard.lms.model.api.Payload;
import com.picsauditing.employeeguard.lms.model.api.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class LmsService {

  @Autowired
  private UserRepository userRepository;

  public UserRepository getUserRepository() {
    return userRepository;
  }

  public void setUserRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public void test() {
    userRepository.save(new User(1, "John"));

    Iterable<User> Users = userRepository.findAll();
    for (User User : Users) {
      System.out.println(User);
    }
    System.out.println();
  }


  public MessageResponse processMessage(Message message) {
    MessageResponse messageResponse = new MessageResponse();
    messageResponse.setRefId(message.getId());

    for (Payload payload : message.getPayloads()) {
      Status status = new Status();
      status.setRefId(payload.getId());

      int statusCode = processPayload(payload);
      status.setStatusCode(statusCode);
      messageResponse.getStatuses().add(status);
    }

    return messageResponse;
  }

  private int processPayload(Payload payload) {
    //todo Pablo, process payload
    return Status.OK
      ;
  }
}
