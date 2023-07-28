package com.skshieldus.esecurity.config.datasource;

import com.skshieldus.esecurity.config.datasource.annotation.CalderMapper;
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
@MapperScan(basePackages = "com.skshieldus.esecurity.repository.entmanage.calder", annotationClass = CalderMapper.class, sqlSessionFactoryRef = "calderSessionFactory")
public class CalderDataSourceConfig {

    @Bean(name = "calderDataSource")
    @ConfigurationProperties(prefix = "ifdb.datasource.calder")
    public DataSource calderDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "calderSessionFactory")
    public SqlSessionFactory calderSessionFactory(ApplicationContext applicationContext, MybatisProperties mybatisProperties) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(calderDataSource());
        bean.setVfs(SpringBootVFS.class);
        bean.setTypeAliasesPackage("com.skshieldus.esecurity.model");
        bean.setMapperLocations(applicationContext.getResources("classpath*:persistence/mapper/entmanage/calder/**/*.xml"));
        bean.setConfigLocation(applicationContext.getResource("classpath:persistence/mapper/mybatis-config.xml"));
        return bean.getObject();
    }

    @Bean(name = "calderTransactionManager")
    public DataSourceTransactionManager calderTransactionManager() {
        return new DataSourceTransactionManager(calderDataSource());
    }

    @Bean(name = "calderSessionTemplate")
    public SqlSessionTemplate calderSessionTemplate(@Qualifier("calderSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
