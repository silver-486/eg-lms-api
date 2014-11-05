package com.picsauditing.employeeguard.lms.service;

import com.picsauditing.employeeguard.lms.dao.UserRepository;
import com.picsauditing.employeeguard.lms.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
//    Iterable<User> Users = repository.findAll();
    System.out.println("Users found with findAll():");
    System.out.println("-------------------------------");
    for (User User : Users) {
      System.out.println(User);
    }
    System.out.println();
  }


}
