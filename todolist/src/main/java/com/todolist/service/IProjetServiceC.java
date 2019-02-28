package com.todolist.service;

import java.util.List;

import com.todolist.dto.ProjetDtoC;

public interface IProjetServiceC {

	ProjetDtoC save(ProjetDtoC projetDto, Long idUtilisateur);

	List<ProjetDtoC> findAll(Long idUtilisateur);

}
