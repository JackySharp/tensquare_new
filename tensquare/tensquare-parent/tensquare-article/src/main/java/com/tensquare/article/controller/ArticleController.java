package com.tensquare.article.controller;

import com.tensquare.article.pojo.Article;
import com.tensquare.article.service.ArticleService;
import com.tensquare.common.entity.ErrorCode;
import com.tensquare.common.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    //添加文章
    @PostMapping
    public Result add(@RequestBody Article article) {
        try {
            articleService.add(article);
        } catch (Exception e) {
            return new Result(false, ErrorCode.ERROR, "添加文章失败");
        }
        return new Result(true,ErrorCode.OK,"添加文章成功");
    }

    //查询所有文章
    @GetMapping
    public Result findAll() {
        List<Article> articles = articleService.findAll();
        if (null != articles) {
            return new Result(true,ErrorCode.OK,"查询所有文章成功",articles);
        } else {
            return new Result(false, ErrorCode.ERROR, "查询所有文章失败");
        }
    }

    //依据id查询文章信息
    @GetMapping("/{articleId}")
    public Result findById(@PathVariable String articleId) {
        Article article = articleService.findById(articleId);
        if (null != article) {
            return new Result(true,ErrorCode.OK,"文章查询成功",article);
        } else {
            return new Result(false, ErrorCode.ERROR, "文章查询失败");
        }
    }

    //依据id修改招聘信息
    @PutMapping("/{articleId}")
    public Result update(@PathVariable String articleId, @RequestBody Article article) {
        try {
            articleService.update(articleId,article);
        } catch (Exception e) {
            return new Result(false, ErrorCode.ERROR, "修改文章失败");
        }
        return new Result(true,ErrorCode.OK,"修改文章成功");
    }

    //依据id删除招聘信息
    @DeleteMapping("/{articleId}")
    public Result remove(@PathVariable String articleId) {
        try {
            articleService.remove(articleId);
        } catch (Exception e) {
            return new Result(false, ErrorCode.ERROR, "删除招聘信息失败");
        }
        return new Result(true,ErrorCode.OK,"删除招聘信息成功");
    }

    //点赞
    @PutMapping("/thumbup/{articleId}")
    public Result thumbup(@PathVariable String articleId) {
        try {
            articleService.thumbup(articleId);
        } catch (Exception e) {
            return new Result(false, ErrorCode.ERROR, "点赞文章失败");
        }
        return new Result(true,ErrorCode.OK,"点赞文章成功");
    }

    //文章审核
    @PutMapping("/examine/{articleId}")
    public Result examine(@PathVariable String articleId) {
        try {
            articleService.examine(articleId);
        } catch (Exception e) {
            return new Result(false, ErrorCode.ERROR, "文章审核1失败");
        }
        return new Result(true,ErrorCode.OK,"文章审核成功");
    }

}
