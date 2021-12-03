package com.mitonal.edu.test.utils;

import com.mitonal.edu.common.security.utils.JwtUtils;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;
import java.util.Map;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JwtUtilsTest {

	@Resource
	private JwtUtils jwtUtils;

	@Test
	public void parseToken() {

		// Map<String, Object> map = jwtUtils.parseToken(
		// "eyJhbGciOiJIUzI1NiJ9.eyJjbGllbnRJZCI6IkJST1dTRVIiLCJyYW5kTnVtIjoiZjFiNTQ2ZjMtMmVkOS00ZGM2LTkyYmQtZTUyMWQ4MGE4N2FmIiwicmVhbG0iOiJQTEFURk9STSIsInVzZXJJZCI6IjEiLCJ1c2VybmFtZSI6ImFkbWluIn0.NjDVdA0dJwN28wyG9Te-NNU0ZUp61xYNCts090HxxQA");
		// Assertions.assertThat(map).isNotNull();
		int a = 1;
	}

}
