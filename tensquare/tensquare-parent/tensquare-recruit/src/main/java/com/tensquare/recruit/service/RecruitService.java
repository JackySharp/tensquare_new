package com.tensquare.recruit.service;

import com.tensquare.common.utils.IdWorker;
import com.tensquare.recruit.dao.RecruitDao;
import com.tensquare.recruit.pojo.Recruit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RecruitService {

    @Autowired
    private RecruitDao recruitDao;

    @Autowired
    private IdWorker idWorker;

    public void add(Recruit recruit) {
        //生成随机id
        recruit.setId(String.valueOf(idWorker.nextId()));
        System.out.println(recruit.getId());
        //添加新的招聘信息
        recruitDao.save(recruit);
    }

    public List<Recruit> findAll() {
        return  recruitDao.findAll();
    }

    public Recruit findById(String recruitId) {
        return recruitDao.findById(recruitId).get();
    }

    public void update(String recruitId, Recruit recruit) {
        recruit.setId(recruitId);
        recruitDao.save(recruit);
    }

    public void remove(String recruitId) {
        recruitDao.deleteById(recruitId);
    }

    public List<Recruit> searchNewList() {
        return recruitDao.findTop3ByStateIsOrderByCreatetimeDesc("1");
    }

    public List<Recruit> searchIsCommend() {
        return recruitDao.findTop3ByStateIsOrderByCreatetimeDesc("2");
    }

}
