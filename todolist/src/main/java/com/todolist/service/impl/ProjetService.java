package com.todolist.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todolist.dto.ProjetDto;
import com.todolist.dto.TacheDto;
import com.todolist.exception.NotFoundException;
import com.todolist.persistence.entity.Projet;
import com.todolist.persistence.entity.Utilisateur;
import com.todolist.persistence.repository.ProjetRepository;
import com.todolist.persistence.repository.UtilisateurRepository;
import com.todolist.service.IProjetService;
import com.todolist.utils.AttributsStatutsTaches;


/**
 * @author Dell
 *
 */
@Service
@Transactional
public class ProjetService implements IProjetService{

	@Autowired 
	ProjetRepository projetRepository;
	
	@Autowired
	UtilisateurRepository utilisateurRepository;
	

	/**
	 * Créer un projet
	 * @param projetDto
	 * @param idUtilisateur
	 * @return ProjetDto
	 */
	@Override
	public ProjetDto save(ProjetDto projetDto, Long idUtilisateur) {
		Projet projet= new Projet();
		projet.setTitre(projetDto.getTitre());
		
		Optional<Utilisateur> user = utilisateurRepository.findById(idUtilisateur);
		
		if (user.isPresent()) {
			user.get().getProjets().add(projet);
			user.get().setProjets(user.get().getProjets());
			
			projetRepository.save(projet);
			projetDto.setId(projet.getId());
			return projetDto;
		}
		else throw new NotFoundException(NotFoundException.UNRECOGNIZEDUSER);
	}

	/**
	 * Lister l'ensemble des projets d'un utilisateur donné.
	 * @param idUtilisateur
	 * @return List ProjetDto
	 */
	@Override
	public List<ProjetDto> findAll(Long idUtilisateur) {
		Optional<Utilisateur> user = utilisateurRepository.findById(idUtilisateur);
		
		if (user.isPresent()) {
			return user.get().getProjets().stream()
					.map(projet -> new ProjetDto(projet)).collect(Collectors.toList());
		}
		else throw new NotFoundException(NotFoundException.UNRECOGNIZEDUSER);
	}

	/**
	 * Lister les tâches d'un projet d'un utilisateur donnée.
	 * @param idProjet
	 * @return List TacheDto
	 */
	@Override
	public List<TacheDto> findById(Long idProjet) {
		Optional<Projet> projet = projetRepository.findById(idProjet);
		ProjetDto projetDto= new ProjetDto(projet.get());
		
		if (projet.isPresent()) {
			
			return projet.get().getTaches().stream()
					.filter(tache -> (tache.getStatut() != AttributsStatutsTaches.DONE))
					.map(tache -> new TacheDto(tache,projetDto)).collect(Collectors.toList());
		}
		else throw new NotFoundException(NotFoundException.UNRECOGNIZEDPROJECT);
	}

	/**
	 * Supprimer un projet d'un utilisateur donné.
	 * @param idProjet
	 * @return List ProjetDto mise à jour
	 */
	@Override
	public void deleteById(Long idProjet) {
		projetRepository.deleteById(idProjet);
	}

	/**
	 * Mettre à jour un projet d'un utilisateur donné.
	 * @param projetDto
	 */
	@Override
	public void update(ProjetDto projetDto) {
		Optional<Projet> projet = projetRepository.findById(projetDto.getId());
		
		if (projet.isPresent()) {
			projet.get().setTitre(projetDto.getTitre());
			projetRepository.save(projet.get());	
		}
		else throw new NotFoundException(NotFoundException.UNRECOGNIZEDPROJECT);
	}
	
}
