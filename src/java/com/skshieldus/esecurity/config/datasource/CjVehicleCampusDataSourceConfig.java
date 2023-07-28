package com.skshieldus.esecurity.config.datasource;

import com.skshieldus.esecurity.config.datasource.annotation.CjVehicleCampusMapper;
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
@MapperScan(basePackages = "com.skshieldus.esecurity.repository.entmanage.cjvehiclecampus", annotationClass = CjVehicleCampusMapper.class, sqlSessionFactoryRef = "cjVehicleCampusSessionFactory")
public class CjVehicleCampusDataSourceConfig {

    @Bean(name = "cjVehicleCampusDataSource")
    @ConfigurationProperties(prefix = "ifdb.datasource.cjvehiclecampus")
    public DataSource cjVehicleCampusDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "cjVehicleCampusSessionFactory")
    public SqlSessionFactory cjVehicleCampusSessionFactory(ApplicationContext applicationContext, MybatisProperties mybatisProperties) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(cjVehicleCampusDataSource());
        bean.setVfs(SpringBootVFS.class);
        bean.setTypeAliasesPackage("com.skshieldus.esecurity.model");
        bean.setMapperLocations(applicationContext.getResources("classpath*:persistence/mapper/entmanage/cjvehiclecampus/**/*.xml"));
        bean.setConfigLocation(applicationContext.getResource("classpath:persistence/mapper/mybatis-config.xml"));
        return bean.getObject();
    }

    @Bean(name = "cjVehicleCampusTransactionManager")
    public DataSourceTransactionManager cjVehicleCampusTransactionManager() {
        return new DataSourceTransactionManager(cjVehicleCampusDataSource());
    }

    @Bean(name = "cjVehicleCampusSessionTemplate")
    public SqlSessionTemplate cjVehicleCampusSessionTemplate(@Qualifier("cjVehicleCampusSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
