package com.picsauditing.employeeguard.lms.model.api;

public class MessageLog {

	Message message;
	MessageResponse messageResponse;

	public MessageLog(Message message) {
		this.message = message;
	}

	public MessageLog(Message message, MessageResponse messageResponse) {
		this.message = message;
		this.messageResponse = messageResponse;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public MessageResponse getMessageResponse() {
		return messageResponse;
	}

	public void setMessageResponse(MessageResponse messageResponse) {
		this.messageResponse = messageResponse;
	}
}
