package com.tensquare.article.controller;

import com.tensquare.article.pojo.Comment;
import com.tensquare.article.service.CommentService;
import com.tensquare.common.entity.ErrorCode;
import com.tensquare.common.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    //新增评论
    @PostMapping
    public Result add(@RequestBody Comment comment) {
        try {
            commentService.add(comment);
        } catch (Exception e) {
            return new Result(false, ErrorCode.ERROR, "添加评论失败");
        }
        return new Result(true,ErrorCode.OK,"添加评论成功");
    }

    //查询所有评论
    @GetMapping
    public Result findAll() {
        List<Comment> comments = null;
        comments = commentService.findAll();
        if (null == comments) {
            return new Result(false, ErrorCode.ERROR, "查询评论失败");
        } else {
            return new Result(true, ErrorCode.OK, "查询评论成功", comments);
        }
    }

    //依据文章id查询评论列表
    @GetMapping("/article/{articleid}")
    public Result findByArticleId(@PathVariable String articleid) {
        List<Comment> comments = commentService.findByArticleId(articleid);
        if (null != comments) {
            return new Result(true,ErrorCode.OK,"查询评论列表成功",comments);
        } else {
            return new Result(false, ErrorCode.ERROR, "查询评论列表失败");
        }
    }

    //依据评论人id查询评论列表
    @GetMapping("/user/{userid}")
    public Result findByUserid(@PathVariable String userid) {
        List<Comment> comments = commentService.findByUserid(userid);
        if (null != comments) {
            return new Result(true,ErrorCode.OK,"查询评论列表成功",comments);
        } else {
            return new Result(false, ErrorCode.ERROR, "查询评论列表失败");
        }
    }

    //依据id删除评论
    @DeleteMapping("/{commentId}")
    public Result remove(@PathVariable String commentId) {
        try {
            commentService.remove(commentId);
        } catch (Exception e) {
            return new Result(false, ErrorCode.ERROR, "删除评论失败");
        }
        return new Result(true,ErrorCode.OK,"删除评论成功");
    }

}
