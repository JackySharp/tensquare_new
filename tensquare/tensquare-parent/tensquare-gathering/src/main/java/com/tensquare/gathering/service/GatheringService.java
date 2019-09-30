package com.tensquare.gathering.service;

import com.tensquare.common.utils.IdWorker;
import com.tensquare.gathering.dao.GatheringDao;
import com.tensquare.gathering.pojo.Gathering;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class GatheringService {

    @Autowired
    private GatheringDao gatheringDao;

    @Autowired
    private IdWorker idWorker;

    public void add(Gathering gathering) {
        //生成随机id
        gathering.setId(String.valueOf(idWorker.nextId()));
        //添加新的招聘信息
        gatheringDao.save(gathering);
    }

    public List<Gathering> findAll() {
        return gatheringDao.findAll();
    }

    //更新缓存
    //以活动id为key，以方法的返回值为value放到缓存中
    @Cacheable(key = "#gatheringId", value = "gathering")
    public Gathering findById(String gatheringId) {
        return gatheringDao.findById(gatheringId).get();
    }

    //清理缓存
    @CacheEvict(key = "#gatheringId")
    public void update(String gatheringId, Gathering gathering) {
        gathering.setId(gatheringId);
        gatheringDao.save(gathering);

    }

    //清理缓存
    @CacheEvict(key = "#gatheringId")
    public void remove(String gatheringId) {
        gatheringDao.deleteById(gatheringId);
    }

}
