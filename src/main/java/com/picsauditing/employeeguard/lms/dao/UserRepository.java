package com.picsauditing.employeeguard.lms.dao;

import com.picsauditing.employeeguard.lms.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface UserRepository extends CrudRepository<User, Long> {


  List<User> findByLastName(String lastName);
}