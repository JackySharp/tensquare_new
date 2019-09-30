package com.tensquare.base.dao;

import com.tensquare.base.pojo.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface LabelDao extends JpaRepository<Label, String>, JpaSpecificationExecutor<Label> {
    List<Label> findAllByRecommendEquals(String recommend);
    List<Label> findAllByStateEquals(String state);
}
