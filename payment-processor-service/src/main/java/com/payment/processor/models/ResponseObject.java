package com.payment.processor.models;


public class ResponseObject {
	String responseMessage;
	String errorMessages;
	
	public ResponseObject(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	
	public ResponseObject(String responseMessage, String errorMessages) {
		this.responseMessage = responseMessage;
		this.errorMessages = errorMessages;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public String getErrorMessage() {
		return errorMessages;
	}

	public void setErrorMessage(String errorMessages) {
		this.errorMessages = errorMessages;
	}
	
}
