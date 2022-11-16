package hu.webuni.airport.web;

import java.util.List;

import org.springframework.validation.FieldError;

public class MyError {

	private String message;
	private int errorcode;
	private List<FieldError> fieldErrors;
	
		public MyError(String message, int errorcode) {
		super();
		this.message = message;
		this.errorcode = errorcode;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getErrorcode() {
		return errorcode;
	}
	public void setErrorcode(int errorcode) {
		this.errorcode = errorcode;
	}

	public List<FieldError> getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(List<FieldError> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}
	
}
