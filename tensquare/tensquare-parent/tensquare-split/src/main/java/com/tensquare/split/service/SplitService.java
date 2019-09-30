package com.tensquare.split.service;


import com.tensquare.common.entity.PageResult;
import com.tensquare.common.utils.IdWorker;
import com.tensquare.split.dao.SplitDao;
import com.tensquare.split.pojo.Split;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class SplitService {

    @Autowired
    private SplitDao splitDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private IdWorker idWorker;

    public void add(Split split) {
        //生成随机id
        split.set_id(String.valueOf(idWorker.nextId()));
        //添加新的吐槽
        splitDao.save(split);
    }

    public List<Split> findAll() {
        return splitDao.findAll();
    }

    public Split findById(String splitId) {
        return splitDao.findById(splitId).get();
    }

    public void update(String splitId, Split split) {
        split.set_id(splitId);
        splitDao.save(split);
    }

    public void remove(String splitId) {
        splitDao.deleteById(splitId);
    }

    public void thumbup(String splitId) throws Exception {

        //模拟已登录用户id
        String userid = "008";

        //先在缓存中查询当前用户是否已经给该吐槽点过赞
        Object obj = redisTemplate.boundValueOps("user_" + userid).get();

        if (null != obj) {
            throw new Exception("不能重复点赞");
        } else {
            /*更新点赞数
            Split split = splitDao.findById(splitId).get();
            split.setThumbup(split.getThumbup() + 1);
            splitDao.save(split);
            */

            //mongoTemplate更新点赞数效率更高
            Query query = new Query().addCriteria(Criteria.where("_id").is(splitId));
            Update update = new Update().inc("thumbup", 1);
            mongoTemplate.updateFirst(query, update, "split");

            //将登陆用户的点赞记录添加到缓存
            redisTemplate.boundValueOps("user_" + userid).set(splitId);
        }
    }

    public PageResult<Split> findByParentid(String parentid, int page, int size) {
        //创建分页对象
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Split> splits = splitDao.findByParentidEquals(parentid, pageable);
        return new PageResult<Split>(splits.getTotalElements(), splits.getContent());
    }
}
