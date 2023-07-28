package com.skshieldus.esecurity.config.datasource;

import com.skshieldus.esecurity.config.datasource.annotation.IdcardSiMapper;
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
@MapperScan(basePackages = "com.skshieldus.esecurity.repository.entmanage.idcardsi", annotationClass = IdcardSiMapper.class, sqlSessionFactoryRef = "idcardSiSessionFactory")
public class IdcardSiDataSourceConfig {

    @Bean(name = "idcardSiDataSource")
    @ConfigurationProperties(prefix = "ifdb.datasource.idcardsi")
    public DataSource idcardSiDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "idcardSiSessionFactory")
    public SqlSessionFactory idcardSiSessionFactory(ApplicationContext applicationContext, MybatisProperties mybatisProperties) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(idcardSiDataSource());
        bean.setVfs(SpringBootVFS.class);
        bean.setTypeAliasesPackage("com.skshieldus.esecurity.model");
        bean.setMapperLocations(applicationContext.getResources("classpath*:persistence/mapper/entmanage/idcardsi/**/*.xml"));
        bean.setConfigLocation(applicationContext.getResource("classpath:persistence/mapper/mybatis-config.xml"));
        return bean.getObject();
    }

    @Bean(name = "idcardSiTransactionManager")
    public DataSourceTransactionManager idcardSiTransactionManager() {
        return new DataSourceTransactionManager(idcardSiDataSource());
    }

    @Bean(name = "idcardSiSessionTemplate")
    public SqlSessionTemplate idcardSiSessionTemplate(@Qualifier("idcardSiSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
