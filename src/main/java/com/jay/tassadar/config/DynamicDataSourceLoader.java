package com.jay.tassadar.config;

import com.jay.tassadar.entity.DataSource;
import com.jay.tassadar.mapper.DataSourceMapper;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DynamicDataSourceLoader implements ApplicationRunner {

    @Autowired
    private DynamicDataSource dynamicDataSource;

    @Resource
    private DataSourceMapper dataSourceMapper;

    @Override
    public void run(ApplicationArguments args) {
        List<DataSource> dataSourceList = dataSourceMapper.getAll();
        Map<Object, Object> dataSources = new HashMap<>();
        for (DataSource dataSource : dataSourceList) {
            HikariDataSource hikariDataSource = DataSourceBuilder.create()
                    .type(HikariDataSource.class)
                    .driverClassName("com.mysql.cj.jdbc.Driver")
                    .username(dataSource.getUsername())
                    .password(dataSource.getPassword())
                    .url(dataSource.getUrl()).build();
            dataSources.put(dataSource.getName(), hikariDataSource);
        }
        dynamicDataSource.setTargetDataSources(dataSources);
        // 执行afterPropertiesSet方法，父类中加载数据源的方法会再执行一次
        dynamicDataSource.afterPropertiesSet();
    }

}
