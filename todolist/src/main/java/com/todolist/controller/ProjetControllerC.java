package com.todolist.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todolist.dto.ProjetDtoC;
import com.todolist.service.IProjetServiceC;

@RestController
@RequestMapping(value="/api/projet")
public class ProjetControllerC {
	
	@Autowired
	IProjetServiceC projetService;
	
	
	@PostMapping(value="/{idUtilisateur}")
	public ProjetDtoC save(@RequestBody ProjetDtoC projetDto, @PathVariable Long idUtilisateur) {
		return projetService.save(projetDto, idUtilisateur);
	}
	
	@GetMapping(value="/{idUtilisateur}")
	public List<ProjetDtoC> findAll(@PathVariable Long idUtilisateur){
		return projetService.findAll(idUtilisateur);
	}

}
