package com.tensquare.web.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 此网关过滤器用于解析请求头并继续向下传递
 */
@Component
public class WebFilter extends ZuulFilter {

    /**
     * 过滤器的执行时机
     * pre：代表在执行网关路由前被调用
     * route：在路由请求时候被调用
     * error：处理请求时发生错误时被调用
     * post：代表在执行 route（路由）和 error（处理请求发生错误是执行）
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
        return 0;
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
        System.out.println("执行前端zuul过滤器...");
        //得到当前请求上下文对象
        RequestContext context = RequestContext.getCurrentContext();
        //得到请求对象
        HttpServletRequest request = context.getRequest();
        //得到请求头
        String header = request.getHeader("Authorization");
        //如果请求头不为空，则向下传递（到网关后台）
        if (StringUtils.isNotBlank(header)) {
            context.addZuulRequestHeader("Authorization",header);
        }
        return null;
    }
}
