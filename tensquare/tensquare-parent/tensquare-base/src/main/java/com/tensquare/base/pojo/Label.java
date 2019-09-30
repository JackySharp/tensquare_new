package com.tensquare.base.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_label")
public class Label {
    @Id
    private String id;//标签ID
    private String labelname;//标签名称
    private String state;//标签状态
    private BigInteger count;//标签被使用数
    private String recommend;//是否推荐
    private BigInteger fans;//粉丝数
}
