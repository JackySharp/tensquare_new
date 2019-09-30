package com.tensquare.user.service;

import com.tensquare.common.utils.IdWorker;
import com.tensquare.user.dao.AdminDao;
import com.tensquare.user.pojo.Admin;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private IdWorker idWorker;

    //BCrypt密码加密器封装在spring-boot-starter-security依赖中，在启动类添加Bean
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private HttpServletRequest request;

    public void add(Admin admin) {
        //生成随机id
        admin.setId(String.valueOf(idWorker.nextId()));
        //使用BCrypt密码加密器给管理员密码
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        //添加新的招聘信息
        adminDao.save(admin);
    }

    public List<Admin> findAll() {
        return adminDao.findAll();
    }

    public Admin findById(String adminId) {
        return adminDao.findById(adminId).get();
    }

    public void update(String adminId, Admin admin) {
        admin.setId(adminId);
        adminDao.save(admin);
    }

    public void remove(String adminId) throws Exception {
        //从session获取认证主体
        Claims claims = (Claims) request.getAttribute("claims");
        if (null == claims) {
            throw new Exception("用管理员不存在");
        }
        //获取认证主体权限
        String role = claims.get("roles").toString();
        if (StringUtils.isNotEmpty(role) && "ROLE_ADMIN".equals(role)) {
            adminDao.deleteById(adminId);
        }
        throw new Exception("当前用户不是管理员");
    }

    public Admin login(Admin admin) throws Exception {
        //从数据库查询与数据库的管理员账号一致的管理员信息
        Admin one = adminDao.findById(admin.getId()).get();
        if (null == one) {
            throw new Exception("管理员账户不存在");
        }
        //比对登陆的管理员输入的密码和数据库中的管理员密码是否一致
        if (passwordEncoder.matches(admin.getPassword(),one.getPassword())) {
            return one;
        }
        throw new Exception("管理员密码错误");
    }
}
