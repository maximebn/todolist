package com.todolist.persistence.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.todolist.persistence.entity.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long>{

}
