package com.todolist.service;

import java.util.List;

import com.todolist.dto.ProjetDtoC;

public interface IProjetServiceC {

	ProjetDtoC save(ProjetDtoC projetDto);

	List<ProjetDtoC> findAll(Long idUtilisateur);

}
