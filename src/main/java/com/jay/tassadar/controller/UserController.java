package com.jay.tassadar.controller;

import com.jay.tassadar.config.DynamicDataSource;
import com.jay.tassadar.entity.User;
import com.jay.tassadar.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping("/signIn")
    public boolean signIn() {
//        String dataBase = "database0";
//        String dataBase = "database1";
        String dataBase = "database2";
        try {
            DynamicDataSource.setDataSourceType(dataBase);
            User user = new User();
            user.setUserName("jay");
            user.setPhone(150);
            return userService.signIn(user);
        } finally {
            DynamicDataSource.clearDataSourceType();
        }
    }
}
