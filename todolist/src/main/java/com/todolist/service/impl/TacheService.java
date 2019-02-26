package com.todolist.service.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todolist.dto.TacheDto;
import com.todolist.persistence.entity.Projet;
import com.todolist.persistence.entity.Tache;
import com.todolist.persistence.entity.Utilisateur;
import com.todolist.persistence.repository.ProjetRepository;
import com.todolist.persistence.repository.TacheRepository;
import com.todolist.persistence.repository.UtilisateurRepository;
import com.todolist.service.ITacheService;

@Service
@Transactional
public class TacheService implements ITacheService {
	@Autowired
	TacheRepository tacheRepository;
	
	@Autowired
	ProjetRepository projetRepository;
	
	@Autowired
	UtilisateurRepository utilisateurRepository;
	
	@Override
	public TacheDto save(TacheDto tacheDto) {
		Tache tache = new Tache();
		tache.setTitre(tacheDto.getTitre());
		tache.setDate(tacheDto.getDate());
		tache.setPriorite(tacheDto.getPriorite());
		tache.setStatut("En cours");
		
		Optional<Projet> projet= projetRepository.findById(tacheDto.getIdProjet());
		
		if (projet.isPresent()) {
			tache.setProjet(projet.get());
		}
		Optional<Utilisateur> utilisateur = utilisateurRepository.findById(tacheDto.getIdUtilisateur());
		if (utilisateur.isPresent()) {
			tache.setUtilisateur(utilisateur.get());
		}
		
		tacheRepository.save(tache);
		tacheDto.setStatut(tache.getStatut());
		tacheDto.setId(tache.getId());
		return tacheDto;
		
		
	}

}
