package com.todolist.service;

import java.util.List;

import javax.mail.internet.AddressException;

import com.todolist.dto.PerformanceDto;
import com.todolist.dto.TaskDto;
import com.todolist.dto.UserDto;
import com.todolist.persistence.entity.User;

public interface IUserService {
	
	UserDto register(UserDto userDto) throws AddressException;

	UserDto updateUserData(UserDto userDto, long idUtilisateur) throws AddressException;

	void deleteAccount(long userId);

	User findByLoginAndPassword(String username, String password);

	PerformanceDto getPerformanceIndex(long userId);

	double artificalNumberOfTasks(List<TaskDto> list);

}
