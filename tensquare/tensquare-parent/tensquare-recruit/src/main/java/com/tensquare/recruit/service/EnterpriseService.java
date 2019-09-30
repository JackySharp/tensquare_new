package com.tensquare.recruit.service;

import com.tensquare.common.utils.IdWorker;
import com.tensquare.recruit.dao.EnterpriseDao;
import com.tensquare.recruit.pojo.Enterprise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnterpriseService {

    @Autowired
    private EnterpriseDao enterpriseDao;

    @Autowired
    private IdWorker idWorker;

    public void add(Enterprise enterprise) {
        //生成随机id
        enterprise.setId(String.valueOf(idWorker.nextId()));
        System.out.println(enterprise.getId());
        //添加新的招聘信息
        enterpriseDao.save(enterprise);
    }

    public List<Enterprise> findAll() {
        return  enterpriseDao.findAll();
    }

    public Enterprise findById(String enterpriseId) {
        return enterpriseDao.findById(enterpriseId).get();
    }

    public void update(String enterpriseId, Enterprise enterprise) {
        enterprise.setId(enterpriseId);
        enterpriseDao.save(enterprise);
    }

    public void remove(String recruitId) {
        enterpriseDao.deleteById(recruitId);
    }

    public List<Enterprise> searchIsHot() {
        return enterpriseDao.findTop3ByIshotIsOrderByJobcountDesc("1");
    }
}
