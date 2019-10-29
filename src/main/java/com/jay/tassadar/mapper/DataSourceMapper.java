package com.jay.tassadar.mapper;

import com.jay.tassadar.entity.DataSource;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DataSourceMapper {

    List<DataSource> getAll();

}
