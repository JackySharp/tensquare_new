package com.tensquare.article.service;

import com.tensquare.article.dao.CommentDao;
import com.tensquare.article.pojo.Comment;
import com.tensquare.common.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private IdWorker idWorker;

    public void add(Comment comment) {
        //生成随机id
        comment.set_id(String.valueOf(idWorker.nextId()));
        //添加新的招聘信息
        commentDao.save(comment);
    }

    public List<Comment> findByUserid(String userid) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userid").is(userid));
        return mongoTemplate.find(query,Comment.class,"comment");
    }

    public List<Comment> findByArticleId(String articleid) {
        Query query = new Query();
        query.addCriteria(Criteria.where("articleid").is(articleid));
        return mongoTemplate.find(query,Comment.class,"comment");
    }

    public void remove(String commentId) {
        //删除数据库的同时删除缓存
        commentDao.deleteById(commentId);
    }

    public List<Comment> findAll() {
        return commentDao.findAll();
    }
}
