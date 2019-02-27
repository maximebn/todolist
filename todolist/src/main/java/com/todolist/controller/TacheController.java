package com.todolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todolist.dto.TacheDto;
import com.todolist.service.ITacheService;

@RestController
@RequestMapping(value="/api/tache")
public class TacheController {
	@Autowired
	ITacheService tacheService;
	
	@PostMapping
	public TacheDto save(@RequestBody TacheDto tacheDto) {	
		return tacheService.save(tacheDto);
	}
}
