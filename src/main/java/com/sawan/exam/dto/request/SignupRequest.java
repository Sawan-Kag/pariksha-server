package com.sawan.exam.dto.request;

import java.util.Set;

import jakarta.validation.constraints.*;

public class SignupRequest {
	
    @NotBlank(message = "Username cannot be blank")
    @NotNull(message = "dddddddddddd")
    private String username;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;
    
    private String fName;
    private String lName;

  private Set<String> roles;

  
  

  public SignupRequest() {

}

public SignupRequest(@NotBlank @Size(min = 3, max = 20) String username, @NotBlank @Size(max = 50) @Email String email,
		Set<String> roles, @NotBlank @Size(min = 6, max = 40) String password) {
	super();
	this.username = username;
	this.email = email;
	this.roles = roles;
	this.password = password;
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

public void setRoles(Set<String> roles) {
	this.roles = roles;
}

public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<String> getRoles() {
    return this.roles;
  }

  public void setRole(Set<String> roles) {
    this.roles = roles;
  }

@Override
public String toString() {
	return "SignupRequest [username=" + username + ", password=" + password + ", email=" + email + ", roles=" + roles
			+ "]";
}
  
  
}
