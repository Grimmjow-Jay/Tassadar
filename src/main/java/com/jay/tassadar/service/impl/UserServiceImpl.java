package com.jay.tassadar.service.impl;

import com.jay.tassadar.exception.BusinessException;
import com.jay.tassadar.cache.TokenCacheSingleton;
import com.jay.tassadar.entity.User;
import com.jay.tassadar.mapper.UserMapper;
import com.jay.tassadar.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

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

    @Override
    public String login(User user, String ip) {
        // 登录成功了，存储用户的信息到缓存里面，返回token给前端
        BusinessException.assertTrue(!"jay".equals(user.getUserName()), "登录失败");
        return TokenCacheSingleton.cache(user, ip);
    }

    @Override
    public Map<String, Object> check(String token, String address) {
        User user = TokenCacheSingleton.check(token, address);
        Map<String, Object> result = new HashMap<>();
        result.put("user", user);
        result.put("other", "other");
        return result;
    }
}
