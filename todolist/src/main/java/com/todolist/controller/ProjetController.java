package com.todolist.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.todolist.dto.ProjetDto;
import com.todolist.dto.TacheDto;
import com.todolist.exception.NotIdentifiedException;
import com.todolist.service.IProjetService;
import com.todolist.utils.AuthChecker;

/**
 * @author Dell
 */
@RestController
@RequestMapping(value="/api/projet")
public class ProjetController {
	
@Autowired IProjetService projetService;
@Autowired private AuthChecker authChecker;

	
	/**
	 * Créer un projet pour un utilisateur donné.
	 * @param projetDto
	 * @return ProjetDto
	 * L'utilisateur doit être authentifié grâce au token.
	 */
	@PostMapping(value="/save")
	@ResponseBody
	public ProjetDto save(@RequestBody ProjetDto projetDto) {
		if (authChecker.isUtilisateur() == null) throw new NotIdentifiedException();
		long idUtilisateur = authChecker.getUserIdFromToken();
		
		return projetService.save(projetDto, idUtilisateur);
	}
	
	
	/**
	 * Lister l'ensemble des projets d'un utilisateur.
	 * @return List<ProjetDto>
	 * L'utilisateur doit être authentifié grâce au token.
	 */
	@GetMapping(value="/findAll")
	@ResponseBody
	public List<ProjetDto> findAll(){
		if (authChecker.isUtilisateur() == null) throw new NotIdentifiedException();
		
		long idUtilisateur = authChecker.getUserIdFromToken();
		return projetService.findAll(idUtilisateur);
	}
	
	/**
	 * Lister les tâches pour un projet donné d'un utilisateur.
	 * @param idProjet
	 * @return List<TacheDto>
	 * L'utilisateur doit être authentifié grâce au token.
	 */
	@GetMapping(value="/findOne")
	@ResponseBody
	public List<TacheDto> findById( @RequestParam Long idProjet){
		return projetService.findById(idProjet);
	}
	
	/**
	 * Suppression d'un projet d'un utilisateur donné, et de toutes les tâches contenues dans ce projet.
	 * @param idProjet
	 * @return List<ProjetDto>
	 * L'utilisateur doit être authentifié grâce au token.
	 */
	@DeleteMapping(value="/deleteById")
	@ResponseBody
	public List<ProjetDto> deleteById(@RequestParam Long idProjet){
		if (authChecker.isUtilisateur() == null) throw new NotIdentifiedException();
		long idUtilisateur = authChecker.getUserIdFromToken();
		
		projetService.deleteById(idProjet);
		 return projetService.findAll(idUtilisateur);
	}
	
	
	/**
	 * Mettre à jour un projet d'un utilisateur donné.
	 * @param projetDto
	 * L'utilisateur doit être authentifié grâce au token.
	 */
	@PutMapping(value="/update")
	@ResponseBody
	public void update(@RequestBody ProjetDto projetDto) {
		projetService.update(projetDto);
	}
}
