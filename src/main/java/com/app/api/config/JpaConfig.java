package com.app.api.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.app.api.constants.Constant;

import jakarta.persistence.EntityManagerFactory;


@Configuration
@EnableJpaRepositories(
    basePackages = "com.app.api.repositories",
    entityManagerFactoryRef = "EntityManagerFactory", 
    transactionManagerRef = "JpaTransactionManager"
)
public class JpaConfig {
  
	@Primary
    @Bean(name = "appDataSource")
    public DataSource dataSource() {
        return DataSourceBuilder.create()
            .url("jdbc:mysql://"+Constant.DB_URI+":3306/"+Constant.DB_NAME+"?characterEncoding=UTF-8")
            .username(Constant.DB_USER)
            .password(Constant.DB_PASSWORD)
            .driverClassName("com.mysql.cj.jdbc.Driver")
            .build();
    }

    @Bean(name = "EntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
      JpaVendorAdapter jpaVendorAdapter
    ) {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource());
		
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		
		em.setPackagesToScan("com.app.api.entities");
		em.setJpaVendorAdapter(jpaVendorAdapter);
		return em;
    }

    @Bean(name = "JpaTransactionManager")
    public PlatformTransactionManager transactionManager(
      	@Qualifier("EntityManagerFactory") EntityManagerFactory entityManagerFactory
    ) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
