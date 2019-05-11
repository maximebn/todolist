package com.todolist.controller;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.todolist.dto.TaskDto;
import com.todolist.exception.NotIdentifiedException;
import com.todolist.service.ITaskService;
import com.todolist.utils.AuthChecker;

/**
 * @author maximebn
 */
@CrossOrigin
@RestController
@RequestMapping(value="/api/tache")



public class TaskController {
	
	@Autowired ITaskService taskService;
	@Autowired private AuthChecker authChecker;
	
	
	/***********************************************************************
	 * @param taskDto
	 * @return taskDto
	 ***********************************************************************/
	@PostMapping(value="/save")
	public TaskDto save(@RequestBody TaskDto taskDto) {	
		return taskService.save(taskDto);
	}
	
	
	/***********************************************************************
	 * returns today tasks list for the current authenticated user
	 * @param id
	 * @return List<TaskDto>
	 * @throws ParseException
	 **********************************************************************/
	@GetMapping(value="/todayList")
	public List<TaskDto> getForToday() {
		if (authChecker.isAnUser() == null) throw new NotIdentifiedException();
		long userId = authChecker.getUserIdFromToken();
		// Updating tasks status for each loading :
		taskService.updateTasksStatusPerUser(userId);
		
		LocalDate today = LocalDate.now();
		return taskService.findByDate(today, userId);
	}
	
	/***********************************************************************
	 * returns these next 6 days tasks list for the current authenticated user
	 * @param id
	 * @return List<TaskDto>
	 * @throws ParseException
	 **********************************************************************/
	@GetMapping(value="/weekList")
	public List<TaskDto> getForNextWeek(){
		if (authChecker.isAnUser() == null) throw new NotIdentifiedException();
		long userId = authChecker.getUserIdFromToken();
		
		LocalDate today = LocalDate.now();
		return taskService.findForWeek(today, userId);
	}

	/***********************************************************************
	 * @param taskId
	 **********************************************************************/
	@DeleteMapping(value="/deleteOne")
 	public void deleteById(@RequestParam Long taskId) {	
		taskService.deleteById(taskId);
	}
 		
	
	/***********************************************************************
	 * All user's tasks
	 * @param userId
	 * @return List<TaskDto>
	 **********************************************************************/
	@GetMapping(value="/findAll")
	public List<TaskDto> findAll() {
		if (authChecker.isAnUser() == null) throw new NotIdentifiedException();
		long userId = authChecker.getUserIdFromToken();
		
		return taskService.findAll(userId);
	}
	
	
	/***********************************************************************
	 * Editing a task
	 * @param taskDto
	 **********************************************************************/
	@PutMapping(value="/update")
	public void update(@RequestBody TaskDto taskDto) {
		taskService.update(taskDto);
	}
}
