package com.todolist.service;

import java.util.List;

import com.todolist.dto.ProjetDtoC;
import com.todolist.dto.TacheDto;

public interface IProjetServiceC {

	ProjetDtoC save(ProjetDtoC projetDto, Long idUtilisateur);

	List<ProjetDtoC> findAll(Long idUtilisateur);

	List<TacheDto> findById(Long idProjet);

	List<ProjetDtoC> deleteById(Long idProjet);

}
