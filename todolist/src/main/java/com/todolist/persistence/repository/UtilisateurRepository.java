package com.todolist.persistence.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.todolist.persistence.entity.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long>{
	
	@Query(value =  "SELECT * FROM utilisateur WHERE Mail = ?1", nativeQuery = true)
	Utilisateur findByLogin(String username);
	
}