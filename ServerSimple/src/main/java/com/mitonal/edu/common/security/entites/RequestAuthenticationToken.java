package com.mitonal.edu.common.security.entites;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class RequestAuthenticationToken extends UsernamePasswordAuthenticationToken {

	private String userId;

	private Realm realm;

	private ClientId clientId;

	private String secret;

	public RequestAuthenticationToken(Object principal, Object credentials, final String userId, Realm realm,
			ClientId clientId, String secret) {
		super(principal, credentials);
		this.userId = userId;
		this.realm = realm;
		this.clientId = clientId;
		this.secret = secret;
	}

}
