package com.todolist.service;

import java.util.List;

import com.todolist.dto.ProjetDtoC;
import com.todolist.dto.TacheDto;

public interface IProjetServiceC {

	/**
	 * Créer un projet
	 * @param projetDto
	 * @param idUtilisateur
	 * @return ProjetDto
	 */
	ProjetDtoC save(ProjetDtoC projetDto, Long idUtilisateur);

	/**
	 * Lister l'ensemble des projets d'un utilisateur donné.
	 * @param idUtilisateur
	 * @return List ProjetDto
	 */
	List<ProjetDtoC> findAll(Long idUtilisateur);

	/**
	 * Lister les tâches d'un projet d'un utilisateur donnée.
	 * @param idProjet
	 * @return List TacheDto
	 */
	List<TacheDto> findById(Long idProjet);

	/**
	 * Supprimer un projet d'un utilisateur donné.
	 * @param idProjet
	 * @return List ProjetDto mise à jour
	 */
	void deleteById(Long idProjet);

	/**
	 * Mettre à jour un projet d'un utilisateur donné.
	 * @param projetDto
	 */
	void update(ProjetDtoC projetDto);

}
