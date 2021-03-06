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

@Entity
@Table(name="utilisateur")
public class Utilisateur {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column (name = "id", unique = true, nullable = false)
	private Long id;
	@Column (name = "Prénom", length=100, nullable=false)
	private String prenom;
	@Column (name = "Mail", length=100, nullable=false)
	private String mail;
	@Column (name = "PassWord", length=100, nullable=false)
	private String password;
	
	@OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="IDUtilisateur", referencedColumnName="id", nullable=false)
    private List<Projet> projets= new ArrayList<Projet>();
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<Projet> getProjets() {
		return projets;
	}
	public void setProjets(List<Projet>  projets) {
		this.projets =  projets;
	}
	
	
}
