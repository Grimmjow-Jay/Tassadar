package com.jay.tassadar.cache;

import com.jay.tassadar.entity.User;
import com.jay.tassadar.exception.BusinessException;
import lombok.Data;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 存储登录成功的用户的token信息
 */
public class TokenCacheSingleton {

    // token有效期
    private static long duration;
    // 用ConcurrentHashMap，线程安全
    private static Map<String, TokenInfo> cache = new ConcurrentHashMap<>();

    // 私有化构造函数，单例
    private TokenCacheSingleton() {
        // 初始化的时候，设置超时时间参数
        duration = 10 * 60 * 1000L;
    }

    // 缓存user，并返回token
    public static String cache(User user, String ip) {
        // 如果不允许一个用户在多个地方登陆的话，这里就再处理一下
        String token = UUID.randomUUID().toString();
        cache.put(token, new TokenInfo(token, user, ip));
        return token;
    }

    // 校验token
    public static User check(String token, String ip) {
        TokenInfo tokenInfo = cache.get(token);
        BusinessException.assertTrue(tokenInfo == null, "Token无效");

        long now = System.currentTimeMillis();
        if (tokenInfo.updateTime + duration < now) { // 过期了，删掉cache里面的，返回过期了
            cache.remove(token);
            BusinessException.assertTrue(true, "Token过期了");
        }
        BusinessException.assertTrue(!tokenInfo.ip.equals(ip), "Token异常");

        // 还有其他的校验，也都可以写在这里

        // 经过上面的检验，都通过了的话，把更新时间更新到最新，然后返回user
        tokenInfo.updateTime = now;
        return tokenInfo.user;
    }

    @Data
    private static class TokenInfo {
        // token
        private String token;
        // token代表的用户
        private User user;
        // 创建时间，时间戳
        private long createTime;
        // 更新时间，时间戳
        private long updateTime;
        // 创建token时的ip地址
        private String ip;

        private TokenInfo(String token, User user, String ip) {
            this.token = token;
            this.user = user;
            this.createTime = System.currentTimeMillis();
            this.updateTime = this.createTime;
            this.ip = ip;
        }

    }

}
