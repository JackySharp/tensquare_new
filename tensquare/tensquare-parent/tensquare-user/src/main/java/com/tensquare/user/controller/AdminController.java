package com.tensquare.user.controller;

import com.tensquare.common.entity.ErrorCode;
import com.tensquare.common.entity.Result;
import com.tensquare.common.utils.JwtUtil;
import com.tensquare.user.pojo.Admin;
import com.tensquare.user.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private JwtUtil jwtUtil;

    //管理员登录
    @PostMapping("/login")
    public Result login(@RequestBody Admin admin) {
        Admin one = null;
        try {
            one = adminService.login(admin);
        } catch (Exception e) {
            return new Result(false,ErrorCode.ERROR,e.getMessage());
        }
        String token = jwtUtil.createJWT(one.getId(), one.getLoginname(), "ROLE_ADMIN");
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("administrator",admin.getLoginname());
        paramMap.put("token",token);
        return new Result(true,ErrorCode.OK,"管理员登录成功",paramMap);
    }

    //增加管理员
    @PostMapping
    public Result add(@RequestBody Admin admin) {
        try {
            adminService.add(admin);
        } catch (Exception e) {
            return new Result(false, ErrorCode.ERROR, "添加管理员失败");
        }
        return new Result(true,ErrorCode.OK,"添加管理员成功");
    }

    //查询所有管理员
    @GetMapping
    public Result findAll() {
        List<Admin> admins = adminService.findAll();
        if (null != admins) {
            return new Result(true,ErrorCode.OK,"查询所有管理员成功",admins);
        } else {
            return new Result(false, ErrorCode.ERROR, "查询所有管理员失败");
        }
    }

    //依据id查询管理员
    @GetMapping("/{adminId}")
    public Result findById(@PathVariable String adminId) {
        Admin admin = adminService.findById(adminId);
        if (null != admin) {
            return new Result(true,ErrorCode.OK,"管理员查询成功",admin);
        } else {
            return new Result(false, ErrorCode.ERROR, "管理员查询失败");
        }
    }

    //依据id修改管理员
    @PutMapping("/{adminId}")
    public Result update(@PathVariable String adminId, @RequestBody Admin admin) {
        try {
            adminService.update(adminId,admin);
        } catch (Exception e) {
            return new Result(false, ErrorCode.ERROR, "修改管理员失败");
        }
        return new Result(true,ErrorCode.OK,"修改管理员成功");
    }

    //依据id删除管理员
    @DeleteMapping("/{adminId}")
    public Result remove(@PathVariable String adminId) {
        try {
            adminService.remove(adminId);
        } catch (Exception e) {
            return new Result(false, ErrorCode.ERROR, e.getMessage());
        }
        return new Result(true,ErrorCode.OK,"删除管理员成功");
    }
}
