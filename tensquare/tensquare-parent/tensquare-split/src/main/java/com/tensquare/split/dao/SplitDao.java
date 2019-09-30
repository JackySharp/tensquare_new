package com.tensquare.split.dao;

import com.tensquare.split.pojo.Split;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface SplitDao extends MongoRepository<Split,String> {
    Page<Split> findByParentidEquals(String parentid, Pageable pageable);
}
