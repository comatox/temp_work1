package com.skshieldus.esecurity.config.datasource;

import com.skshieldus.esecurity.config.datasource.annotation.IdcardVisitMapper;
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
@MapperScan(basePackages = "com.skshieldus.esecurity.repository.entmanage.idcardvisit", annotationClass = IdcardVisitMapper.class, sqlSessionFactoryRef = "idcardVisitSessionFactory")
public class IdcardVisitDataSourceConfig {

    @Bean(name = "idcardVisitDataSource")
    @ConfigurationProperties(prefix = "ifdb.datasource.idcardvisit")
    public DataSource idcardVisitDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "idcardVisitSessionFactory")
    public SqlSessionFactory idcardVisitSessionFactory(ApplicationContext applicationContext, MybatisProperties mybatisProperties) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(idcardVisitDataSource());
        bean.setVfs(SpringBootVFS.class);
        bean.setTypeAliasesPackage("com.skshieldus.esecurity.model");
        bean.setMapperLocations(applicationContext.getResources("classpath*:persistence/mapper/entmanage/idcardvisit/**/*.xml"));
        bean.setConfigLocation(applicationContext.getResource("classpath:persistence/mapper/mybatis-config.xml"));
        return bean.getObject();
    }

    @Bean(name = "idcardVisitTransactionManager")
    public DataSourceTransactionManager idcardVisitTransactionManager() {
        return new DataSourceTransactionManager(idcardVisitDataSource());
    }

    @Bean(name = "idcardVisitSessionTemplate")
    public SqlSessionTemplate idcardVisitSessionTemplate(@Qualifier("idcardVisitSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
