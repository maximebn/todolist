package com.todolist.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todolist.dto.ProjetDtoC;
import com.todolist.dto.TacheDto;
import com.todolist.persistence.entity.Projet;
import com.todolist.persistence.entity.Tache;
import com.todolist.persistence.repository.ProjetRepository;
import com.todolist.persistence.repository.UtilisateurRepository;
import com.todolist.service.IProjetServiceC;
import com.todolist.utils.AttributsStatutsTaches;




@Service
@Transactional
public class ProjetServiceC implements IProjetServiceC{

	@Autowired 
	ProjetRepository projetRepository;
	
	@Autowired
	UtilisateurRepository utilisateurRepository;
	
	@Override
	public ProjetDtoC save(ProjetDtoC projetDto, Long idUtilisateur) {
		Projet projet= new Projet();
		projet.setTitre(projetDto.getTitre());
		
		List<Projet> projets=  utilisateurRepository.findById(idUtilisateur).get().getProjets();
		projets.add(projet);
		utilisateurRepository.findById(idUtilisateur).get().setProjets(projets);
	
		
		projetRepository.save(projet);
		projetDto.setId(projet.getId());
		
		
		return projetDto;
	}

	@Override
	public List<ProjetDtoC> findAll(Long idUtilisateur) {
		List<Projet> projets=  utilisateurRepository.findById(idUtilisateur).get().getProjets();
		List<ProjetDtoC> projetsDto= projets.stream().map(projet -> new ProjetDtoC(projet)).collect(Collectors.toList());
		return projetsDto;
	}

	@Override
	public List<TacheDto> findById(Long idProjet) {
		Projet projet = projetRepository.findById(idProjet).get();
		List<Tache> taches=projet.getTaches();
		List<TacheDto> list = taches.stream()
				.filter(tache -> (tache.getStatut() != AttributsStatutsTaches.DONE))
				.map(tache -> new TacheDto(tache,idProjet))
				.collect(Collectors.toList());
		
		return list;
	}

	@Override
	public List<ProjetDtoC> deleteById(Long idProjet) {
		projetRepository.deleteById(idProjet);
		
		return null;
	}
	
}
