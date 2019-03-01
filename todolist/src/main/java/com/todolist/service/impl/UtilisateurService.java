package com.todolist.service.impl;


import java.util.Optional;

import javax.mail.internet.AddressException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todolist.dto.ProjetDtoC;
import com.todolist.dto.UtilisateurDto;
import com.todolist.exception.NotFoundException;
import com.todolist.persistence.entity.Utilisateur;
import com.todolist.persistence.repository.UtilisateurRepository;
import com.todolist.service.IEmailService;
import com.todolist.service.IProjetServiceC;
import com.todolist.service.IUtilisateurService;


@Service
@Transactional
public class UtilisateurService implements IUtilisateurService {
	
	@Autowired UtilisateurRepository utilisateurRepository;
	@Autowired IProjetServiceC projetService;
	@Autowired IEmailService emailService;
	//@Autowired
	//PasswordEncoder passwordEncoder;

	
	// ---------------------------------------------------------------------------------------------------------------------------//
	/**
	 * Enregistrer un nouvel utilisateur : vérification validité du mail (=username) et initialisation projet Inbox
	 * @param UtilisateurDto
	 * @return UtilisateurDto
	 */
	@Override
	public UtilisateurDto register(UtilisateurDto userDto) throws AddressException {
		if (emailService.isValidEmailAddress(userDto.getMail()) && !emailService.isEmailAlreadyUsed(userDto.getMail())) {

		//String encryptPass = passwordEncoder.encode(userDto.getPassword());
		//userDto.setPassword(encryptPass);
		
		Utilisateur user = new Utilisateur();
		user.setPrenom(userDto.getPrenom());
		user.setMail(userDto.getMail());
		user.setPassword(userDto.getPassword());
		
		utilisateurRepository.save(user);
		
		ProjetDtoC projetDto = new ProjetDtoC();
		projetDto.setTitre("Inbox");
		projetService.save(projetDto, user.getId());

		return userDto;
	}
		else {
			throw new AddressException("Adresse mail non valide ou déjà existante");
		}
	}
	
	
	// ---------------------------------------------------------------------------------------------------------------------------//
		/**
		 * Mise à jour données utilisateur : modification du mot de passe d'un utilisateur, du prénom, de l'adresse mail ...
		 * @param UtilisateurDto
		 * @return UtilisateurDto
		 */
	@Override
	public UtilisateurDto updateUserData(UtilisateurDto userDto, long idUtilisateur) throws AddressException {		
		Optional<Utilisateur> opt = utilisateurRepository.findById(idUtilisateur);
		
		if (opt.isPresent()) {
			Utilisateur user = opt.get();
			
			if (!userDto.getMail().equals(user.getMail())) {
				if (emailService.isValidEmailAddress(userDto.getMail()) && !emailService.isEmailAlreadyUsed(userDto.getMail())) {
					user.setMail(userDto.getMail());
			}
				else throw new AddressException("Adresse mail non valide ou déjà existante");
			}
			user.setPrenom(userDto.getPrenom());
			user.setPassword(userDto.getPassword());
			utilisateurRepository.save(user);
			return userDto;
			
		}
		else throw new NotFoundException("Id utilisateur non reconnu");
		}
	// ---------------------------------------------------------------------------------------------------------------------------//


}
