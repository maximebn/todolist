package com.todolist.service;

public interface IEmailService {
	boolean isValidEmailAddress(String email);
	boolean isEmailAlreadyUsed(String email);

}
