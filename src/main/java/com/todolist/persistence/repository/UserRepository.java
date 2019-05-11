package com.todolist.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.todolist.persistence.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	@Query(value =  "SELECT * FROM utilisateur WHERE Mail = ?1", nativeQuery = true)
	User findByLogin(String username);
}