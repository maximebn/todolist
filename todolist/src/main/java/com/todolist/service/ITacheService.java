package com.todolist.service;

import java.time.LocalDate;
import java.util.List;

import com.todolist.dto.TacheDto;

public interface ITacheService {

	TacheDto save(TacheDto tacheDto);
	List<TacheDto> findByDate(LocalDate date, long id);
	List<TacheDto> triTacheByDate(List<TacheDto> list);
	List<TacheDto> findForWeek(LocalDate today, long id);
	void deleteById(Long idTache);
	List<TacheDto> findAll(Long idUtilisateur);
	void update(TacheDto tacheDto);
	List<TacheDto> findDone(LocalDate date, long idUtilisateur);
	List<TacheDto> findDoneForWeek(LocalDate startDate, long idUtilisateur);	

}
