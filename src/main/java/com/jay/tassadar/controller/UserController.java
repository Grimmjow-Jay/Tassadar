package com.jay.tassadar.controller;

import com.jay.tassadar.config.DynamicDataSource;
import com.jay.tassadar.entity.User;
import com.jay.tassadar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserController {

    @Resource
    private UserService userService;
    @Autowired
    private DynamicDataSource dataSource;

    @RequestMapping("/signIn")
    public boolean signIn() {
        String companyId = "companyId";
        try {
            dataSource.setDataSourceType(companyId);
            User user = new User();
            user.setUserName("jay");
            user.setPhone(150);
            return userService.signIn(user);
        } finally {
            DynamicDataSource.clearDataSourceType();
        }
    }
}
