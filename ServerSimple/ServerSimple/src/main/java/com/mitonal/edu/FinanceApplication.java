package com.mitonal.edu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.poi.ss.usermodel.DataFormat;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.text.DateFormat;

//@EnableAdminServer
@EnableJpaAuditing
@EnableScheduling
@EnableAsync
@EnableCaching
@EnableAspectJAutoProxy
//@Configuration
@SpringBootApplication(exclude = { RedisAutoConfiguration.class })
public class FinanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinanceApplication.class, args);
	}

//	@Bean
//	public ObjectMapper objectMapper() {
//		ObjectMapper om=new ObjectMapper();
//		om.setDateFormat(DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM));
//		om.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
//		return om;
//	}

}
