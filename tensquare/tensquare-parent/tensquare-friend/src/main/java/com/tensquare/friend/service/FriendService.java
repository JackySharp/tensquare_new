package com.tensquare.friend.service;

import com.tensquare.friend.dao.FriendDao;
import com.tensquare.friend.dao.NoFriendDao;
import com.tensquare.friend.pojo.Friend;
import com.tensquare.friend.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class FriendService {

    @Autowired
    private FriendDao friendDao;

    @Autowired
    private NoFriendDao noFriendDao;

    public void addOrNot(String userid, String friendid, String type) {
        if ("1".equals(type)) {
            //查询当前用户是否在好友的黑名单中
            if (null != noFriendDao.findByUseridAndFriendid(friendid, userid)) {
                throw new RuntimeException("对方设置了不允许加好友");
            }
            //如果之前把对方拉黑，则加好友时自动将对方从黑名单移除
            if (null != noFriendDao.findByUseridAndFriendid(userid, friendid)) {
                noFriendDao.deleteByUseridAndFriendid(userid,friendid);
            }
            //查询对方是否也加了当前用户的好友，"0"代表单方面喜欢，"1"代表相互喜欢
            Friend friend = friendDao.findByUseridAndFriendid(friendid, userid);
            if (null != friend) {
                //如果对方也加了自己为好友，则双方都设置为相互喜欢
                friendDao.saveFriend(friendid,userid,"1");
                friendDao.save(new Friend(userid,friendid,"1"));
            } else {
                friendDao.save(new Friend(userid, friendid, "0"));
            }
        } else if ("2".equals(type)) {
            //拉黑好友
            noFriendDao.save(new NoFriend(userid,friendid));
            //删除好友关系
            friendDao.deleteFriend(userid,friendid);
            //修改对方为单方面喜欢
            friendDao.saveFriend(friendid, userid,"0");
        } else {
            throw new RuntimeException("未知操作");
        }
    }

    public void delete(String userid, String friendid) {
        friendDao.deleteFriend(userid,friendid);
        //将对方修改为单方面喜欢
        friendDao.saveFriend(friendid,userid,"0");
    }
}
