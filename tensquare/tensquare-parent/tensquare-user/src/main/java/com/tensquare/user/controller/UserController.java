package com.tensquare.user.controller;

import com.tensquare.common.entity.ErrorCode;
import com.tensquare.common.entity.Result;
import com.tensquare.common.utils.JwtUtil;
import com.tensquare.user.pojo.User;
import com.tensquare.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    //发送手机验证码
    @PostMapping("/sendsms/{mobile}")
    public Result sendsms(@PathVariable String mobile) {
        try {
            userService.sendSMS(mobile);
        } catch (Exception e) {
            return new Result(false, ErrorCode.ERROR, "短信发送失败,请稍后重试");
        }
        return new Result(true, ErrorCode.OK, "短信发送成功,请注意查收");
    }

    //用户注册
    @PostMapping("/register/{code}")
    public Result registry(@PathVariable String code, @RequestBody User user) {
        try {
            userService.registry(code, user);
        } catch (Exception e) {
            return new Result(false, ErrorCode.ERROR, e.getMessage());
        }
        return new Result(true, ErrorCode.OK, "用户注册成功");
    }

    //用户登录
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        //从数据库查询名称与登陆的用户名一致的用户信息
        User one = null;
        try {
            one = userService.login(user);
        } catch (Exception e) {
            return new Result(false, ErrorCode.ERROR, e.getMessage());
        }
        //生成token
        String token = jwtUtil.createJWT(one.getId(), user.getMobile(), "ROLE_USER");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("nickname", one.getNickname());
        paramMap.put("token", token);
        return new Result(true, ErrorCode.OK, "用户登录成功", paramMap);
    }


    //增加用户
    @PostMapping
    public Result add(@RequestBody User user) {
        try {
            userService.add(user);
        } catch (Exception e) {
            return new Result(false, ErrorCode.ERROR, "添加用户失败");
        }
        return new Result(true, ErrorCode.OK, "添加用户成功");
    }

    //查询所有用户
    @GetMapping
    public Result findAll() {
        List<User> users = userService.findAll();
        if (null != users) {
            return new Result(true, ErrorCode.OK, "查询所有用户成功", users);
        } else {
            return new Result(false, ErrorCode.ERROR, "查询所有用户失败");
        }
    }

    //依据id查询用户
    @GetMapping("/{userId}")
    public Result findById(@PathVariable String userId) {
        User user = userService.findById(userId);
        if (null != userId) {
            return new Result(true, ErrorCode.OK, "查询用户成功", user);
        } else {
            return new Result(false, ErrorCode.ERROR, "查询用户失败");
        }
    }

    //依据id修改用户
    @PutMapping("/{userId}")
    public Result update(@PathVariable String userId, @RequestBody User user) {
        try {
            userService.update(userId, user);
        } catch (Exception e) {
            return new Result(false, ErrorCode.ERROR, "修改用户失败");
        }
        return new Result(true, ErrorCode.OK, "修改用户成功");
    }

    //依据id删除用户
    @DeleteMapping("/{userId}")
    public Result remove(@PathVariable String userId) {
        try {
            userService.remove(userId);
        } catch (Exception e) {
            return new Result(false, ErrorCode.ERROR, e.getMessage());
        }
        return new Result(true, ErrorCode.OK, "删除用户成功");
    }

    //修改关注数
    @PutMapping("/incfollow/{userId}/{x}")
    public Result incFollows(@PathVariable String userId, @PathVariable int x) {
        //userService.setFollows(userId,x);
        try {
            userService.setFollows(userId,x);
        } catch (Exception e) {
            return new Result(false,ErrorCode.ERROR,"修改关注数失败");
        }
        return new Result(true,ErrorCode.OK,"修改关注数成功");
    }

    //修改粉丝数
    @PutMapping("/incfans/{userId}/{x}")
    public Result incFans(@PathVariable String userId, @PathVariable int x) {
        //userService.setFans(userId,x);
        try {
            userService.setFans(userId,x);
        } catch (Exception e) {
            return new Result(false,ErrorCode.ERROR,"修改粉丝数失败");
        }
        return new Result(true,ErrorCode.OK,"修改粉丝数成功");
    }

}