package com.javaProject.payments_App_Cli.entity;

public class UserOperations {
	
	public User doUserRegistration(String fname,String lname,long phnum,String dob,String addr,String pswd) {
		User u = new User();
		u.setFirstName(fname);
		u.setLastName(lname);
		u.setPhoneNumber(phnum);
		u.setDateOfBirth(dob);
		u.setAddress(addr);
		u.setPassword(pswd);
		
		u.setUserId((int)(Math.random()*1000)+100); 
		
		return u;
	}
	
}
