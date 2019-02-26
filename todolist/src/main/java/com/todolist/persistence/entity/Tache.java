package com.todolist.persistence.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="tache")
public class Tache {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column (name = "id", unique = true, nullable = false)
	private Long id;
	@Column (name = "Titre_tache", length=100, nullable=false)
	private String titre;
	@Column (name = "Date_echeance", length=100, nullable=false)
	private Date date;
	@Column (name = "Priorite", length=100, nullable=false)
	private String priorite;
	@Column (name = "Statut", length=100, nullable=false)
	private String statut;
	
	@ManyToOne
	@JoinColumn(name="IDUtilisateur", referencedColumnName="id", nullable=false)
	private Utilisateur utilisateur;
	
	@ManyToOne
	@JoinColumn(name="IDProjet", referencedColumnName="id", nullable=true)
	private Projet projet;

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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
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

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public Projet getProjet() {
		return projet;
	}

	public void setProjet(Projet projet) {
		this.projet = projet;
	}
	
}