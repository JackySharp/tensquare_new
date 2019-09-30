package com.tensquare.qa.controller;

import com.tensquare.common.entity.ErrorCode;
import com.tensquare.common.entity.Result;
import com.tensquare.qa.pojo.Reply;
import com.tensquare.qa.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reply")
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    //添加回复
    @PostMapping
    public Result add(@RequestBody Reply reply) {
        try {
            replyService.add(reply);
        } catch (Exception e) {
            return new Result(false, ErrorCode.ERROR, "新增回复失败");
        }
        return new Result(true, ErrorCode.OK, "新增回复成功");
    }

    //查询所有回复列表
    @GetMapping
    public Result findAll() {
        List<Reply> replies = replyService.findAll();
        if (null != replies) {
            return new Result(true, ErrorCode.OK, "查询所有回复成功", replies);
        } else {
            return new Result(false, ErrorCode.ERROR, "查询所有回复失败");
        }
    }

    //依据id查询回复
    @GetMapping("/{replyId}")
    public Result findById(@PathVariable String replyId) {
        Reply reply = replyService.findById(replyId);
        if (null != reply) {
            return new Result(true, ErrorCode.OK, "回复列表查询成功", reply);
        } else {
            return new Result(false, ErrorCode.ERROR, "回复列表查询失败");
        }
    }

    //依据id修改回复
    @PutMapping("/{replyId}")
    public Result update(@PathVariable String replyId, @RequestBody Reply reply) {
        try {
            replyService.update(replyId, reply);
        } catch (Exception e) {
            return new Result(false, ErrorCode.ERROR, "修改回复失败");
        }
        return new Result(true, ErrorCode.OK, "修改回复成功");
    }

    //依据id删除回复
    @DeleteMapping("/{replyId}")
    public Result remove(@PathVariable String replyId) {
        try {
            replyService.remove(replyId);
        } catch (Exception e) {
            return new Result(false, ErrorCode.ERROR, "删除回复失败");
        }
        return new Result(true, ErrorCode.OK, "删除回复成功");
    }

    //依据问题id查询回复列表
    @GetMapping("/problem/{problemId}")
    public Result searchNewList(@PathVariable String problemId) {
        List<Reply> replies = replyService.searchByProblemId(problemId);
        if (null != replies) {
            return new Result(true, ErrorCode.OK, "查询回复列表成功", replies);
        } else {
            return new Result(false, ErrorCode.ERROR, "查询回复列表失败");
        }
    }

    //回答问题
    @PostMapping("/save")
    public Result searchHotList(@RequestBody Reply reply) {
        try {
            replyService.add(reply);
        } catch (Exception e) {
            return new Result(false, ErrorCode.ERROR, "回答问题失败");
        }
        return new Result(true, ErrorCode.OK, "回答问题成功");
    }

    //我的回复列表
    //@GetMapping("/list/{page}/{size}")
    //public Result myReplyList(@PathVariable int page, @PathVariable int size) {
    //    PageResult<Reply> replies = replyService.myReplyList(page,size);
    //    if (null != replies) {
    //        return new Result(true,ErrorCode.OK,"查询我的回复列表成功",replies);
    //    } else {
    //        return new Result(false, ErrorCode.ERROR, "查询我的回复列表失败");
    //    }
    //}

}
