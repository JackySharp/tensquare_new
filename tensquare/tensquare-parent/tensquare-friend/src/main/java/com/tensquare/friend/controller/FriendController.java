package com.tensquare.friend.controller;

import com.tensquare.common.entity.ErrorCode;
import com.tensquare.common.entity.Result;
import com.tensquare.friend.client.UserClient;
import com.tensquare.friend.service.FriendService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @Autowired
    private UserClient userClient;

    @Autowired
    private HttpServletRequest request;

    //添加好友或拉黑某人
    @PutMapping("like/{friendid}/{type}")
    public Result addOrNot(@PathVariable String friendid, @PathVariable String type) {
        Claims claims = (Claims) request.getAttribute("claims");
        if (null != claims && "ROLE_USER".equals(claims.get("roles").toString())) {
            try {
                friendService.addOrNot(claims.getId(), friendid, type);
                if ("1".equals(type)) {
                    //自己关注数加1
                    userClient.incFollows(claims.getId(), 1);
                    //对方粉丝数加1
                    userClient.incFans(friendid, 1);
                    return new Result(true, ErrorCode.OK, "添加好友成功");
                } else if ("2".equals(type)) {
                    //自己关注数减1
                    userClient.incFollows(claims.getId(), -1);
                    //对方粉丝数减1
                    userClient.incFans(friendid, -1);
                    return new Result(true, ErrorCode.OK, "拉黑成功");
                }
            } catch (Exception e) {
                return new Result(false, ErrorCode.ERROR, e.getMessage());
            }
        }
        return new Result(false, ErrorCode.ACCESS_ERROR, "操作权限不足,请重新登录");
    }

    //删除好友
    @DeleteMapping("/{friendid}")
    public Result delete(@PathVariable String friendid) {
        Claims claims = (Claims) request.getAttribute("claims");
        if (null != claims && "ROLE_USER".equals(claims.get("roles").toString())) {
            try {
                friendService.delete(claims.getId(), friendid);
                //自己关注数减1
                userClient.incFollows(claims.getId(), -1);
                //对方关注数减1
                userClient.incFans(friendid, -1);
                return new Result(true, ErrorCode.OK, "删除好友成功");
            } catch (Exception e) {
                return new Result(false, ErrorCode.ERROR, "删除好友失败");
            }
        }
        //如果当前登录用户不是用户身份，则不允许删除好友，管理员也不能删除
        return new Result(false, ErrorCode.ACCESS_ERROR, "操作权限不足,请重新登录");
    }

    //测试Hystrix熔断功能
    @GetMapping
    public Result test() {
        return userClient.findById("1");
    }
}
