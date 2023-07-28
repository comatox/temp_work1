package com.skshieldus.esecurity.config.datasource;

import com.skshieldus.esecurity.config.datasource.annotation.IcVstcarGothamMapper;
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
@MapperScan(basePackages = "com.skshieldus.esecurity.repository.entmanage.icvstcargotham", annotationClass = IcVstcarGothamMapper.class, sqlSessionFactoryRef = "icVstcarGothamSessionFactory")
public class IcVstcarGothamDataSourceConfig {

    @Bean(name = "icVstcarGothamDataSource")
    @ConfigurationProperties(prefix = "ifdb.datasource.icvstcargotham")
    public DataSource icVstcarGothamDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "icVstcarGothamSessionFactory")
    public SqlSessionFactory icVstcarGothamSessionFactory(ApplicationContext applicationContext, MybatisProperties mybatisProperties) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(icVstcarGothamDataSource());
        bean.setVfs(SpringBootVFS.class);
        bean.setTypeAliasesPackage("com.skshieldus.esecurity.model");
        bean.setMapperLocations(applicationContext.getResources("classpath*:persistence/mapper/entmanage/icvstcargotham/**/*.xml"));
        bean.setConfigLocation(applicationContext.getResource("classpath:persistence/mapper/mybatis-config.xml"));
        return bean.getObject();
    }

    @Bean(name = "icVstcarGothamTransactionManager")
    public DataSourceTransactionManager icVstcarGothamTransactionManager() {
        return new DataSourceTransactionManager(icVstcarGothamDataSource());
    }

    @Bean(name = "icVstcarGothamSessionTemplate")
    public SqlSessionTemplate icVstcarGothamSessionTemplate(@Qualifier("icVstcarGothamSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
