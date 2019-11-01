package com.jay.tassadar.config;

import com.jay.tassadar.entity.DataSource;
import com.jay.tassadar.mapper.DataSourceMapper;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

public class DynamicDataSource extends AbstractRoutingDataSource {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static Map<Object, Object> dataSources = new HashMap<>();

    @Resource
    private DataSourceMapper dataSourceMapper;

    public void setDataSourceType(String companyId){
        if ( !dataSources.containsKey(companyId) ){
            DataSource dataSource = dataSourceMapper.getDatasource(companyId);
            HikariDataSource hikariDataSource = DataSourceBuilder.create()
                    .type(HikariDataSource.class)
                    .driverClassName("com.mysql.cj.jdbc.Driver")
                    .username(dataSource.getUsername())
                    .password(dataSource.getPassword())
                    .url(dataSource.getUrl()).build();
            dataSources.put(dataSource.getName(), hikariDataSource);

            setTargetDataSources(dataSources);
            // 执行afterPropertiesSet方法，父类中加载数据源的方法会再执行一次
            afterPropertiesSet();
        }
        contextHolder.set(companyId);
    }

    public static void clearDataSourceType() {
        contextHolder.remove();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return contextHolder.get();
    }
}
