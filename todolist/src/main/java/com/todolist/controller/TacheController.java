package com.todolist.controller;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.todolist.dto.TacheDto;
import com.todolist.dto.TacheDtoMax;
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
	
	@GetMapping(value="/todayList")
	public List<TacheDtoMax> getForToday(@RequestParam long id) throws ParseException{
			LocalDate today = LocalDate.now();
			return tacheService.findByDate(today, id);
	}
	
	@GetMapping(value="/weekList")
	public List<TacheDtoMax> getForNextWeek(@RequestParam long id) throws ParseException{
			LocalDate today = LocalDate.now();
			return tacheService.findForWeek(today, id);
	}

}
