package com.todolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todolist.dto.UtilisateurDto;
import com.todolist.service.IProjetServiceC;
import com.todolist.service.IUtilisateurService;

@RestController
@RequestMapping(value="/api")
public class UserController {
	
@Autowired
IProjetServiceC projetService;

@Autowired
IUtilisateurService utilisateurService;

@PostMapping(value="/registration")
public UtilisateurDto save(@RequestBody UtilisateurDto  userDto) {
	return utilisateurService.save(userDto);
}
		

}
