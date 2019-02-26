package com.todolist.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todolist.persistence.entity.Tache;

public interface TacheRepository extends JpaRepository<Tache, Long>{

}
