package com.tensquare.friend.client.impl;

import com.tensquare.common.entity.ErrorCode;
import com.tensquare.common.entity.Result;
import com.tensquare.friend.client.UserClient;
import org.springframework.stereotype.Component;

/**
 * Hystrix熔断回调函数
 */
@Component
public class UserClientImpl implements UserClient {
    @Override
    public Result incFollows(String userId, int x) {
        return new Result(false, ErrorCode.REMOTE_ERROR,"tensquare-user服务远程调用异常!");
    }

    @Override
    public Result incFans(String userId, int x) {
        return new Result(false, ErrorCode.REMOTE_ERROR,"tensquare-user服务远程调用异常!");
    }

    @Override
    public Result findById(String userId) {
        return new Result(false, ErrorCode.REMOTE_ERROR,"tensquare-user服务远程调用异常!");
    }
}
