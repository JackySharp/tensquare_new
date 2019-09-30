package com.tensquare.qa.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "tb_reply")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reply {
    @Id
    private String id;//回复编号
    private String problemid;//问题id
    private String content;//回答内容
    private Timestamp createtime;//创建时间
    private Timestamp updatetime;//更新时间
    private String userid;//回答人id
    private String nickname;//回答人昵称
}
