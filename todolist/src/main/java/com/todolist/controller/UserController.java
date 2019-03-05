package com.todolist.controller;


import java.security.Principal;

import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todolist.dto.UtilisateurDto;
import com.todolist.exception.NotIdentifiedException;
import com.todolist.service.IProjetService;
import com.todolist.service.IUtilisateurService;
import com.todolist.utils.AuthChecker;

@RestController
@RequestMapping(value="/api")
public class UserController {
	
@Autowired IProjetService projetService;
@Autowired IUtilisateurService utilisateurService;
@Autowired private AuthChecker authChecker;

@PostMapping(value="/registration")
public UtilisateurDto save(@RequestBody UtilisateurDto  userDto) throws AddressException {
	return utilisateurService.register(userDto);
}

@RequestMapping("/user/revoke-token")
public void  logout(Principal principal)  {
    authChecker.revokeToken(principal);
}

@PostMapping(value="/user/update")
public UtilisateurDto update(@RequestBody UtilisateurDto  userDto) throws AddressException {
	if (authChecker.isUtilisateur() == null) throw new NotIdentifiedException();
	long idUtilisateur = authChecker.getUserIdFromToken();
	
	return utilisateurService.updateUserData(userDto, idUtilisateur);
}

@GetMapping(value="/user/completionIndex")
public double getCompletionIndex() {
	if (authChecker.isUtilisateur() == null) throw new NotIdentifiedException();
	long idUtilisateur = authChecker.getUserIdFromToken();
	
	return utilisateurService.getIndicePerformance(idUtilisateur);
}	


}
