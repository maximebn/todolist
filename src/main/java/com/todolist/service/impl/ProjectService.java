package com.todolist.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todolist.dto.ProjectDto;
import com.todolist.dto.TaskDto;
import com.todolist.exception.NotFoundException;
import com.todolist.persistence.entity.Project;
import com.todolist.persistence.entity.User;
import com.todolist.persistence.repository.ProjectRepository;
import com.todolist.persistence.repository.UserRepository;
import com.todolist.service.IProjectService;
import com.todolist.service.ITaskService;
import com.todolist.utils.TasksStatusAttributes;


/**
 * @author maximebn
 */
@Service
@Transactional
public class ProjectService implements IProjectService{

	@Autowired 
	ProjectRepository projectRepo;
	@Autowired
	UserRepository userRepo;
	@Autowired
	ITaskService taskService;

	
	
	/******************************************************************************************************
	 * New project
	 * @param projectDto
	 * @param userId
	 * @return ProjectDto
	 */
	@Override
	public ProjectDto save(ProjectDto projectDto, Long userId) {
		Project project= new Project();
		project.setTitle(projectDto.getTitle());
		
		Optional<User> user = userRepo.findById(userId);
		
		if (user.isPresent()) {
			user.get().getProjects().add(project);
			user.get().setProjets(user.get().getProjects());
			
			projectRepo.save(project);
			projectDto.setId(project.getId());
			return projectDto;
		}
		else throw new NotFoundException(NotFoundException.UNRECOGNIZEDUSER);
	}
	
	

	/******************************************************************************************************
	 * All projects for a given user
	 * @param userId
	 * @return List ProjetDto
	 */
	@Override
	public List<ProjectDto> findAll(Long userId) {
		Optional<User> user = userRepo.findById(userId);
		
		if (user.isPresent()) {
			return user.get().getProjects().stream()
					.map(project -> new ProjectDto(project)).collect(Collectors.toList());
		}
		else throw new NotFoundException(NotFoundException.UNRECOGNIZEDUSER);
	}
	

	/******************************************************************************************************
	 * Tasks of a project for a given user
	 * @param idProject
	 * @return List TaskDto
	 */
	@Override
	public List<TaskDto> findById(Long projectId) {
		Optional<Project> project = projectRepo.findById(projectId);
		ProjectDto projectDto= new ProjectDto(project.get());
		
		if (project.isPresent()) {
			
			List<TaskDto>tasksDto=project.get().getTasks().stream()
					.filter(task ->task.getStatus().compareTo(TasksStatusAttributes.DONE) != 0)
				/**((tache.getStatut().equals(AttributsStatutsTaches.ENCOURS))|| (tache.getStatut().equals(AttributsStatutsTaches.ENRETARD))))
				*/	.map(task -> new TaskDto(task, projectDto)).collect(Collectors.toList());
			taskService.tasksSortingByDate(tasksDto);
			return tasksDto;
		}
		else throw new NotFoundException(NotFoundException.UNRECOGNIZEDPROJECT);
	}
	

	/******************************************************************************************************
	 * Deleting a project for a given user
	 * @param projectId
	 * @return List DtoProject
	 */
	@Override
	public void deleteById(Long projectId) {
		projectRepo.deleteById(projectId);
	}

	
	
	/******************************************************************************************************
	 * Updating a project for a given user
	 * @param projectDto
	 */
	@Override
	public void update(ProjectDto projectDto) {
		Optional<Project> project = projectRepo.findById(projectDto.getId());
		
		if (project.isPresent()) {
			project.get().setTitle(projectDto.getTitle());
			projectRepo.save(project.get());	
		}
		else throw new NotFoundException(NotFoundException.UNRECOGNIZEDPROJECT);
	}
	
}
