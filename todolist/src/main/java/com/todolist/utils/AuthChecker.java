package com.todolist.utils;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.stereotype.Component;

import com.todolist.persistence.entity.Utilisateur;

@Component
public class AuthChecker {
@Autowired private ConsumerTokenServices consumerTokenServices;
@Autowired private AuthorizationServerTokenServices authorizationServerTokenServices;



	public Utilisateur isUtilisateur() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		boolean isUser = SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"));
		if (principal != null && principal instanceof Utilisateur && isUser) {
			return (Utilisateur)principal;
		} else {
			return null;
		}
	}

		
	// Obtenir l'id à partir du token généré :
	public long getUserIdFromToken () {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Utilisateur user = (Utilisateur)principal;
		
		return user.getId();
	}
	
	public void revokeToken (Principal principal) {
		OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) principal;
	    OAuth2AccessToken accessToken = authorizationServerTokenServices.getAccessToken(oAuth2Authentication);
	    consumerTokenServices.revokeToken(accessToken.getValue());
	}
	
}
