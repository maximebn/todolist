package com.todolist.dto;

import com.todolist.persistence.entity.User;

public class UserDto {
	
	private String name;
	private String mail;
	private String password;
	
	public UserDto() {
		super();
	}
	
	public UserDto(User user) {
		this.setName(user.getName());
		this.setMail(user.getMail());
		this.setPassword(user.getPassword());
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
