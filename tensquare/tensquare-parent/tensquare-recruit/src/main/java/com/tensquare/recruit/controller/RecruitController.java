package com.tensquare.recruit.controller;

import com.tensquare.common.entity.ErrorCode;
import com.tensquare.common.entity.Result;
import com.tensquare.recruit.pojo.Recruit;
import com.tensquare.recruit.service.RecruitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recruit")
public class RecruitController {

    @Autowired
    private RecruitService recruitService;

    //添加招聘信息
    @PostMapping
    public Result add(@RequestBody Recruit recruit) {
        try {
            recruitService.add(recruit);
        } catch (Exception e) {
            return new Result(false, ErrorCode.ERROR, "新增招聘信息失败");
        }
        return new Result(true,ErrorCode.OK,"新增招聘信息成功");
    }

    //查询所有招聘信息
    @GetMapping
    public Result findAll() {
        List<Recruit> recruits = recruitService.findAll();
        if (null != recruits) {
            return new Result(true,ErrorCode.OK,"查询所有招聘信息成功",recruits);
        } else {
            return new Result(false, ErrorCode.ERROR, "查询所有招聘信息失败");
        }
    }

    //依据id查询招聘信息
    @GetMapping("/{recruitId}")
    public Result findById(@PathVariable String recruitId) {
        Recruit recruit = recruitService.findById(recruitId);
        if (null != recruit) {
            return new Result(true,ErrorCode.OK,"招聘信息查询成功",recruit);
        } else {
            return new Result(false, ErrorCode.ERROR, "新增招聘信息查询失败");
        }
    }

    //依据id修改招聘信息
    @PutMapping("/{recruitId}")
    public Result update(@PathVariable String recruitId, @RequestBody Recruit recruit) {
        try {
            recruitService.update(recruitId,recruit);
        } catch (Exception e) {
            return new Result(false, ErrorCode.ERROR, "修改招聘信息失败");
        }
        return new Result(true,ErrorCode.OK,"修改招聘信息成功");
    }

    //依据id删除招聘信息
    @DeleteMapping("/{recruitId}")
    public Result remove(@PathVariable String recruitId) {
        try {
            recruitService.remove(recruitId);
        } catch (Exception e) {
            return new Result(false, ErrorCode.ERROR, "删除招聘信息失败");
        }
        return new Result(true,ErrorCode.OK,"删除招聘信息成功");
    }

    //查询推荐职位
    @GetMapping("search/recommend")
    public Result searchIsCommend() {
        List<Recruit> recruits = recruitService.searchIsCommend();
        if (null != recruits) {
            return new Result(true,ErrorCode.OK,"查询推荐职位成功",recruits);
        } else {
            return new Result(false, ErrorCode.ERROR, "查询推荐职位失败");
        }
    }

    //查询热门职位
    @GetMapping("search/newlist")
    public Result searchNewList() {
        List<Recruit> recruits = recruitService.searchNewList();
        if (null != recruits) {
            return new Result(true,ErrorCode.OK,"查询最新职位成功",recruits);
        } else {
            return new Result(false, ErrorCode.ERROR, "查询最新职位失败");
        }
    }
}
