package com.todolist.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.todolist.dto.ProjetDto;
import com.todolist.dto.TacheDto;
import com.todolist.exception.NotFoundException;
import com.todolist.persistence.entity.Projet;
import com.todolist.persistence.entity.Tache;
import com.todolist.persistence.entity.Utilisateur;
import com.todolist.persistence.repository.ProjetRepository;
import com.todolist.persistence.repository.TacheRepository;
import com.todolist.persistence.repository.UtilisateurRepository;
import com.todolist.service.IProjetService;
import com.todolist.service.ITacheService;
import com.todolist.utils.AttributsStatutsTaches;

@Service
@Transactional
public class TacheService implements ITacheService {
	
	@Autowired TacheRepository tacheRepository;
	@Autowired ProjetRepository projetRepository;
	@Autowired UtilisateurRepository utilisateurRepository;
	@Autowired IProjetService projetService;
	
	
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
		Long idProjet=tacheDto.getProjet().getId();
		List<Tache> taches= projetRepository.findById(idProjet).get().getTaches();
		taches.add(tache);
		projetRepository.findById(idProjet).get().setTaches(taches);
		
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
	public List<TacheDto> findByDate(LocalDate date, long idUtilisateur) {
		Optional<Utilisateur> u = utilisateurRepository.findById(idUtilisateur);
		List <TacheDto> tacheListDto = new ArrayList<>();
		
		if (u.isPresent()) {
			
			for (Projet p : u.get().getProjets()) {
				
				ProjetDto projetDto= new ProjetDto(p);
				p.getTaches().stream()
									.filter(tache -> (tache.getDate().isEqual(date) && (!tache.getStatut().equals(AttributsStatutsTaches.DONE))) || tache.getStatut().equals(AttributsStatutsTaches.ENRETARD))
									.map(tache -> new TacheDto(tache, projetDto))
									.forEach(dtotache -> tacheListDto.add(dtotache));
				}
				return tacheListDto;
		}
		else {
			throw new NotFoundException (NotFoundException.UNRECOGNIZEDUSER);
		}
	}
	
	// ---------------------------------------------------------------------------------------------------------------------------//
	/**
	 * Lister les tâches d'un utilisateur pour les 6 prochains jours
	 * @param date
	 * @return List
	 */
	
	@Override
		public List<TacheDto> findForWeek(LocalDate startDate, long idUtilisateur) {
			List <TacheDto> tacheListDto = new ArrayList<>();

			this.findByDate(startDate, idUtilisateur)
			.forEach(dtotache -> tacheListDto.add(dtotache));
			
			for (int i=1; i<7; i++) {
				LocalDate date = startDate.plusDays(i);
				this.findByDate(date, idUtilisateur)
					.stream()
					.filter(dtotache -> dtotache.getStatut().equals(AttributsStatutsTaches.ENCOURS))
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
	public List<TacheDto> triTacheByDate(List<TacheDto> list) {
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
	public void updateStatutTaches(List<TacheDto> list) {
		List<Tache> taches = tacheRepository.findAll();
		taches.stream()
				.filter(tache -> tache.getStatut().equals(AttributsStatutsTaches.ENCOURS) && tache.getDate().isBefore(LocalDate.now()) )
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
	}

	// ---------------------------------------------------------------------------------------------------------------------------//
		/** Delete une tache, via son id
		*  @param idUtilisateur
		*  @return list
		*/
	
	@Override
	public List<TacheDto> findAll(Long idUtilisateur) {
		Optional<Utilisateur> user = utilisateurRepository.findById(idUtilisateur);
		if (user.isPresent()) {
			List<Projet> projets = user.get().getProjets();
			List<TacheDto> tachesDto = new ArrayList<>();
			
			for (Projet projet : projets) {
				
				ProjetDto projetDto = new ProjetDto(projet);
				List<Tache> taches = projet.getTaches();
				taches.stream().filter(tache -> !tache.getStatut().equals(AttributsStatutsTaches.DONE)).map(tache -> new TacheDto(tache, projetDto)).forEach(tacheDto-> tachesDto.add(tacheDto));
				
				
			}	
			return tachesDto;
		}
		else throw new NotFoundException(NotFoundException.UNRECOGNIZEDUSER);
	}

	// ---------------------------------------------------------------------------------------------------------------------------//
			/**Modifie une tache, via son id
			*  @param tacheDto
			*  @return 
			*/
		@Override
		public void update(TacheDto tacheDto) {
			Optional<Tache> optTache = tacheRepository.findById(tacheDto.getId());
			
			if (optTache.isPresent()) {
				Tache tache = optTache.get();
				tache.setTitre(tacheDto.getTitre());
				tache.setDate(tacheDto.getDate());
				tache.setPriorite(tacheDto.getPriorite());
				tache.setStatut(tacheDto.getStatut());
				Long idProjet=tacheDto.getProjet().getId();
				List<Tache> taches= projetRepository.findById(idProjet).get().getTaches();
				taches.add(tache);
				projetRepository.findById(idProjet).get().setTaches(taches);
				tacheRepository.save(tache);			
			}
			else throw new NotFoundException(NotFoundException.UNRECOGNIZEDTASK);	
		}	
	
}
