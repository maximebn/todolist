package com.todolist.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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
		List <TacheDtoMax> tacheListDto = new ArrayList<>();
		
		if (u.isPresent()) {
			
			for (Projet p : u.get().getProjets()) {
				p.getTaches().stream()
									.filter(tache -> (tache.getDate().isEqual(date) && (tache.getStatut() != AttributsStatutsTaches.DONE)) || tache.getStatut() == AttributsStatutsTaches.ENRETARD)
									.map(tache -> new TacheDtoMax(tache))
									.forEach(dtotache -> tacheListDto.add(dtotache));
				};
				return tacheListDto;
		}
		else {
			throw new NotFoundException ("Utilisateur non trouvé");
		}
	}
	
	// ---------------------------------------------------------------------------------------------------------------------------//
	/**
	 * Lister les tâches d'un utilisateur pour les 6 prochains jours
	 * @param date
	 * @return List
	 */
	
	@Override
		public List<TacheDtoMax> findForWeek(LocalDate startDate, long idUtilisateur) {
			List <TacheDtoMax> tacheListDto = new ArrayList<>();
			
			for (int i=0; i<7; i++) {
				LocalDate date = startDate.plusDays(i);
				this.findByDate(date, idUtilisateur)
					.forEach(dtotache -> tacheListDto.add(dtotache));
				}
			
			return tacheListDto;
		}
	
	
	// ---------------------------------------------------------------------------------------------------------------------------//
	/** Tri d'une liste de tâches par date d'échéance.
	 * @param List
	 * @return List
	 */
	@Override
	public List<TacheDtoMax> triTacheByDate(List<TacheDtoMax> list) {
		list.sort((l1, l2) -> l1.getDate().compareTo(l2.getDate()));
		return list;
	}
	
	
	// ---------------------------------------------------------------------------------------------------------------------------//
	/** Mise à jour du statut de toutes les taches de la base de données
	 *  Passage de l'état En cours à l'état En retard si la date d'échéance est inférieure à la date du jour
	 *  @param List
	 *  @return void
	 */
	@Scheduled(cron = " 0 0 1 * * * " )
	public void updateStatutTaches(List<TacheDtoMax> list) {
		List<Tache> taches = tacheRepository.findAll();
		taches.stream()
				.filter(tache -> tache.getStatut()== AttributsStatutsTaches.ENCOURS && tache.getDate().isBefore(LocalDate.now()) )
				.forEach(tache -> tache.setStatut(AttributsStatutsTaches.ENRETARD));
	}

	// ---------------------------------------------------------------------------------------------------------------------------//
	/** Delete une tache, via son id
	*  @param List
	*  @return void
	*/
	@Override
	public void deleteById(Long idTache) {
		tacheRepository.deleteById(idTache);
		return;
	}

	// ---------------------------------------------------------------------------------------------------------------------------//
		/** Delete une tache, via son id
		*  @param idUtilisateur
		*  @return list
		*/
	
	@Override
	public List<TacheDto> findAll(Long idUtilisateur) {
		List<Projet> projets = utilisateurRepository.findById(idUtilisateur).get().getProjets();
		List<TacheDto> tachesDto = new ArrayList<>();
		for (Projet projet : projets) {
			List<Tache> taches = projet.getTaches();
			taches.stream().filter(tache -> tache.getStatut() != AttributsStatutsTaches.DONE).map(tache -> new TacheDto(tache, projet.getId())).forEach(tacheDto-> tachesDto.add(tacheDto));;
			
		}
		return tachesDto;
	}

	// ---------------------------------------------------------------------------------------------------------------------------//
			/**Modifie une tache, via son id
			*  @param tacheDto
			*  @return 
			*/
		@Override
		public void update(TacheDto tacheDto) {
			Tache tache = tacheRepository.findById(tacheDto.getId()).get();
			tache.setTitre(tacheDto.getTitre());
			tache.setDate(tacheDto.getDate());
			tache.setPriorite(tacheDto.getPriorite());
			tache.setStatut(tacheDto.getStatut());
			
			List<Tache> taches= projetRepository.findById(tacheDto.getIdProjet()).get().getTaches();
			taches.add(tache);
			projetRepository.findById(tacheDto.getIdProjet()).get().setTaches(taches);
			
			tacheRepository.save(tache);
			return;
			
		}

	
	
}
