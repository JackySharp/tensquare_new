package com.tensquare.recruit.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.AssertTrue;

@Entity
@Table(name = "tb_enterprise")//表名和实体类名一致时可以省略name属性
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Enterprise {
    @Id
    private String id;//企业id
    private String name;//企业名称
    private String summary;//企业简介
    private String address;//企业地址
    private String labels;//标签列表
    private String coordinate;//坐标
    private String ishot;//是否热门
    private String logo;//企业LOGO
    private String jobcount;//职位数
    private String url;//企业网址
}
