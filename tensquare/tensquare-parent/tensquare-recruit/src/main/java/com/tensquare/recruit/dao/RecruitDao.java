package com.tensquare.recruit.dao;

import com.tensquare.recruit.pojo.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

//JpaRepository<Recruit,String> "<>"中的第一个参数代表实体类，第二个参数代表主键字段的类型
public interface RecruitDao extends JpaRepository<Recruit,String>, JpaSpecificationExecutor<Recruit> {
    List<Recruit> findTop3ByStateIsOrderByCreatetimeDesc(String stats);
}
