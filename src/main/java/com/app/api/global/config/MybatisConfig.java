package com.app.api.global.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan(
    basePackages = "com.app.api.global.mappers",
	sqlSessionFactoryRef = "MybatisSqlSessionFactory"
)
public class MybatisConfig {

    @Bean(name = "MybatisSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(
		@Qualifier("appDataSource") DataSource dataSource
	) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setMapperLocations(
			new PathMatchingResourcePatternResolver()
				.getResources("classpath*:mappers/database/*.xml")
		);
        return factoryBean.getObject();
    }

    @Bean(name = "MybatisSqlTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("MybatisSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name = "MybatisTransactionManager")
    public PlatformTransactionManager transactionManager(
		@Qualifier("appDataSource") DataSource dataSource
	) {
        return new DataSourceTransactionManager(dataSource);
    }
}
