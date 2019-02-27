package com.todolist.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todolist.dto.ProjetDtoC;
import com.todolist.persistence.entity.Projet;
import com.todolist.persistence.repository.ProjetRepository;
import com.todolist.persistence.repository.UtilisateurRepository;
import com.todolist.service.IProjetServiceC;




@Service
@Transactional
public class ProjetServiceC implements IProjetServiceC{

	@Autowired 
	ProjetRepository projetRepository;
	
	@Autowired
	UtilisateurRepository utilisateurRepository;
	
	@Override
	public ProjetDtoC save(ProjetDtoC projetDto) {
		Projet projet= new Projet();
		projet.setTitre(projetDto.getTitre());
		
		List<Projet> projets=  utilisateurRepository.findById(projetDto.getIdUtilisateur()).get().getProjets();
	projets.add(projet);
	utilisateurRepository.findById(projetDto.getIdUtilisateur()).get().setProjets(projets);
	
		
		projetRepository.save(projet);
		projetDto.setId(projet.getId());
		
		
		return projetDto;
	}
	

}
