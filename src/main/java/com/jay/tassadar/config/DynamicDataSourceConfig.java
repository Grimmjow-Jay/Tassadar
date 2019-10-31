package com.jay.tassadar.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties(DataSourceProperties.class)
public class DynamicDataSourceConfig {

    @Bean
    public DynamicDataSource dynamicDataSource(DataSourceProperties dataSourceProperties) {
        Map<Object, Object> dataSources = new HashMap<>();
        HikariDataSource dataSource0 = DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .driverClassName(dataSourceProperties.getDriverClassName())
                .username(dataSourceProperties.getUsername())
                .password(dataSourceProperties.getPassword())
                .url(dataSourceProperties.getUrl())
                .build();
        dataSources.put("database0", dataSource0);

        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setDefaultTargetDataSource(dataSource0);
        dynamicDataSource.setTargetDataSources(dataSources);
        return dynamicDataSource;
    }

}
