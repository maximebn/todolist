package com.todolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todolist.dto.TacheDto;
import com.todolist.service.ITacheService;

@RestController
@RequestMapping(value="/api/tache")
public class tacheController {
	@Autowired
	ITacheService tacheService;
	
	@PostMapping
	public TacheDto save(TacheDto tacheDto) {
		tacheService.save(tacheDto);
		return tacheService.save(tacheDto);
	}
}
