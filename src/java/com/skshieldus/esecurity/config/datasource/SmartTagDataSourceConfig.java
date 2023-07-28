package com.skshieldus.esecurity.config.datasource;

import com.skshieldus.esecurity.config.datasource.annotation.SmartTagMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.skshieldus.esecurity.repository.entmanage.smartTag", annotationClass = SmartTagMapper.class, sqlSessionFactoryRef = "smartTagSessionFactory")
public class SmartTagDataSourceConfig {

    @Bean(name = "smartTagDataSource")
    @ConfigurationProperties(prefix = "ifdb.datasource.smarttag")
    public DataSource esecuritysiDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "smartTagSessionFactory")
    public SqlSessionFactory esecuritysiSessionFactory(ApplicationContext applicationContext, MybatisProperties mybatisProperties) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(esecuritysiDataSource());
        bean.setVfs(SpringBootVFS.class);
        bean.setTypeAliasesPackage("com.skshieldus.esecurity.model");
        bean.setMapperLocations(applicationContext.getResources("classpath*:persistence/mapper/entmanage/smartTag/**/*.xml"));
        bean.setConfigLocation(applicationContext.getResource("classpath:persistence/mapper/mybatis-config.xml"));
        return bean.getObject();
    }

    @Bean(name = "smartTagTransactionManager")
    public DataSourceTransactionManager esecuritysiTransactionManager() {
        return new DataSourceTransactionManager(esecuritysiDataSource());
    }

    @Bean(name = "smartTagSessionTemplate")
    public SqlSessionTemplate esecuritysiSessionTemplate(@Qualifier("smartTagSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
