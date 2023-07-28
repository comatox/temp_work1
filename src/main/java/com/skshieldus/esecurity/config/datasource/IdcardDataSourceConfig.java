package com.skshieldus.esecurity.config.datasource;

import com.skshieldus.esecurity.config.datasource.annotation.IdcardMapper;
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
@MapperScan(basePackages = "com.skshieldus.esecurity.repository.entmanage.idcard", annotationClass = IdcardMapper.class, sqlSessionFactoryRef = "idcardSessionFactory")
public class IdcardDataSourceConfig {

    @Bean(name = "idcardDataSource")
    @ConfigurationProperties(prefix = "ifdb.datasource.idcard")
    public DataSource idcardDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "idcardSessionFactory")
    public SqlSessionFactory idcardSessionFactory(ApplicationContext applicationContext, MybatisProperties mybatisProperties) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(idcardDataSource());
        bean.setVfs(SpringBootVFS.class);
        bean.setTypeAliasesPackage("com.skshieldus.esecurity.model");
        bean.setMapperLocations(applicationContext.getResources("classpath*:persistence/mapper/entmanage/idcard/**/*.xml"));
        bean.setConfigLocation(applicationContext.getResource("classpath:persistence/mapper/mybatis-config.xml"));
        return bean.getObject();
    }

    @Bean(name = "idcardTransactionManager")
    public DataSourceTransactionManager idcardTransactionManager() {
        return new DataSourceTransactionManager(idcardDataSource());
    }

    @Bean(name = "idcardSessionTemplate")
    public SqlSessionTemplate idcardSessionTemplate(@Qualifier("idcardSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
