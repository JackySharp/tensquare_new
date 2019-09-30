package com.tensquare.article.service;

import com.tensquare.article.dao.ArticleDao;
import com.tensquare.article.pojo.Article;
import com.tensquare.common.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Transactional
@Service
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private RedisTemplate redisTemplate;

    public void add(Article article) {
        //生成随机id
        article.setId(String.valueOf(idWorker.nextId()));
        //添加新的招聘信息
        articleDao.save(article);
    }

    public List<Article> findAll() {
        return  articleDao.findAll();
    }

    //public Article findById(String articleId) {
    //    return articleDao.findById(articleId).get();
    //}

    public void update(String articleId, Article article) {
        article.setId(articleId);
        //先删除缓存
        redisTemplate.delete("article_" + articleId);
        //在更新数据库
        articleDao.save(article);

    }

    public void remove(String articleId) {
        //删除数据库的同时删除缓存
        redisTemplate.delete("article_" + articleId);
        articleDao.deleteById(articleId);
    }

    //给文章点赞
    public void thumbup(String articleId) {
        articleDao.thumbup(articleId);
    }

    //依据id查询用户并放到缓存中
    public Article findById(String articleId) {
        //在缓存中查询当前用户点赞过的文章id列表
        Article article = (Article) redisTemplate.opsForValue().get("article_" + articleId);
        if (null == article) {
            article = articleDao.findById(articleId).get();
            //更新缓存并设置key的过期时间为10分钟
            redisTemplate.opsForValue().set("article_" + articleId,article,10,TimeUnit.MINUTES);
        }
        return article;
    }

    public void examine(String articleId) {
        articleDao.examine(articleId);
    }
}
