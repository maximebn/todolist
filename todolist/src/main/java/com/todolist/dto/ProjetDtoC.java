package com.todolist.dto;

import com.todolist.persistence.entity.Projet;

public class ProjetDtoC {

	
	private Long id;
	
	private String titre;
	private Long idUtilisateur;
	
	public ProjetDtoC() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ProjetDtoC(Projet projet) {
		this.setId(projet.getId());
		this.setTitre(projet.getTitre());
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public Long getIdUtilisateur() {
		return idUtilisateur;
	}

	public void setIdUtilisateur(Long idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}
	
	
}
