package com.picsauditing.employeeguard.lms.controller;

import com.picsauditing.employeeguard.lms.main.Mocker;
import com.picsauditing.employeeguard.lms.model.api.Message;
import com.picsauditing.employeeguard.lms.model.api.MessageResponse;
import com.picsauditing.employeeguard.lms.model.api.Payload;
import com.picsauditing.employeeguard.lms.service.ApiService;
import com.picsauditing.employeeguard.lms.service.LmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class LmsController {

  @Autowired
  LmsService lmsService;

  @Autowired
  Mocker mocker;

  @Autowired
  ApiService apiService;

  //http://localhost:8080/testLmsApi
  //entry point for testing
  @RequestMapping("/testLmsApi")
  public ResponseEntity<MessageResponse> testLmsApi() throws Exception {

    Message message = mocker.mockerMessageTest();

    MessageResponse messageResponse = apiService.sendMessage(message);

    return new ResponseEntity<>(messageResponse, HttpStatus.OK);
  }


  /**
   * Mothership end point
   * @param message
   * @return
   */
  @RequestMapping("/lmsApi")
  public ResponseEntity<MessageResponse> receiveMessage(@RequestBody Message message) {

//    print(message);

    MessageResponse messageResponse = lmsService.processMessage(message);
    messageResponse.setStatusCode(0);

    return new ResponseEntity<>(messageResponse, HttpStatus.OK);
  }


  private void print(Message msg) {
    System.out.println("msg.id: " + msg.getId());
    for (Payload payload : msg.getPayloads()) {
      System.out.println(payload);
    }
  }
}
