package com.todolist.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todolist.dto.TacheDto;
import com.todolist.dto.TacheDtoMax;
import com.todolist.exception.NotFoundException;
import com.todolist.persistence.entity.Projet;
import com.todolist.persistence.entity.Tache;
import com.todolist.persistence.entity.Utilisateur;
import com.todolist.persistence.repository.ProjetRepository;
import com.todolist.persistence.repository.TacheRepository;
import com.todolist.persistence.repository.UtilisateurRepository;
import com.todolist.service.IProjetServiceC;
import com.todolist.service.ITacheService;
import com.todolist.utils.AttributsStatutsTaches;

@Service
@Transactional
public class TacheService implements ITacheService {
	@Autowired
	TacheRepository tacheRepository;
	
	@Autowired
	ProjetRepository projetRepository;
	
	@Autowired
	UtilisateurRepository utilisateurRepository;
	
	@Autowired 
	IProjetServiceC projetService;
	
	
	// ---------------------------------------------------------------------------------------------------------------------------//
		/**
		 * Persister une tâche
		 * @param TacheDto
		 * @return tacheDto
		 */
	@Override
	public TacheDto save(TacheDto tacheDto) {
		Tache tache = new Tache();
		tache.setTitre(tacheDto.getTitre());
		tache.setDate(tacheDto.getDate());
		tache.setPriorite(tacheDto.getPriorite());
		tache.setStatut(AttributsStatutsTaches.ENCOURS);
		
		List<Tache> taches= projetRepository.findById(tacheDto.getIdProjet()).get().getTaches();
		taches.add(tache);
		projetRepository.findById(tacheDto.getIdProjet()).get().setTaches(taches);
		

		tacheRepository.save(tache);
		tacheDto.setStatut(tache.getStatut());
		tacheDto.setId(tache.getId());
		return tacheDto;
		
	}
	
	// ---------------------------------------------------------------------------------------------------------------------------//
	/**
	 * Lister les tâches d'un utilisateur à une date donnée
	 * @param date
	 * @return List
	 */
	@Override
	public List<TacheDtoMax> findByDate(LocalDate date, long idUtilisateur) {
		Optional<Utilisateur> u = utilisateurRepository.findById(idUtilisateur);
		List <TacheDtoMax> tacheListDto = new ArrayList<TacheDtoMax>();
		
		if (u.isPresent()) {
			
			for (Projet p : u.get().getProjets()) {
				p.getTaches().stream()
									.filter(tache -> tache.getDate().isEqual(date) && (tache.getStatut() != AttributsStatutsTaches.DONE))
									.map(tache -> new TacheDtoMax(tache))
									.forEach(dtotache -> tacheListDto.add(dtotache));
				};
				return tacheListDto;
		}
		else {
			throw new NotFoundException ("Utilisateur non trouvé");
		}
	}
}
