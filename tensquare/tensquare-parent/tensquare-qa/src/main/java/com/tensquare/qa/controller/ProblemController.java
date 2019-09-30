package com.tensquare.qa.controller;

import com.tensquare.common.entity.ErrorCode;
import com.tensquare.common.entity.PageResult;
import com.tensquare.common.entity.Result;
import com.tensquare.qa.pojo.Problem;
import com.tensquare.qa.service.ProblemService;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/problem")
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    //添加问题
    @PostMapping
    public Result add(@RequestBody Problem problem) {
        try {
            problemService.add(problem);
        } catch (Exception e) {
            return new Result(false, ErrorCode.ERROR, "新增问题失败");
        }
        return new Result(true, ErrorCode.OK, "新增问题成功");
    }

    //查询所有问题列表
    @GetMapping
    public Result findAll() {
        List<Problem> problems = problemService.findAll();
        if (null != problems) {
            return new Result(true, ErrorCode.OK, "查询所有问题成功", problems);
        } else {
            return new Result(false, ErrorCode.ERROR, "查询所有问题失败");
        }
    }

    //依据id查询问题列表
    @GetMapping("/{problemId}")
    public Result findById(@PathVariable String problemId) {
        Problem problem = problemService.findById(problemId);
        if (null != problem) {
            return new Result(true, ErrorCode.OK, "问题列表查询成功", problem);
        } else {
            return new Result(false, ErrorCode.ERROR, "问题列表查询失败");
        }
    }

    //依据id修改问题
    @PutMapping("/{problemId}")
    public Result update(@PathVariable String problemId, @RequestBody Problem problem) {
        try {
            problemService.update(problemId, problem);
        } catch (Exception e) {
            return new Result(false, ErrorCode.ERROR, "修改问题失败");
        }
        return new Result(true, ErrorCode.OK, "修改问题成功");
    }

    //依据id删除问题
    @DeleteMapping("/{problemId}")
    public Result remove(@PathVariable String problemId) {
        try {
            problemService.remove(problemId);
        } catch (Exception e) {
            return new Result(false, ErrorCode.ERROR, "删除问题失败");
        }
        return new Result(true, ErrorCode.OK, "删除问题成功");
    }

    //查询最新问答列表
    @GetMapping("/newlist/{label}/{page}/{size}")
    public Result searchNewList(@PathVariable String label, @PathVariable int page, @PathVariable int size) {
        PageResult<Problem> problems = problemService.searchNewList(label, page, size);
        if (null != problems) {
            return new Result(true, ErrorCode.OK, "查询最新问答列表成功", problems);
        } else {
            return new Result(false, ErrorCode.ERROR, "查询最新问答列表失败");
        }
    }

    //查询热门问答列表
    @GetMapping("/hotlist/{label}/{page}/{size}")
    public Result searchHotList(@PathVariable String label, @PathVariable int page, @PathVariable int size) {
        PageResult<Problem> problems = problemService.searchHotList(label, page, size);
        if (null != problems) {
            return new Result(true, ErrorCode.OK, "查询热门问答列表成功", problems);
        } else {
            return new Result(false, ErrorCode.ERROR, "查询热门问答列表失败");
        }
    }

    //查询等待问答列表
    @GetMapping("/waitlist/{label}/{page}/{size}")
    public Result searchWaitList(@PathVariable String label, @PathVariable int page, @PathVariable int size) {
        PageResult<Problem> problems = problemService.searchWaitList(label, page, size);
        if (null != problems) {
            return new Result(true, ErrorCode.OK, "查询等待问答列表成功", problems);
        } else {
            return new Result(false, ErrorCode.ERROR, "查询等待问答列表失败");
        }
    }
}
