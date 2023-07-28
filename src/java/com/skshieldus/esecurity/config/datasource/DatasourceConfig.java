package com.skshieldus.esecurity.config.datasource;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.skshieldus.esecurity.repository", annotationClass = Mapper.class, sqlSessionFactoryRef = "defaultSessionFactory")
public class DatasourceConfig {

    @Primary
    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "defaultSessionFactory")
    public SqlSessionFactory defaultSessionFactory(ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource());
        bean.setVfs(SpringBootVFS.class);
        bean.setTypeAliasesPackage("com.skshieldus.esecurity.model");
        bean.setMapperLocations(applicationContext.getResources("classpath*:persistence/mapper/*/*.xml"));
        bean.setConfigLocation(applicationContext.getResource("classpath:persistence/mapper/mybatis-config.xml"));
        return bean.getObject();
    }

    @Primary
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Primary
    @Bean(name = "defaultSessionTemplate")
    public SqlSessionTemplate defaultSessionTemplate(@Qualifier("defaultSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
