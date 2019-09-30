package com.tensquare.friend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tb_friend")
@IdClass(Friend.class)
@Data
@AllArgsConstructor
public class Friend implements Serializable {
    @Id
    private String userid;
    @Id
    private String friendid;
    private String islike;

    public Friend() {
    }

    public Friend(String userid, String friendid) {
        this.userid = userid;
        this.friendid = friendid;
    }
}
