package com.mitonal.edu.test.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.integration.support.locks.ExpirableLockRegistry;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LockConfigurationManual {

	@Resource
	private ExpirableLockRegistry expirableLockRegistry;

	@Test
	public void testJdbclock() {
		// Lock lock = expirableLockRegistry.obtain("hello");
		// lock.lock();
		// lock.unlock();
	}

}
