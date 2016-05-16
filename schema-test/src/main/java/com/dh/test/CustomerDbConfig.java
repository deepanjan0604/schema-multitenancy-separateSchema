package com.dh.test;


import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
@EnableJpaRepositories(
		 	entityManagerFactoryRef = "customerEntityManager",
		    transactionManagerRef = "customerTransactionManager",
		    basePackages = {"com.dh.test"})
public class CustomerDbConfig {
	
	@Autowired
    JpaVendorAdapter jpaVendorAdapter;
	
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource());
		em.setPackagesToScan(new String[] {"com.dh.test"});
		em.setJpaVendorAdapter(jpaVendorAdapter);
		em.setJpaProperties(additionalJpaProperties());
		em.setPersistenceUnitName("customer");
		return em;
	}
	
	Properties additionalJpaProperties(){
		Properties properties = new Properties();
		properties.setProperty("hibernate.ddl-auto", "update");
		properties.setProperty("jdbc.dialect", "org.hibernate.dialect.MySQLDialect");
		properties.setProperty("hibernate.show-sql", "true");
		return properties;
	}
	
	@Bean
	public DataSource dataSource(){
		return DataSourceBuilder.create()
				.url("jdbc:mysql://localhost:3306/customer")
				.driverClassName("com.mysql.jdbc.Driver")
				.username("root")
				.password("root")
				.build();
	}	
	
	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory customerEntityManager){
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(customerEntityManager);
		return transactionManager;
	}
}