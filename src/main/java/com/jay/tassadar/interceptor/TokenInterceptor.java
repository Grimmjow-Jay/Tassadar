package com.jay.tassadar.interceptor;

import com.jay.tassadar.cache.TokenCacheSingleton;
import com.jay.tassadar.entity.User;
import com.jay.tassadar.exception.BusinessException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截所有访问，进行token验证
 */
@Component
public class TokenInterceptor implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 不拦截的url
        String[] excludes = {"/", "/check", "/login/**", "/error"};
        HandlerInterceptor interceptor = new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

                // 获取token的方式可以从请求头里获取，或者请求参数，或者其他
                String token = request.getParameter("token");
                if (token == null) {
                    token = request.getHeader("token");
                }
                BusinessException.assertTrue(token == null, "没有token");

                /*
                 * 在这个项目里面可以用下面的方式，直接判断token是否有效。
                 * 如果是在其他项目里，没有TokenCacheSingleton，可以发送http请求访问本项目来验证token
                 */

                // 进行校验token
                User user = TokenCacheSingleton.check(token, request.getRemoteAddr());
                // 校验成功了，把用户信息设置进request里面，获取保存到其他地方，最好是ThreadLocal里面
                request.setAttribute("user", user);
                return true;
            }
        };
        registry.addInterceptor(interceptor).addPathPatterns("/**").excludePathPatterns(excludes);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
}
