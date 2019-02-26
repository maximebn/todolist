package com.todolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	
	@PostMapping
	public ProjetDtoC save(@RequestBody ProjetDtoC projetDto) {
		return projetService.save(projetDto);
	}

}
