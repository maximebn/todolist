package com.todolist.service;

import java.util.List;

import javax.mail.internet.AddressException;

import com.todolist.dto.TacheDtoMax;
import com.todolist.dto.UtilisateurDto;

public interface IUtilisateurService {
	
	UtilisateurDto register(UtilisateurDto userDto) throws AddressException;

	UtilisateurDto updateUserData(UtilisateurDto userDto, long idUtilisateur) throws AddressException;

	void deleteAccount(long idUtilisateur);

	long calculTotalParPriorite(List<TacheDtoMax> list);

	long getIndicePerformance(long idUtilisateur);

}
