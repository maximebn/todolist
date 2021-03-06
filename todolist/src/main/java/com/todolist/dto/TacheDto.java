package com.todolist.dto;

import java.time.LocalDate;

import com.todolist.persistence.entity.Tache;

public class TacheDto {
	
	private String titre;
	private LocalDate date;
	private String priorite;
	private String statut;
	private Long id;
	private ProjetDto projet;


	public TacheDto() {
		super();
	}

	public TacheDto(Tache tache, ProjetDto projetDto) {
		this.setId(tache.getId());
		this.setTitre(tache.getTitre());
		this.setDate(tache.getDate());
		this.setPriorite(tache.getPriorite());
		this.setStatut(tache.getStatut());
		this.setProjet(projetDto);
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getPriorite() {
		return priorite;
	}

	public void setPriorite(String priorite) {
		this.priorite = priorite;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProjetDto getProjet() {
		return projet;
	}

	public void setProjet(ProjetDto projet) {
		this.projet = projet;
	}

	
}
