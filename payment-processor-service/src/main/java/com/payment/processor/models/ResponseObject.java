package com.payment.processor.models;

import java.util.List;
/**
 * Communication class.
 * @author Joseph DiBiasi
 * @version 1.0
 */
public class ResponseObject {
	String responseMessage;
	String errorMessages;
	List<?> data;
	
	public String getErrorMessages() {
		return errorMessages;
	}

	public void setErrorMessages(String errorMessages) {
		this.errorMessages = errorMessages;
	}

	public List<?> getData() {
		return data;
	}

	public void setData(List<?> data) {
		this.data = data;
	}

	public ResponseObject(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	
	public ResponseObject(String responseMessage, String errorMessages) {
		this.responseMessage = responseMessage;
		this.errorMessages = errorMessages;
	}
	
	public ResponseObject(String responseMessage, List<?> data) {
		this.responseMessage = responseMessage;
		this.data = data;
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
