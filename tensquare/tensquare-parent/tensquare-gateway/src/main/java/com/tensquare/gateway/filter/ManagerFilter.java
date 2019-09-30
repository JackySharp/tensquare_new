package com.tensquare.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.tensquare.common.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 此网关过滤器用于验证用户身份
 */
@Component
public class ManagerFilter extends ZuulFilter {

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 过滤器的执行时机
     * pre：代表在执行网关路由前被调用
     * route：在路由请求时候被调用
     * error：处理请求时发生错误时被调用
     * post：代表在执行 route（路由）和 error（处理请求发生错误时执行）
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤器的执行顺序，返回值越小越先执行
     * @return
     */
    @Override
    public int filterOrder() {
        return 1;
    }

    /**
     * 是否执行此过滤器
     * true：代表会执行
     * false：代表不会执行
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 执行过滤器的具体业务
     * 该方法的返回值，不管是任何值都会继续向下执行
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        System.out.println("执行后台zuul过滤器...");
        //获得请求上下文对象
        RequestContext context = RequestContext.getCurrentContext();
        //获取请求对象
        HttpServletRequest request = context.getRequest();
        //因为当我们发送请求到网关时是两次请求，此方法（OPTION）需要放行
        if ("OPTION".equals(request.getMethod())) {
            return null;
        }
        //如果是登录请求或注册请求，需要放行
        if ("/user/admin/login".equals(request.getRequestURI()) || "/user/user/login".equals(request.getRequestURI()) || "/user/user/registry".equals(request.getRequestURI())) {
            return null;
        }
        //获得请求头
        String header = request.getHeader("Authorization");
        if (StringUtils.isNotBlank(header) && header.startsWith("Bearer ")) {
            //获得token
            String token = header.substring(7);
            //获得认证主体
            Claims claims = jwtUtil.parseJWT(token);
            if (null != claims) {
                //获得认证主体权限
                String role = claims.get("roles").toString();
                //必须是管理员权限才能放行
                if (StringUtils.isNotEmpty(role) && "ROLE_ADMIN".equals(role)) {
                    //将自己的请求头添加到网关的请求头
                    context.addZuulRequestHeader("Authorization",header);
                    return null;
                }
            }
        }
        //阻止向下传递请求
        context.setSendZuulResponse(false);
        context.setResponseStatusCode(403);//403一般代表请求权限不足
        context.setResponseBody("无权访问");
        context.getResponse().setContentType("text/html;charset=utf-8");
        return null;
    }
}
