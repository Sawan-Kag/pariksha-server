package com.sawan.exam.dto.response;

import java.util.List;

public class UserInfoResponse {
	private String id;
	private String username;
	private String email;
	private String fName;
	private String lName;
	private List<String> roles;
	private boolean isenable;

	
	
	public UserInfoResponse(String id, String username, String email, String fName, String lName, List<String> roles,
			boolean isenable) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.fName = fName;
		this.lName = lName;
		this.roles = roles;
		this.isenable = isenable;
	}


	public boolean isIsenable() {
		return isenable;
	}


	public void setIsenable(boolean isenable) {
		this.isenable = isenable;
	}


	public void setRoles(List<String> roles) {
		this.roles = roles;
	}


	public void setisenable(boolean isenable) {
		this.isenable = isenable;
		
	}
	
	
	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public boolean getisenable() {
		return isenable;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getRoles() {
		return roles;
	}
}
