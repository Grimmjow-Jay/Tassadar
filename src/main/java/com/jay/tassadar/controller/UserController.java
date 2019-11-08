package com.jay.tassadar.controller;

import com.jay.tassadar.config.DynamicDataSource;
import com.jay.tassadar.entity.User;
import com.jay.tassadar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class UserController {

    @Resource
    private UserService userService;
    @Autowired
    private DynamicDataSource dataSource;

    @CrossOrigin("*")
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

    /**
     * 登录，返回token
     * @param user 用户
     * @return 返回token
     */
    @RequestMapping("/login")
    public String login(User user, HttpServletRequest request) {
        return userService.login(user,request.getRemoteAddr());
    }

    /**
     * 校验token是否有效
     * @param token token
     * @param address ip地址
     * @return 有效的话返回，token代表的详细信息
     */
    @RequestMapping("/check")
    public Map<String, Object> check(String token, String address) {
        return userService.check(token, address);
    }

    @RequestMapping("/checkTest")
    public String checkTest() {
        return "OK";
    }

}
