package com.mitonal.edu.common.entities;

import com.mitonal.edu.common.security.entites.ClientId;
import com.mitonal.edu.common.security.entites.Realm;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * 认证用户描述
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DefaultUserDetails implements UserDetails, Cloneable {

	private String id;

	private String username;

	private String name;

	private String password;

	private Realm realm;

	private ClientId clientId;

	private String secret;

	private String token;

	@Builder.Default
	private boolean enabled = true;

	@Builder.Default
	private boolean credentialsNonExpired = true;

	@Builder.Default
	private boolean accountNonLocked = true;

	@Builder.Default
	private boolean accountNonExpired = true;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.EMPTY_LIST;
	}

//	@Override
//	public boolean isAccountNonExpired() {
//		return true;
//	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}
