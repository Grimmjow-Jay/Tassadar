package com.jay.tassadar.service.impl;

import com.jay.tassadar.entity.User;
import com.jay.tassadar.mapper.UserMapper;
import com.jay.tassadar.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    @Transactional
    public boolean signIn(User user) {
        User grimmjow = new User();
        System.out.println(grimmjow);
        int i1 = userMapper.signIn(user);
        return (i1 / (user.getUserId() - 5)) == 1;
    }
}
