package com.todolist.service.impl;

import java.util.Optional;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todolist.persistence.entity.Utilisateur;
import com.todolist.persistence.repository.UtilisateurRepository;
import com.todolist.service.IEmailService;

@Service
@Transactional
public class EmailService implements IEmailService {
@Autowired UtilisateurRepository utilisateurRepository;

	
	public  boolean isValidEmailAddress(String email) {
		boolean result = true;
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (AddressException ex) {
			result = false;
		}
		return result;
		}
    
    public boolean  isEmailAlreadyUsed(String email) {
    	Optional<Utilisateur> result = utilisateurRepository.findAll().stream().filter(user -> user.getMail().equals(email)).findFirst();
    	return (result.isPresent()) ;
    }
}