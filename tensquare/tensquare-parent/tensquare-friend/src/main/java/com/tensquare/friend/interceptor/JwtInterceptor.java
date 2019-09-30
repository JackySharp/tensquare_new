package com.tensquare.friend.interceptor;

import com.tensquare.common.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取请求头的Authorization属性
        String header = request.getHeader("Authorization");
        //判断请求头的Authorization属性中是否包含"Bearer "字符串
        if (StringUtils.isNotBlank(header) && header.startsWith("Bearer ")) {
            //从第7位开始截取Authorization字段的值，得到token
            String token = header.substring(7);
            //解析token，得到认证主体
            Claims claims = jwtUtil.parseJWT(token);
            //将认证主体放入HttpServletRequest类对象
            request.setAttribute("claims",claims);
        }
        return true;//返回true，代表执行完毕
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
