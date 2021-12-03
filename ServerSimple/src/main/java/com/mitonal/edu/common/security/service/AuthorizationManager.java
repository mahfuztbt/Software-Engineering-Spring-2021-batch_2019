package com.mitonal.edu.common.security.service;

import com.mitonal.edu.common.entities.DefaultUserDetails;

public interface AuthorizationManager {

	void login(DefaultUserDetails user);

	void logout(DefaultUserDetails user);

	boolean validate(DefaultUserDetails user);

}
