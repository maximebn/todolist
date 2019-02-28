package com.todolist.dto;

import com.todolist.persistence.entity.Utilisateur;

public class UtilisateurDto {
	
	private String prenom;
	private String mail;
	private String password;
	
	public UtilisateurDto() {
		super();
	}
	
	public UtilisateurDto(Utilisateur user) {
		this.setPrenom(user.getPrenom());
		this.setMail(user.getMail());
		this.setPassword(user.getPassword());
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
}
