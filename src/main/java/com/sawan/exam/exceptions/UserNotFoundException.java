package com.sawan.exam.exceptions;

public class UserNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -5316388420660892464L;
	private String message;

    public UserNotFoundException(String message) {
        super();
    	this.message=message;
    
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
    
}
