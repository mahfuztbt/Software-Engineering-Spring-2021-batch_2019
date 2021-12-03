package com.mitonal.edu.common.security.config;

import com.mitonal.edu.common.security.entites.PasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

/**
 * 密码编码器配置，
 */
@Configuration
public class PasswordEncoderConfig {

	@Bean
	public org.springframework.security.crypto.password.PasswordEncoder passwordEncoder() {

		String idForEncode = PasswordEncoder.MD5.getCode();
		Map<String, org.springframework.security.crypto.password.PasswordEncoder> encoders = new HashMap<>();
		encoders.put(PasswordEncoder.BCRYPT.getCode(), new BCryptPasswordEncoder());
		encoders.put(PasswordEncoder.PBKDF2.getCode(), new Pbkdf2PasswordEncoder());
		encoders.put(PasswordEncoder.SCRYPT.getCode(), new SCryptPasswordEncoder());
		encoders.put(PasswordEncoder.SHA256.getCode(), new StandardPasswordEncoder());
		encoders.put(PasswordEncoder.MD5.getCode(), new MessageDigestPasswordEncoder(PasswordEncoder.MD5.getCode()));
		encoders.put(PasswordEncoder.NOOP.getCode(), NoOpPasswordEncoder.getInstance());

		var passwordEncoder = new DelegatingPasswordEncoder(idForEncode, encoders);
		// return PasswordEncoderFactories.createDelegatingPasswordEncoder();
		return passwordEncoder;
	}

}
