package com.tensquare.recruit.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_recruit")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recruit {
    @Id
    private String id;//职位id
    private String jobname;//职位名称
    private String salary;//薪资范围
    private String experience;//经验要求
    private String education;//学历要求
    private String type;//任职方式
    private String address;//办公地址
    private String createtime;//职位创建时间
    private String eid;//企业id
    private String state;//职位发布状态
    private String label;//标签
    private String url;//网址
    private String content1;//职位描述
    private String content2;//任职要求
}
