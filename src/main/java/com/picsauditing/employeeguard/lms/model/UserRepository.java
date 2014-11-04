package com.picsauditing.employeeguard.lms.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserRepository extends CrudRepository<User, Long> {

  List<User> findByLastName(String lastName);
}