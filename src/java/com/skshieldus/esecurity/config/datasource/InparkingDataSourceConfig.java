package com.skshieldus.esecurity.config.datasource;

import com.skshieldus.esecurity.config.datasource.annotation.InparkingMapper;
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
@MapperScan(basePackages = "com.skshieldus.esecurity.repository.entmanage.inparking", annotationClass = InparkingMapper.class, sqlSessionFactoryRef = "inparkingSessionFactory")
public class InparkingDataSourceConfig {

    @Bean(name = "inparkingDataSource")
    @ConfigurationProperties(prefix = "ifdb.datasource.inparking")
    public DataSource inparkingDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "inparkingSessionFactory")
    public SqlSessionFactory inparkingSessionFactory(ApplicationContext applicationContext, MybatisProperties mybatisProperties) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(inparkingDataSource());
        bean.setVfs(SpringBootVFS.class);
        bean.setTypeAliasesPackage("com.skshieldus.esecurity.model");
        bean.setMapperLocations(applicationContext.getResources("classpath*:persistence/mapper/entmanage/inparking/**/*.xml"));
        bean.setConfigLocation(applicationContext.getResource("classpath:persistence/mapper/mybatis-config.xml"));
        return bean.getObject();
    }

    @Bean(name = "inparkingTransactionManager")
    public DataSourceTransactionManager inparkingTransactionManager() {
        return new DataSourceTransactionManager(inparkingDataSource());
    }

    @Bean(name = "inparkingSessionTemplate")
    public SqlSessionTemplate inparkingSessionTemplate(@Qualifier("inparkingSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
