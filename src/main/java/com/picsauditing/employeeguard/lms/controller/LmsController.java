package com.picsauditing.employeeguard.lms.controller;

import com.picsauditing.employeeguard.lms.model.User;
import com.picsauditing.employeeguard.lms.model.api.Response;
import com.picsauditing.employeeguard.lms.model.api.ResponseImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
class LmsController {

/*
    @RequestMapping(value = "/eg-lms-api/{name}", method = RequestMethod.POST)
    String hello(@PathVariable String name) {
        return "Hello, " + name + "!";
    }
*/

  @RequestMapping(value = "/eg-lms-api", method = RequestMethod.POST)
  public ResponseEntity<Response> update(@RequestBody User user) {

    Response response = new ResponseImpl();
    response.setId(1);
    response.setStatus("ok");

    return new ResponseEntity<Response>(response, HttpStatus.OK);
  }
}
