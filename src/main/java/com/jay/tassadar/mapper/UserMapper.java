package com.jay.tassadar.mapper;

import com.jay.tassadar.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    int signIn(User user);

}
