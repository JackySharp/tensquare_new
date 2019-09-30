package com.tensquare.qa.dao;

import com.tensquare.qa.pojo.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface ProblemDao extends JpaRepository<Problem,String>, JpaSpecificationExecutor<Problem> {

    @Query(nativeQuery = true, value = "select tb_problem.* from tb_problem,tb_pl where id = problemid and labelid = ?1 order by createtime desc")
    Page<Problem> fineNewProblem(String label, Pageable pageable);

    @Query(nativeQuery = true, value = "select tb_problem.* from tb_problem,tb_pl where id = problemid and labelid = ?1 order by visits desc")
    Page<Problem> fineHotProblem(String label, Pageable pageable);

    @Query(nativeQuery = true, value = "select tb_problem.* from tb_problem,tb_pl where id = problemid and labelid = ?1 and solve = '0' order by createtime asc")
    Page<Problem> fineWaitProblem(String label, Pageable pageable);
}
