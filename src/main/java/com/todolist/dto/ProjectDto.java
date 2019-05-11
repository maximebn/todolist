package com.todolist.dto;

import com.todolist.persistence.entity.Project;

public class ProjectDto {
	
	private Long id;
	private String title;
	
	public ProjectDto() {
		super();
	}
	
	public ProjectDto(Project project) {
		this.setId(project.getId());
		this.setTitle(project.getTitle());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	
}
