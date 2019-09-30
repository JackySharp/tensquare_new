package com.tensquare.qa.service;

import com.tensquare.common.entity.PageResult;
import com.tensquare.common.utils.IdWorker;
import com.tensquare.qa.dao.ProblemDao;
import com.tensquare.qa.pojo.Problem;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class ProblemService {

    @Autowired
    private ProblemDao problemDao;

    @Autowired
    private IdWorker idWorker;

    public void add(Problem problem) {
        //生成随机id
        problem.setId(String.valueOf(idWorker.nextId()));
        System.out.println(problem.getId());
        //添加新的招聘信息
        problemDao.save(problem);
    }

    public List<Problem> findAll() {
        return  problemDao.findAll();
    }

    public Problem findById(String problemId) {
        return problemDao.findById(problemId).get();
    }

    public void update(String problemId, Problem recruit) {
        recruit.setId(problemId);
        problemDao.save(recruit);
    }

    public void remove(String problemId) {
        problemDao.deleteById(problemId);
    }

    public PageResult<Problem> searchNewList(String label, int page, int size) {
        Page<Problem> problems = null;

        //创建分页查询条件
        Pageable pageable = PageRequest.of(page - 1,size);

        if ("0".equals(label)) {
            //如果标签id为0，则查询所有问题
            problems = problemDao.findAll(pageable);
        } else {
            //依据问题id列表查询最新问题列表
            problems = problemDao.fineNewProblem(label, pageable);
        }
        return new PageResult<Problem>(problems.getTotalElements(),problems.getContent());
    }

    public PageResult<Problem> searchHotList(String label, int page, int size) {
        Page<Problem> problems = null;

        //创建分页查询条件
        Pageable pageable = PageRequest.of(page - 1,size);

        if ("0".equals(label)) {
            //如果标签id为0，则查询所有问题
            problems = problemDao.findAll(pageable);
        } else {
            //依据问题id列表查询最新问题列表
            problems = problemDao.fineHotProblem(label, pageable);
        }
        return new PageResult<Problem>(problems.getTotalElements(),problems.getContent());
    }

    public PageResult<Problem> searchWaitList(String label, int page, int size) {
        Page<Problem> problems = null;

        //创建分页查询条件
        Pageable pageable = PageRequest.of(page - 1,size);

        if ("0".equals(label)) {
            //如果标签为0,则查询所有问题
            problems = problemDao.findAll(pageable);
        } else {
            //依据问题id列表查询最新问题列表
            problems = problemDao.fineWaitProblem(label, pageable);
        }
        return new PageResult<Problem>(problems.getTotalElements(),problems.getContent());
    }

}
