package com.todolist.controller;

import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
public UtilisateurDto save(@RequestBody UtilisateurDto  userDto) throws AddressException {
	return utilisateurService.register(userDto);
}

@PostMapping(value="/user/update")
public UtilisateurDto save(@RequestBody UtilisateurDto  userDto, @RequestParam Long idUtilisateur) throws AddressException {
	return utilisateurService.updateUserData(userDto, idUtilisateur);
}
		

}
