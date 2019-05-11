package com.todolist.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todolist.persistence.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long>{

}
