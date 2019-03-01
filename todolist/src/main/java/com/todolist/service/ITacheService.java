package com.todolist.service;

import java.time.LocalDate;
import java.util.List;

import com.todolist.dto.TacheDto;
import com.todolist.dto.TacheDtoMax;

public interface ITacheService {

	TacheDto save(TacheDto tacheDto);
	List<TacheDtoMax> findByDate(LocalDate date, long id);
	List<TacheDtoMax> triTacheByDate(List<TacheDtoMax> list);
	List<TacheDtoMax> findForWeek(LocalDate today, long id);
	void deleteById(Long idTache);
	List<TacheDto> findAll(Long idUtilisateur);
	

}
