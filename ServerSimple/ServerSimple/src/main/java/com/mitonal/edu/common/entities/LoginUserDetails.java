package com.mitonal.edu.common.entities;

import com.mitonal.edu.common.security.entites.ClientId;
import com.mitonal.edu.common.security.entites.Realm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * 认证用户描述
 */
@Data
public class LoginUserDetails extends DefaultUserDetails {

	private DefaultUserDetails info;

	private String access_token;
}
