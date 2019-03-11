package com.todolist.controller;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.todolist.dto.TacheDto;
import com.todolist.exception.NotIdentifiedException;
import com.todolist.service.ITacheService;
import com.todolist.utils.AuthChecker;

/**
 * @author Dell
 *
 */
@CrossOrigin
@RestController
@RequestMapping(value="/api/tache")
public class TacheController {
	
	@Autowired ITacheService tacheService;
	@Autowired private AuthChecker authChecker;
	
	
	/** Permet de rentrer et sauvegarder une nouvelle tache
	 * @param tacheDto
	 * @return tacheDto
	 */
	@PostMapping(value="/save")
	public TacheDto save(@RequestBody TacheDto tacheDto) {	
		return tacheService.save(tacheDto);
	}
	
	
	/**Permet d'afficher la liste des taches pour une journée
	 * @param id
	 * @return ListTacheDto
	 * @throws ParseException
	 */
	@GetMapping(value="/todayList")
	public List<TacheDto> getForToday() {
		if (authChecker.isUtilisateur() == null) throw new NotIdentifiedException();
		long idUtilisateur = authChecker.getUserIdFromToken();
		
		LocalDate today = LocalDate.now();
		return tacheService.findByDate(today, idUtilisateur);
	}
	
	/** Permet d'afficher une liste de taches pour aujourdh'hui et les 6 prochains jours
	 * @param id
	 * @return ListTacheDto
	 * @throws ParseException
	 */
	@GetMapping(value="/weekList")
	public List<TacheDto> getForNextWeek(){
		if (authChecker.isUtilisateur() == null) throw new NotIdentifiedException();
		long idUtilisateur = authChecker.getUserIdFromToken();
		
		LocalDate today = LocalDate.now();
		return tacheService.findForWeek(today, idUtilisateur);
	}

	/** Permet d'effacer une tache
	 * @param idTache
	 */
	@DeleteMapping(value="/deleteOne")
 	public void deleteById(@RequestParam Long idTache) {		
	}
 		
	
	/** Permet d'afficher toutes les taches d'un utilisateur
	 * @param idUtilisateur
	 * @return ListTacheDto
	 */
	@GetMapping(value="/findAll")
	public List<TacheDto> findAll() {
		if (authChecker.isUtilisateur() == null) throw new NotIdentifiedException();
		long idUtilisateur = authChecker.getUserIdFromToken();
		
		return tacheService.findAll(idUtilisateur);
	}
	
	
	/** Permet de modifier une tache
	 * @param tacheDto
	 */
	@PutMapping(value="/update")
	public void update(@RequestBody TacheDto tacheDto) {
		tacheService.update(tacheDto);
	}
}
