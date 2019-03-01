package com.todolist.service;

import javax.mail.internet.AddressException;

import com.todolist.dto.UtilisateurDto;

public interface IUtilisateurService {
	
	UtilisateurDto register(UtilisateurDto userDto) throws AddressException;

	UtilisateurDto updateUserData(UtilisateurDto userDto, long idUtilisateur) throws AddressException;

}
