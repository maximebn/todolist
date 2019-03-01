package com.todolist.persistence.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 
 * @author Marielle
 * @description Entité projet
 * possède les attributs
 * id: identifiant unique auto-généré, 
 * titre : le titre du projet,
 * taches: liste de Tache liées à ce projet.
 *  @see Tache
 */

@Entity
@Table(name="projet")
public class Projet {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column (name = "id", unique = true, nullable = false)
	private Long id;
	@Column (name = "Titre_projet", length=100, nullable=false)
	private String titre;
	
	@OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="IDProjet", referencedColumnName="id", nullable=false)
    private List<Tache> taches= new ArrayList<Tache>();
	

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

	public List<Tache> getTaches() {
		return taches;
	}

	public void setTaches(List<Tache> taches) {
		this.taches = taches;
	}

	
	
}
