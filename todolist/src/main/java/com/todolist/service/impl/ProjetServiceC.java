package com.todolist.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.todolist.dto.ProjetDtoC;
import com.todolist.persistence.entity.Projet;
import com.todolist.persistence.repository.ProjetRepository;
import com.todolist.service.IProjetServiceC;

public class ProjetServiceC implements IProjetServiceC{

	@Autowired 
	ProjetRepository projetRepository;
	
	@Override
	public ProjetDtoC save(ProjetDtoC projetDto) {
		Projet projet= new Projet();
		projet.setTitre(projetDto.getTitre());
		projetRepository.save(projet);
		projetDto.setId(projet.getId());
		
		
		return projetDto;
	}
	

}
