package com.todolist.persistence.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author maximebn
 * @description Project entity
 * @see Task
 */

@Entity
@Table(name="projet")
public class Project {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column (name = "id", unique = true, nullable = false)
	private Long id;
	@Column (name = "Titre_projet", length=100, nullable=false)
	private String title;
	
	@OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="IDProjet", referencedColumnName="id", nullable=false)
    private List<Task> tasks= new ArrayList<Task>();
	

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

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	
	
}
