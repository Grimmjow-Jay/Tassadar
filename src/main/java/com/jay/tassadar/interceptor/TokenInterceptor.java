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
 * <pre>
 * 身份认证流程：
 *   登录：用户传的用户名、密码参数无误后，生成一个token，并把这个token对应的用户信息保存起来，
 *        可以保存在内存里，也可以保存到数据库或者redis等等。然后将这个token返回给用户。
 *   认证：用户每次访问都需要携带token，服务器拦截所有需要验证身份的请求，获取到参数获取请求头
 *        里面的token，然后对该token进行校验，判断该token有效，如果有效，则延长token的有效期，
 *        并且将用户信息存到方便获取的地方，如HttpServletRequest里面。拦截器放行，进行业务处理。
 * </pre>
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
