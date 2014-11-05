package com.picsauditing.employeeguard.lms.dao;

import java.util.List;

import com.picsauditing.employeeguard.lms.model.User;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, Long> {


  List<User> findByLastName(String lastName);
}