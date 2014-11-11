package com.picsauditing.employeeguard.lms.service;

import com.picsauditing.employeeguard.lms.model.api.Message;
import com.picsauditing.employeeguard.lms.model.api.MessageLog;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MessageRegistry {

  Map<Long, MessageLog> registry = new HashMap<>();

  public void storeMessage(Message message) {
    registry.put(message.getId(), new MessageLog(message));
  }

  public MessageLog retrieveMessageLog(Message message) {
    return registry.get(message.getId());
  }

  public Message retrieveMessage(Message message) {
    return registry.get(message.getId()).getMessage();
  }

}
