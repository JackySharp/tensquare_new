package com.tensquare.user.service;

import com.aliyuncs.http.HttpRequest;
import com.tensquare.common.utils.IdWorker;
import com.tensquare.common.utils.JwtUtil;
import com.tensquare.user.dao.UserDao;
import com.tensquare.user.pojo.User;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Transactional
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    //BCrypt密码加密器封装在spring-boot-starter-security依赖中，在启动类添加Bean
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private HttpServletRequest request;

    public void add(User user) {
        //生成随机id
        user.setId(String.valueOf(idWorker.nextId()));
        //添加新的招聘信息
        userDao.save(user);
    }

    public List<User> findAll() {
        return userDao.findAll();
    }

    public User findById(String userId) {
        return userDao.findById(userId).get();
    }

    public void update(String userId, User user) {
        user.setId(userId);
        userDao.save(user);
    }

    public void remove(String userId) throws Exception {
        //从session中获取认证主体
        Claims claims = (Claims) request.getAttribute("claims");
        if (null == claims) {
            throw new RuntimeException("无访问权限,请重新登录");
        }
        //从认证主体获取用户的权限
        String role = claims.get("roles").toString();
        //如果当前用户拥有管理员权限，则可以删除用户
        if (StringUtils.isNotEmpty(role) && "ROLE_ADMIN".equals(role)) {
            userDao.deleteById(userId);
        } else {
            throw new RuntimeException("抱歉,您无权删除用户");
        }
    }

    //传递发送验证码短信消息
    public void sendSMS(String mobile) {
        //生成随机6位验证码
        String validCode = new Random().nextInt(900000) + 100000 + "";
        //将验证码存到缓存
        redisTemplate.opsForValue().set("phone_" + mobile,validCode,1,TimeUnit.MINUTES);
        //封装验证码和手机号
        Map<String,String> map = new HashMap<>();
        map.put("mobile",mobile);
        map.put("validCode",validCode);
        //传递验证码和手机号给消息中间件
        rabbitTemplate.convertAndSend("","sms",map);
    }

    public void registry(String code, User user) throws Exception {
        //获得用户手机号
        String mobile = user.getMobile();
        //从pong缓存中取出验证码
        String validCode = (String) redisTemplate.opsForValue().get("phone_" + mobile);
        //如果验证码不存在，说明用户没有在1分钟内输入有效验证码，而验证码自动过期
        if (null == validCode) {
            throw new Exception("验证码失效,请重新获取验证码");
        } else if (!code.equals(validCode)) {
            //如果用户输入的验证码与缓存中的验证码不匹配，则验证码输入错误
            throw new Exception("验证码错误,请重新输入");
        } else {
            //使用Spring Security自带的BCrypt密码加密器对用户密码进行加密
            String password = passwordEncoder.encode(user.getPassword());
            user.setPassword(password);
            user.setId(idWorker.nextId() + "");
            //向数据库中添加用户数据
            userDao.save(user);
        }
    }

    public User login(User user) throws Exception {
        //从数据库查询名称与登陆的用户名一致的用户信息
        User one = userDao.findByMobile(user.getMobile());
        if (null == one) {
            throw new Exception("用户不存在");
        } else {
            //使用密码加密器验证登录用户输入的密码与数据库中查询到的用户的密码是否一致
            if (passwordEncoder.matches(user.getPassword(), one.getPassword())) {
                return one;
            } else {
                throw new Exception("密码输入错误");
            }
        }
    }

    public void setFollows(String userid, int n) {
        userDao.incFollows(userid,n);
    }

    public void setFans(String userid, int n) {
        userDao.incFans(userid,n);
    }
}
