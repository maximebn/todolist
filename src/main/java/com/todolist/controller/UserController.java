package com.todolist.controller;


import java.security.Principal;

import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todolist.dto.PerformanceDto;
import com.todolist.dto.UserDto;
import com.todolist.exception.NotIdentifiedException;
import com.todolist.service.IProjectService;
import com.todolist.service.IUserService;
import com.todolist.utils.AuthChecker;


/**
 * @author maximebn
 */
@RestController
@CrossOrigin
@RequestMapping(value="/api")

public class UserController {
	
@Autowired IProjectService projectService;
@Autowired IUserService userService;
@Autowired private AuthChecker authChecker;



	/***********************************************************************
	 * @param userDto
	 * @return UserDto
	 ***********************************************************************/
	@PostMapping(value="/registration")
	public UserDto save(@RequestBody UserDto userDto) throws AddressException {
		return userService.register(userDto);
	}

	
	/***********************************************************************
	 * @param principal
	 * @return void
	 ***********************************************************************/
	@RequestMapping("/user/revoke-token")
	public void logout(Principal principal)  {
		authChecker.revokeToken(principal);
	}

	
	/***********************************************************************
	 * @param userDto
	 * @return UserDto
	 ***********************************************************************/
	@PostMapping(value="/user/update")
	public UserDto update(@RequestBody UserDto userDto) throws AddressException {
		if (authChecker.isAnUser() == null) throw new NotIdentifiedException();
		long userId = authChecker.getUserIdFromToken();
	
		return userService.updateUserData(userDto, userId);
	}


	/***********************************************************************
	 * @return PerformanceDto
	 ***********************************************************************/
	@GetMapping(value="/user/completionIndex")
	public PerformanceDto getCompletionIndex() {
		if (authChecker.isAnUser() == null) throw new NotIdentifiedException();
		long userId = authChecker.getUserIdFromToken();
		System.out.println(userId);
	
		return userService.getPerformanceIndex(userId);
	}	

	

	/***********************************************************************
	 * @return void
	 ***********************************************************************/
	@DeleteMapping(value="/user/delete")
	public void deleteAccount() throws AddressException {
		if (authChecker.isAnUser() == null) throw new NotIdentifiedException();
		long userId = authChecker.getUserIdFromToken();
		userService.deleteAccount(userId);
	}


}
