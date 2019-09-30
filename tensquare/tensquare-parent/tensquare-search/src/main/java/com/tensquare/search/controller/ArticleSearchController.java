package com.tensquare.search.controller;

import com.tensquare.common.entity.ErrorCode;
import com.tensquare.common.entity.PageResult;
import com.tensquare.common.entity.Result;
import com.tensquare.search.pojo.Article;
import com.tensquare.search.service.ArticleSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleSearchController {

    @Autowired
    private ArticleSearchService searchService;

    //新增文章
    @PostMapping
    public Result addIndex(@RequestBody Article article) {
        try {
            searchService.add(article);
        } catch (Exception e) {
            return new Result(false,ErrorCode.ERROR,"向索引库新增一篇文章失败");
        }
        return new Result(true,ErrorCode.OK,"向索引库新增一片文章成功");
    }

    //搜索所有文章
    @GetMapping
    public Result searchAll() {
        List<Article> articles = searchService.searchAll();
        return new Result(true,ErrorCode.OK,"查询所有文章成功",articles);
    }

    //按标题或内容关键字搜索文章
    @GetMapping("/search/{keyword}/{page}/{size}")
    public Result searchByKeyword(@PathVariable String keyword, @PathVariable int page, @PathVariable int size) {
        PageResult<Article> articles = searchService.searchByKeyword(keyword, page, size);
        if (null != articles) {
            return new Result(true,ErrorCode.OK,"按关键字分页搜索文档成功",articles);
        } else {
            return new Result(false,ErrorCode.ERROR,"按关键字分页搜索文档失败");
        }
    }

}

