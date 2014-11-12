package com.picsauditing.employeeguard.lms.main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.picsauditing.employeeguard.lms.model.api.Command;
import com.picsauditing.employeeguard.lms.model.api.Message;
import com.picsauditing.employeeguard.lms.model.api.Payload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class MockRequestService {

  @Autowired
  Mocker mocker;
  private CommandHandler commandHandler;

  @PostConstruct
  public void init() {
    commandHandler = buildCommandChain();
  }

  public Message mockRequest(Command command) throws JsonProcessingException {

    CommandWrapper commandWrapper = new CommandWrapper(command);
    commandHandler.handleCommand(commandWrapper);

    return wrapPayloads(commandWrapper.getPayloads());
  }

  public Message mockRequest(Command... commands) throws JsonProcessingException {

    List<Payload> payloads = new ArrayList<>();
    for (Command command : commands) {
      CommandWrapper commandWrapper = new CommandWrapper(command);
      commandHandler.handleCommand(commandWrapper);
      payloads.addAll(commandWrapper.getPayloads());
      System.out.println(command.toString() + " : " + commandWrapper.getPayloads());
    }

    return wrapPayloads(payloads);
  }

  private Message wrapPayloads(List<Payload> payloads) {
    Message message = new Message();
    message.setId(mocker.randomId());
    message.setPayloads(payloads);
    return message;
  }

  private CommandHandler buildCommandChain() {
    CommandHandler commandHandler = new AddPicsAccountCommandHandler();

    commandHandler.setNext(new AddUserHandler()
        .setNext(new DeactivatePicsAccountCommandHandler()
            .setNext(new DeactivateUserHandler()
                .setNext(new DeletePicsAccountCommandHandler()
                    .setNext(new DeleteUserHandler()
                        .setNext(new GetAllPicsAccountsByIdsCommandHandler()
                            .setNext(new GetAllUsersIdsCommandHandler()
                                .setNext(new GetPicsAccountsByIdsCommandHandler()
                                    .setNext(new GetUsersByIdHandler()
                                        .setNext(new UpdatePicsAccountCommandHandler()
                                            .setNext(new UpdateUserHandler()
                                                .setNext(new GetAssignmentByCourseIdHandler()
                                                    .setNext(new GetAssignmentByUserIdHandler()
                                                        .setNext(new GetAssignmentByUserIdCourseIdHandler()
                                                            .setNext(new GetAssignmentByIdHandler()
                                                                .setNext(new GetAllAssignmentsIds()
                                                                    .setNext(new AssignUserToCourse()
                                                                    )
                                                                )
                                                            )
                                                        )
                                                    )
                                                )
                                            )
                                        )
                                    )
                                )
                            )
                        )
                    )
                )
            )
        )
    );
    return commandHandler;
  }
}
