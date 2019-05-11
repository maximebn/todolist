package com.todolist.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.todolist.dto.ProjectDto;
import com.todolist.dto.TaskDto;
import com.todolist.exception.NotIdentifiedException;
import com.todolist.service.IProjectService;
import com.todolist.utils.AuthChecker;

/**
 * @author maximebn
 */

@CrossOrigin
@RestController
@RequestMapping(value="/api/projet")

public class ProjectController {
	
@Autowired IProjectService projectService;
@Autowired private AuthChecker authChecker;

	
	/*******************************************************************************
	 * Creating project for a given user
	 * @param projectDto
	 * @return ProjectDto
	 * User must be token authenticated
	 *******************************************************************************/

	@PostMapping(value="/save")
	@ResponseBody
	public ProjectDto save(@RequestBody ProjectDto projectDto) {
		if (authChecker.isAnUser() == null) throw new NotIdentifiedException();
		long userId = authChecker.getUserIdFromToken();
		
		return projectService.save(projectDto, userId);
	}
	
	
	/*******************************************************************************
	 * Listing all projects for a given user
	 * @return List<ProjectDto>
	 * User must be token authenticated
	 *******************************************************************************/
	
	@GetMapping(value="/findAll")
	@ResponseBody
	public List<ProjectDto> findAll(){
		if (authChecker.isAnUser() == null) throw new NotIdentifiedException();
		
		long userId = authChecker.getUserIdFromToken();
		return projectService.findAll(userId);
	}
	
	/*******************************************************************************
	 * Listing all tasks for one user
	 * @param idProject
	 * @return List<TaskDto>
	  * User must be token authenticated
	 *******************************************************************************/
	
	@GetMapping(value="/findOne")
	@ResponseBody
	public List<TaskDto> findById( @RequestParam Long idProjet){
		return projectService.findById(idProjet);
	}
	
	/*******************************************************************************
	 * Deleting a project for a given user, means all related tasks are also deleted.
	 * @param idProject
	 * @return List<ProjectDto>
	  * User must be token authenticated
	 *******************************************************************************/
	
	@DeleteMapping(value="/deleteById")
	@ResponseBody
	public List<ProjectDto> deleteById(@RequestParam Long idProject){
		if (authChecker.isAnUser() == null) throw new NotIdentifiedException();
		long userId = authChecker.getUserIdFromToken();
		
		projectService.deleteById(idProject);
		 return projectService.findAll(userId);
	}
	
	
	/*******************************************************************************
	 * Updating a project for a given user
	 * @param projectDto
	  * User must be token authenticated
	 *******************************************************************************/
	
	@PutMapping(value="/update")
	@ResponseBody
	public void update(@RequestBody ProjectDto projectDto) {
		projectService.update(projectDto);
	}
}
