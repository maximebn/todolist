package com.todolist.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todolist.dto.UtilisateurDto;
import com.todolist.persistence.entity.Utilisateur;
import com.todolist.persistence.repository.UtilisateurRepository;
import com.todolist.service.IUtilisateurService;


@Service
@Transactional
public class UtilisateurService implements IUtilisateurService {
	
	@Autowired
	UtilisateurRepository utilisateurRepository;
	
	//@Autowired
	//PasswordEncoder passwordEncoder;

	@Override
	public UtilisateurDto save(UtilisateurDto userDto) {
		//String encryptPass = passwordEncoder.encode(userDto.getPassword());
		//userDto.setPassword(encryptPass);
		Utilisateur user = new Utilisateur();
		user.setPrenom(userDto.getPrenom());
		user.setMail(userDto.getMail());
		user.setPassword(userDto.getPassword());
		
		utilisateurRepository.save(user);
		
		return userDto;
	}
}
