package com.todolist.service;

import java.util.List;

import javax.mail.internet.AddressException;

import com.todolist.dto.DtoPerf;
import com.todolist.dto.TacheDto;
import com.todolist.dto.UtilisateurDto;
import com.todolist.persistence.entity.Utilisateur;

public interface IUtilisateurService {
	
	UtilisateurDto register(UtilisateurDto userDto) throws AddressException;

	UtilisateurDto updateUserData(UtilisateurDto userDto, long idUtilisateur) throws AddressException;

	void deleteAccount(long idUtilisateur);

	DtoPerf getIndicePerformance(long idUtilisateur);

	Utilisateur findByLoginAndPassword(String username, String password);

	double calculTotalParPriorite(List<TacheDto> list);

}
