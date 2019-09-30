package com.tensquare.qa.service;

import com.tensquare.common.entity.PageResult;
import com.tensquare.common.utils.IdWorker;
import com.tensquare.qa.dao.ReplyDao;
import com.tensquare.qa.pojo.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyService {

    @Autowired
    private ReplyDao replyDao;

    @Autowired
    private IdWorker idWorker;

    public void add(Reply reply) {
        //生成随机id
        reply.setId(String.valueOf(idWorker.nextId()));
        System.out.println(reply.getId());
        //添加新的招聘信息
        replyDao.save(reply);
    }

    public List<Reply> findAll() {
        return replyDao.findAll();
    }

    public Reply findById(String replyId) {
        return replyDao.findById(replyId).get();
    }

    public void update(String replyId, Reply reply) {
        reply.setId(replyId);
        replyDao.save(reply);
    }

    public void remove(String replyId) {
        replyDao.deleteById(replyId);
    }

    public List<Reply> searchByProblemId(String problemId) {
        return replyDao.findByProblemid(problemId);
    }

}
