package com.jay.tassadar.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class DynamicDataSourceConfig {

    @Bean
    public DynamicDataSource dynamicDataSource() {
        Map<Object, Object> dataSources = new HashMap<>();
        HikariDataSource dataSource0 = DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .username("root")
                .password("123456")
                .url("jdbc:mysql:///tassadar?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=UTC")
                .build();
        dataSources.put("database0", dataSource0);

        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setDefaultTargetDataSource(dataSource0);
        dynamicDataSource.setTargetDataSources(dataSources);
        return dynamicDataSource;
    }

}
