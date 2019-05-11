package com.todolist.dto;

import java.time.LocalDate;

import com.todolist.persistence.entity.Task;

public class TaskDto {
	
	private String title;
	private LocalDate date;
	private String priority;
	private String status;
	private Long id;
	private ProjectDto project;


	public TaskDto() {
		super();
	}

	public TaskDto(Task task, ProjectDto projectDto) {
		this.setId(task.getId());
		this.setTitle(task.getTitle());
		this.setDate(task.getDate());
		this.setPriority(task.getPriority());
		this.setStatus(task.getStatus());
		this.setProject(projectDto);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProjectDto getProject() {
		return project;
	}

	public void setProject(ProjectDto project) {
		this.project = project;
	}
	
}
