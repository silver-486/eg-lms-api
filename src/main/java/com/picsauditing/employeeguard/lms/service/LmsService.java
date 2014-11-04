package com.picsauditing.employeeguard.lms.service;

import com.picsauditing.employeeguard.lms.model.User;
import com.picsauditing.employeeguard.lms.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class LmsService implements ILmsService {

  @Autowired
  private UserRepository dao;

  @Transactional
  public User addUser(long id, String username) {
    User user = new User(id, username);
    dao.save(user);

    return user;
  }

}
