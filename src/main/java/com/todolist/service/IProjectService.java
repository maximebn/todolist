package com.todolist.service;

import java.util.List;

import com.todolist.dto.ProjectDto;
import com.todolist.dto.TaskDto;

public interface IProjectService {

	ProjectDto save(ProjectDto projectDto, Long userId);

	List<ProjectDto> findAll(Long idUser);

	List<TaskDto> findById(Long idProject);

	void deleteById(Long idProject);

	void update(ProjectDto projectDto);

}
