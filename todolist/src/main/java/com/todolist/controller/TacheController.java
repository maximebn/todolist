package com.todolist.controller;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.todolist.dto.TacheDto;
import com.todolist.dto.TacheDtoMax;
import com.todolist.service.ITacheService;

/**
 * @author Dell
 *
 */
/**
 * @author Dell
 *
 */
/**
 * @author Dell
 *
 */
@RestController
@RequestMapping(value="/api/tache")
public class TacheController {
	@Autowired
	ITacheService tacheService;
	
	
	
	/** Permet de rentrer et sauvegarder une nouvelle tache
	 * @param tacheDto
	 * @return tacheDto
	 */
	@PostMapping
	public TacheDto save(@RequestBody TacheDto tacheDto) {	
		return tacheService.save(tacheDto);
	}
	
	
	/**Permet d'afficher la liste des taches pour une journée
	 * @param id
	 * @return ListTacheDto
	 * @throws ParseException
	 */
	@GetMapping(value="/todayList")
	public List<TacheDtoMax> getForToday(@RequestParam long id) throws ParseException{
			LocalDate today = LocalDate.now();
			return tacheService.findByDate(today, id);
	}
	
	/** Permet d'afficher une liste de taches pour aujourdh'hui et les 6 prochains jours
	 * @param id
	 * @return ListTacheDto
	 * @throws ParseException
	 */
	@GetMapping(value="/weekList")
	public List<TacheDtoMax> getForNextWeek(@RequestParam long id) throws ParseException{
			LocalDate today = LocalDate.now();
			return tacheService.findForWeek(today, id);
	}

	/** Permet d'effacer une tache
	 * @param idTache
	 */
	@DeleteMapping(value="/{idTache}")
 	public void deleteById(@PathVariable Long idTache) {
		return ;
		
	}
 		
	
	/** Permet d'afficher toutes les taches d'un utilisateur
	 * @param idUtilisateur
	 * @return ListTacheDto
	 */
	@GetMapping(value="/All/{idUtilisateur}")
	public List<TacheDto> findAll(@PathVariable Long idUtilisateur) {
		return tacheService.findAll(idUtilisateur);
	}
	
	
	/** Permet de modifier une tache
	 * @param tacheDto
	 */
	@PostMapping(value="/update")
	public void update(@RequestBody TacheDto tacheDto) {
		tacheService.update(tacheDto);
		return;
	}
}
