package com.picsauditing.employeeguard.lms.controller;

import com.picsauditing.employeeguard.lms.main.MockRequestService;
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

import static com.picsauditing.employeeguard.lms.model.api.Command.*;

@RestController
class LmsController {

  @Autowired
  LmsService lmsService;

  @Autowired
  ApiService apiService;

  @Autowired
  MockRequestService mockRequestService;

  //http://localhost:8080/testLmsApi
  //entry point for testing
  @RequestMapping("/testLmsApi")
  public ResponseEntity<MessageResponse> testLmsApi() throws Exception {

//    Message message = mocker.mockerMessageTest();
    Message message = mockRequestService.mockRequest(
      ADD_USER,
      UPDATE_USER,
      DEACTIVATE_USER,
      DELETE_USER,
      GET_USERS_BY_IDS,
      GET_ALL_USER_IDS,
      GET_COURSE_LINK,
      ASSIGN_EMPLOYEE_TO_TRAINING,
      GET_LAUNCH_LINK,
      ADD_PICS_ACCOUNT,
      UPDATE_PICS_ACCOUNT,
      DEACTIVATE_PICS_ACCOUNT,
      DELETE_PICS_ACCOUNT,
      FIND_PICS_ACCOUNT_BY_IDS,
      GET_ALL_PICS_ACCOUNT_IDS
    );

    MessageResponse messageResponse = apiService.sendMessage(message);

    return new ResponseEntity<>(messageResponse, HttpStatus.OK);
  }


  /**
   * Mothership end point
   *
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
