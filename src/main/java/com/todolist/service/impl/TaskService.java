package com.todolist.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.todolist.dto.ProjectDto;
import com.todolist.dto.TaskDto;
import com.todolist.exception.NotFoundException;
import com.todolist.persistence.entity.Project;
import com.todolist.persistence.entity.Task;
import com.todolist.persistence.entity.User;
import com.todolist.persistence.repository.ProjectRepository;
import com.todolist.persistence.repository.TaskRepository;
import com.todolist.persistence.repository.UserRepository;
import com.todolist.service.IProjectService;
import com.todolist.service.ITaskService;
import com.todolist.utils.TasksStatusAttributes;


/**
 * @author maximebn
 */
@Service
@Transactional
public class TaskService implements ITaskService {
	
	@Autowired TaskRepository taskRepo;
	@Autowired ProjectRepository projectRepo;
	@Autowired UserRepository userRepo;
	@Autowired IProjectService projectService;
	
	
	/******************************************************************************************************
	 * Task persist
	 * @param TaskDto
	 * @return taskDto
	 */
	@Override
	public TaskDto save(TaskDto taskDto) {
		Task task = new Task();
		task.setTitle(taskDto.getTitle());
		task.setDate(taskDto.getDate());
		task.setPriority(taskDto.getPriority());
		task.setStatus(TasksStatusAttributes.CURRENT);
		
		Long projectId = taskDto.getProject().getId();
		List<Task> tasks= projectRepo.findById(projectId).get().getTasks();
		
		tasks.add(task);
		projectRepo.findById(projectId).get().setTasks(tasks);
		
		taskRepo.save(task);
		taskDto.setStatus(task.getStatus());
		taskDto.setId(task.getId());
		return taskDto;
	}
	
	
	/******************************************************************************************************
	 * User's task listing
	 * @param date
	 * @return List
	 */
	@Override
	public List<TaskDto> findByDate(LocalDate date, long userId) {
		Optional<User> u = userRepo.findById(userId);
		List <TaskDto> taskDtoList = new ArrayList<>();
		
		if (u.isPresent()) {
			
			for (Project p : u.get().getProjects()) {
				
				ProjectDto projectDto= new ProjectDto(p);
				p.getTasks().stream()
									.filter(task -> (task.getDate().isEqual(date) && (!task.getStatus().equals(TasksStatusAttributes.DONE))) || task.getStatus().equals(TasksStatusAttributes.INLATE))
									.map(task -> new TaskDto(task, projectDto))
									.forEach(taskDto -> taskDtoList.add(taskDto));
				}
				return taskDtoList;
		}
		else {
			throw new NotFoundException (NotFoundException.UNRECOGNIZEDUSER);
		}
	}
	
	
	/******************************************************************************************************
	 * Listing a given user's tasks, at a given date, for which status is DONE 
	 * @param date
	 * @return List
	 */
	@Override
	public List<TaskDto> findDone(LocalDate date, long userId) {
		Optional<User> u = userRepo.findById(userId);
		List <TaskDto> taskDtoList = new ArrayList<>();
		
		if (u.isPresent()) {
			
			for (Project p : u.get().getProjects()) {
				
				ProjectDto projectDto= new ProjectDto(p);
				p.getTasks().stream()
									.filter(task -> (task.getDate().isEqual(date) && (task.getStatus().equals(TasksStatusAttributes.DONE))))
									.map(task -> new TaskDto(task, projectDto))
									.forEach(taskDto -> taskDtoList.add(taskDto));
				}
				return taskDtoList;
		}
		else {
			throw new NotFoundException (NotFoundException.UNRECOGNIZEDUSER);
		}
	}
	
	
	/******************************************************************************************************
	 * listing a given user's DONE tasks for next 6 days
	 * @param date
	 * @return List
	 */
	@Override
		public List<TaskDto> findDoneForWeek(LocalDate startDate, long userId) {
			List <TaskDto> taskDtoList = new ArrayList<>();

			for (int i=0; i<7; i++) {
				LocalDate date = startDate.plusDays(i);
				this.findDone(date, userId)
					.stream()
					.forEach(taskDto -> taskDtoList.add(taskDto));
				}
			return taskDtoList;
		}
	
	
	/******************************************************************************************************
	 * listing a given user's tasks for next 6 days
	 * @param date
	 * @return List
	 */
	@Override
		public List<TaskDto> findForWeek(LocalDate startDate, long userId) {
			List <TaskDto> taskDtoList = new ArrayList<>();
			this.findByDate(startDate, userId)
				.stream()
				.forEach(taskDto -> taskDtoList.add(taskDto));
				
				for (int i=1; i<7; i++) {
					LocalDate date = startDate.plusDays(i);
					this.findByDate(date, userId)
						.stream()
						.filter(taskDto -> taskDto.getStatus().equals(TasksStatusAttributes.CURRENT))
						.forEach(taskDto -> taskDtoList.add(taskDto));
					}
				return taskDtoList;
			}
		
	
	/******************************************************************************************************
	/** Sorting a tasks' list by expiration date
	 * @param List
	 * @return List
	 */
	@Override
	public List<TaskDto> tasksSortingByDate(List<TaskDto> list) {
		list.sort((l1, l2) -> l1.getDate().compareTo(l2.getDate()));
		return list;
	}
	
	
	/******************************************************************************************************
	/** Updating status of all tasks
	 *  DONE OR LATE, EVERY DAY. Updating status is also made elsewhere, each time user connects its account.
	 *  @param List
	 *  @return void
	 */
	@Scheduled(cron = " 0 0 1 * * * " )
	public void updateStatutTaches() {
		List<Task> taches = taskRepo.findAll();
		taches.stream()
				.filter(tache -> tache.getStatus().equals(TasksStatusAttributes.CURRENT) && tache.getDate().isBefore(LocalDate.now()) )
				.forEach(tache -> tache.setStatus(TasksStatusAttributes.INLATE));
	}
	
	
	
	/******************************************************************************************************
	/** Updating tasks' status of one single user
	 *  DONE OR LATE, EVERY DAY. Updating status is also made elsewhere, each time user connects its account.
	 *  @param List
	 *  @return void
	 */
	 public void updateTasksStatusPerUser(long userId) {
		Optional<User> user = userRepo.findById(userId);
		if (user.isPresent()) {
			List<Project> projects = user.get().getProjects();
				
			for (Project project : projects) {
				List<Task> tasks = project.getTasks();
				tasks.stream()
				.filter(task -> task.getStatus().equals(TasksStatusAttributes.CURRENT) && task.getDate().isBefore(LocalDate.now()) )
				.forEach(task -> task.setStatus(TasksStatusAttributes.INLATE));
			}
		}
	}

	/******************************************************************************************************
	/** Deleting a task by id
	*  @param List
	*  @return void
	*/
	@Override
	public void deleteById(Long idTache) {
		taskRepo.deleteById(idTache);
	}

	
	
	/******************************************************************************************************
	/** Listing all tasks for a given user
	*  @param userId
	*  @return list
	*/
	@Override
	public List<TaskDto> findAll(Long userId) {
		Optional<User> user = userRepo.findById(userId);
		if (user.isPresent()) {
			List<Project> projects = user.get().getProjects();
			List<TaskDto> tasksDto = new ArrayList<>();
			
			for (Project project : projects) {
				
				ProjectDto projectDto = new ProjectDto(project);
				List<Task> tasks = project.getTasks();
				tasks.stream().filter(task -> !task.getStatus().equals(TasksStatusAttributes.DONE)).map(task -> new TaskDto(task, projectDto)).forEach(taskDto-> tasksDto.add(taskDto));
				
				
			}	
			this.tasksSortingByDate(tasksDto);
			return tasksDto;
		}
		else throw new NotFoundException(NotFoundException.UNRECOGNIZEDUSER);
	}
	
	

	/******************************************************************************************************
	/** Editing a task by Id
     *  @param taskDto
	 *  @return 
     */
	@Override
	public void update(TaskDto taskDto) {
		Optional<Task> opt = taskRepo.findById(taskDto.getId());
			
			if (opt.isPresent()) {
				Task task = opt.get();
				task.setTitle(taskDto.getTitle());
				task.setDate(taskDto.getDate());
				task.setPriority(taskDto.getPriority());
				if(taskDto.getStatus().toLowerCase().compareTo("effectuée")==0 ) {
					task.setStatus(TasksStatusAttributes.DONE);
				}
				else if (taskDto.getStatus().toLowerCase().compareTo("en retard")==0) {
					task.setStatus(TasksStatusAttributes.INLATE);
				}
				else { task.setStatus(TasksStatusAttributes.CURRENT);
				}
				Long idProject=taskDto.getProject().getId();
				List<Task> tasks= projectRepo.findById(idProject).get().getTasks();
				tasks.add(task);
				projectRepo.findById(idProject).get().setTasks(tasks);
				taskRepo.save(task);			
			}
			else throw new NotFoundException(NotFoundException.UNRECOGNIZEDTASK);	
		}


	
}
