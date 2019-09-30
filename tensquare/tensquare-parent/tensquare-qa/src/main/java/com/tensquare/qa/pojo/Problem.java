package com.tensquare.qa.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "tb_problem")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Problem {
    @Id
    private String id;//问题id
    private String title;//问题标题
    private String content;//问题内容
    private Timestamp createtime;//问题创建时间
    private Timestamp updatetime;//问题更新时间
    private String userid;//用户id
    private String nickname;//用户昵称
    private long visits;//浏览量
    private long thumbup;//点赞数
    private long reply;//回复数
    private String solve;//是否解决
    private String replyname;//回复人昵称
    private Timestamp replytime;//回复时间
}
