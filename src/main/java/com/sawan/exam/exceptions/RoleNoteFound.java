package com.sawan.exam.exceptions;

public class RoleNoteFound extends RuntimeException {
	private static final long serialVersionUID = -5316388420660892464L;
	private String message;
    
	

	public RoleNoteFound(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
    
}