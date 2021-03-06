package com.example.demo.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhangzongbo
 * @date 19-3-4 下午3:17
 */

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Cookie[] cookies = request.getCookies();
        StringBuilder stringBuilder = new StringBuilder();
        for (Cookie cookie : cookies){
//            log.info("name: {}, value: {}", cookie.getName(), cookie.getValue());
            String cookieStr = String.format("name: %s, value: %s; ",cookie.getName(), cookie.getValue());
            stringBuilder.append(cookieStr);
        }
        log.info("cookies: {}",stringBuilder);
        log.info("url: {}",request.getRequestURL());
        log.info("param: {}",request.getQueryString());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
