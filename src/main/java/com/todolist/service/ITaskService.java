package com.todolist.service;

import java.time.LocalDate;
import java.util.List;

import com.todolist.dto.TaskDto;

public interface ITaskService {

	TaskDto save(TaskDto taskDto);
	
	List<TaskDto> findByDate(LocalDate date, long id);
	
	List<TaskDto> findForWeek(LocalDate today, long id);
	
	void deleteById(Long taskId);
	
	List<TaskDto> findAll(Long userId);
	
	void update(TaskDto taskDto);
	
	List<TaskDto> findDone(LocalDate date, long userId);
	
	List<TaskDto> findDoneForWeek(LocalDate startDate, long userId);
	
	void updateTasksStatusPerUser(long userId);
	
	List<TaskDto> tasksSortingByDate(List<TaskDto> list);	
}
