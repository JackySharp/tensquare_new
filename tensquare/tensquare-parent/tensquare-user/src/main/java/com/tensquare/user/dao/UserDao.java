package com.tensquare.user.dao;

import com.tensquare.user.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserDao extends JpaRepository<User,String>, JpaSpecificationExecutor<User> {

    @Query(nativeQuery = true, value = "select * from tb_user where mobile = ?1")
    User findByMobile(String mobile);

    @Modifying
    @Query(nativeQuery = true, value = "update tb_user set followcount = followcount + ?2 where id = ?1")
    void incFollows(String userid, int n);

    @Modifying
    @Query(nativeQuery = true, value = "update tb_user set fanscount = fanscount + ?2 where id = ?1")
    void incFans(String userid, int n);
}
