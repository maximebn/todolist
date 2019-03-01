package com.todolist.utils;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.todolist.persistence.entity.Utilisateur;

@Component
public class AuthChecker {

	public Utilisateur isUtilisateur() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		boolean isUser = SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"));
		if (principal != null && principal instanceof Utilisateur && isUser) {
			return (Utilisateur)principal;
		} else {
			return null;
		}
	}

		
	// Obtenir l'id à partir du token généré :
	public long getUserIdFromToken () {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Utilisateur user = (Utilisateur)principal;
		return user.getId();
	}
	
}
