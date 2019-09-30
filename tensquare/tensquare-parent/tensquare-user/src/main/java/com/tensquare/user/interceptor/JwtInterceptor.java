package com.tensquare.user.interceptor;

import com.tensquare.common.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String header = request.getHeader("Authorization");
        //如果请求头中包含"Authorization"字段，且"Authorization"字段的值以"Bearer "开头
        if (StringUtils.isNotBlank(header) && header.startsWith("Bearer ")) {
            //如果是，从第7个字符开始截取
            String token = header.substring(7);
            //解析token
            Claims claims = jwtUtil.parseJWT(token);
            //判断token中的认证主体是否为空，如果不为空，则将主题信息设置为HttpRequest对象的属性
            if (null != claims) {
                request.setAttribute("claims", claims);
            }
        }
        return true;//返回true放行
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
