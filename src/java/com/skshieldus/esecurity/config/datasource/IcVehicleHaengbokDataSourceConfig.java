package com.skshieldus.esecurity.config.datasource;

import com.skshieldus.esecurity.config.datasource.annotation.IcVehicleHaengbokMapper;
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
@MapperScan(basePackages = "com.skshieldus.esecurity.repository.entmanage.icvehiclehaengbok", annotationClass = IcVehicleHaengbokMapper.class, sqlSessionFactoryRef = "icVehicleHaengbokSessionFactory")
public class IcVehicleHaengbokDataSourceConfig {

    @Bean(name = "icVehicleHaengbokDataSource")
    @ConfigurationProperties(prefix = "ifdb.datasource.icvehiclehaengbok")
    public DataSource icVehicleHaengbokDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "icVehicleHaengbokSessionFactory")
    public SqlSessionFactory icVehicleHaengbokSessionFactory(ApplicationContext applicationContext, MybatisProperties mybatisProperties) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(icVehicleHaengbokDataSource());
        bean.setVfs(SpringBootVFS.class);
        bean.setTypeAliasesPackage("com.skshieldus.esecurity.model");
        bean.setMapperLocations(applicationContext.getResources("classpath*:persistence/mapper/entmanage/icvehiclehaengbok/**/*.xml"));
        bean.setConfigLocation(applicationContext.getResource("classpath:persistence/mapper/mybatis-config.xml"));
        return bean.getObject();
    }

    @Bean(name = "icVehicleHaengbokTransactionManager")
    public DataSourceTransactionManager icVehicleHaengbokTransactionManager() {
        return new DataSourceTransactionManager(icVehicleHaengbokDataSource());
    }

    @Bean(name = "icVehicleHaengbokSessionTemplate")
    public SqlSessionTemplate icVehicleHaengbokSessionTemplate(@Qualifier("icVehicleHaengbokSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
