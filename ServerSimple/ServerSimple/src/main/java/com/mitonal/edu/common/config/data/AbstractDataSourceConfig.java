package com.mitonal.edu.common.config.data;

import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * 抽象jpa数据源配置器
 */
public abstract class AbstractDataSourceConfig {

	// @Autowired
	// @Qualifier("secondaryDataSource")
	// protected DataSource japDataSource;

	protected abstract DataSource createDataSource();// {}

	@Resource
	protected Properties jpaProperties;

	// @Bean(name = "entityManagerSecondary")
	public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
		return entityManagerFactorySecondary(builder).getObject().createEntityManager();
	}

	// @Bean(name = "entityManagerFactorySecondary")
	public LocalContainerEntityManagerFactoryBean entityManagerFactorySecondary(EntityManagerFactoryBuilder builder) {
		LocalContainerEntityManagerFactoryBean entityManagerFactory = builder.dataSource(createDataSource())
				.packages("cn.ntshare.laboratory.domain.slave") // 设置实体类所在位置
				.persistenceUnit("secondaryPersistenceUnit").build();
		entityManagerFactory.setJpaProperties(jpaProperties);
		return entityManagerFactory;
	}

	// @Bean(name = "transactionManagerSecondary")
	protected PlatformTransactionManager transactionManagerSecondary(EntityManagerFactoryBuilder builder) {
		return new JpaTransactionManager(entityManagerFactorySecondary(builder).getObject());
	}

}