package com.todolist.service.impl;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.mail.internet.AddressException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todolist.dto.ProjetDtoC;
import com.todolist.dto.TacheDtoMax;
import com.todolist.dto.UtilisateurDto;
import com.todolist.exception.NotFoundException;
import com.todolist.persistence.entity.Utilisateur;
import com.todolist.persistence.repository.UtilisateurRepository;
import com.todolist.service.IEmailService;
import com.todolist.service.IProjetServiceC;
import com.todolist.service.ITacheService;
import com.todolist.service.IUtilisateurService;
import com.todolist.utils.AttributsPrioriteTaches;
import com.todolist.utils.AttributsStatutsTaches;


@Service
@Transactional
public class UtilisateurService implements IUtilisateurService {
	private static String UnrecognizedUser = "Id de l'utilisateur non reconnu";
	private static String UnrecognizedMail= "Adresse mail non valide ou déjà existante";

	
	@Autowired UtilisateurRepository utilisateurRepository;
	@Autowired IProjetServiceC projetService;
	@Autowired ITacheService tacheService;;
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
			throw new AddressException(UnrecognizedMail);
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
				else throw new AddressException(UnrecognizedMail);
			}
			user.setPrenom(userDto.getPrenom());
			user.setPassword(userDto.getPassword());
			utilisateurRepository.save(user);
			return userDto;
			
		}
		else throw new NotFoundException(UnrecognizedUser);
		}
	// ---------------------------------------------------------------------------------------------------------------------------//
	
	
	// ---------------------------------------------------------------------------------------------------------------------------//
			/**
			 * Suppression d'un compte utilisateur :
			 * @param idUtilisateur
			 * @return void
			 */
		@Override
		public void deleteAccount(long idUtilisateur) {
			Optional<Utilisateur> opt = utilisateurRepository.findById(idUtilisateur);
			
			if (opt.isPresent()) {
				utilisateurRepository.deleteById(idUtilisateur);
			}
			else throw new NotFoundException(UnrecognizedUser);
		}
		
	
		// ---------------------------------------------------------------------------------------------------------------------------//
		/**
		 * Calcul d'un nombre total de taches majoré ou monioré selon la priorité des taches contenues dans la liste :
		 * @param liste de taches
		 * @return long
		 */
	@Override
		public long calculTotalParPriorite(List<TacheDtoMax> list) {
			long nTransforme = 0;
			for (TacheDtoMax t : list) {
				if (t.getPriorite() != null) {
					if (t.getPriorite() == AttributsPrioriteTaches.PRIORITAIRE)
						nTransforme = nTransforme+ 5;
					else if (t.getPriorite() == AttributsPrioriteTaches.IMPORTANTE)
						 nTransforme = nTransforme+ 2;
					else if (t.getPriorite() == AttributsPrioriteTaches.NORMALE)
						 nTransforme = nTransforme+ 1;
					}
				else nTransforme = nTransforme+ 1;
				}
			System.out.println(nTransforme);
			return nTransforme;
		}
	
	
	// ---------------------------------------------------------------------------------------------------------------------------//
			/**
			 * Calcul d'un indice de performance, nombre de taches effectuées/nombre de taches total selon priorité :
			 * @param idUtilisateur
			 * @return long
			 */
		@Override
		public long getIndicePerformance(long idUtilisateur) {
			Optional<Utilisateur> opt = utilisateurRepository.findById(idUtilisateur);
			LocalDate sixDaysAgo = LocalDate.now().minusDays(6);
			
			if (opt.isPresent()) {
				List <TacheDtoMax> tachesEnRetard = tacheService.findForWeek(sixDaysAgo, idUtilisateur).stream().filter(t -> t.getStatut()==AttributsStatutsTaches.ENRETARD).collect(Collectors.toList());
				List <TacheDtoMax> tachesEffectuees= tacheService.findForWeek(sixDaysAgo, idUtilisateur).stream().filter(t -> t.getStatut()==AttributsStatutsTaches.DONE).collect(Collectors.toList());
				System.out.println(tachesEffectuees.get(1));
				
				double nbreTotal = (double)(this.calculTotalParPriorite(tachesEffectuees) + this.calculTotalParPriorite(tachesEnRetard));
				double indiceDePerformance = 0;
				
				if (nbreTotal != 0) {
					indiceDePerformance = (this.calculTotalParPriorite(tachesEffectuees) / nbreTotal)*100;
				}
				return (long)indiceDePerformance;
			}
			else throw new NotFoundException(UnrecognizedUser);
		}
			
}

