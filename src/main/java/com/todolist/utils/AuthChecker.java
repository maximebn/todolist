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

import com.todolist.persistence.entity.User;

@Component
public class AuthChecker {
@Autowired private ConsumerTokenServices consumerTokenServices;
@Autowired private AuthorizationServerTokenServices authorizationServerTokenServices;


	public User isAnUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		boolean isUser = SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"));
		if (principal != null && principal instanceof User && isUser) {
			return (User)principal;
		} else {
			return null;
		}
	}

		
	// How to get id from generated token
	public long getUserIdFromToken () {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = (User)principal;
		
		return user.getId();
	}
	
	// How to reclaim and revoke token
	public void revokeToken (Principal principal) {
		OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) principal;
	    OAuth2AccessToken accessToken = authorizationServerTokenServices.getAccessToken(oAuth2Authentication);
	    consumerTokenServices.revokeToken(accessToken.getValue());
	}
	
}
