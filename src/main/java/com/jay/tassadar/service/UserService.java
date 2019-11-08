package com.jay.tassadar.service;

import com.jay.tassadar.entity.User;

import java.util.Map;

public interface UserService {

    boolean signIn(User user);

    String login(User user, String ip);

    Map<String, Object> check(String token, String address);
}
