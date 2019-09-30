package com.tensquare.recruit.controller;

import com.tensquare.common.entity.ErrorCode;
import com.tensquare.common.entity.Result;
import com.tensquare.recruit.pojo.Enterprise;
import com.tensquare.recruit.service.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enterprise")
public class EnterpriseController {

    @Autowired
    private EnterpriseService enterpriseService;

    //添加企业
    @PostMapping
    public Result add(@RequestBody Enterprise enterprise) {
        try {
            enterpriseService.add(enterprise);
        } catch (Exception e) {
            return new Result(false, ErrorCode.ERROR, "新增企业失败");
        }
        return new Result(true,ErrorCode.OK,"新增企业成功");
    }

    //查询所有企业
    @GetMapping
    public Result findAll() {
        List<Enterprise> enterprises = enterpriseService.findAll();
        if (null != enterprises) {
            return new Result(true,ErrorCode.OK,"查询所有企业成功",enterprises);
        } else {
            return new Result(false, ErrorCode.ERROR, "查询所有企业失败");
        }
    }

    //依据id查询企业信息
    @GetMapping("/{enterpriseId}")
    public Result findById(@PathVariable String enterpriseId) {
        Enterprise enterprise = enterpriseService.findById(enterpriseId);
        if (null != enterprise) {
            return new Result(true,ErrorCode.OK,"招聘信息查询成功",enterprise);
        } else {
            return new Result(false, ErrorCode.ERROR, "新增招聘信息查询失败");
        }
    }

    //依据id修改企业信息
    @PutMapping("/{enterpriseId}")
    public Result update(@PathVariable String enterpriseId, @RequestBody Enterprise enterprise) {
        try {
            enterpriseService.update(enterpriseId,enterprise);
        } catch (Exception e) {
            return new Result(false, ErrorCode.ERROR, "修改企业失败");
        }
        return new Result(true,ErrorCode.OK,"修改企业成功");
    }

    //依据id删除企业
    @DeleteMapping("/{enterpriseId}")
    public Result remove(@PathVariable String enterpriseId) {
        try {
            enterpriseService.remove(enterpriseId);
        } catch (Exception e) {
            return new Result(false, ErrorCode.ERROR, "删除企业失败");
        }
        return new Result(true,ErrorCode.OK,"删除企业成功");
    }

    //查询热门企业
    @GetMapping("/search/hotlist")
    public Result searchIsHot() {
        List<Enterprise> enterprises = enterpriseService.searchIsHot();
        if (null != enterprises) {
            return new Result(true,ErrorCode.OK,"查询企业成功",enterprises);
        } else {
            return new Result(false, ErrorCode.ERROR, "查询企业失败");
        }
    }

}
