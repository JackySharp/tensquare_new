package com.tensquare.friend.client;

import com.tensquare.common.entity.Result;
import com.tensquare.friend.client.impl.UserClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(value = "tensquare-user", fallback = UserClientImpl.class)
public interface UserClient {

    @PutMapping("/user/incfollow/{userId}/{x}")
    Result incFollows(@PathVariable("userId") String userId, @PathVariable("x") int x);

    @PutMapping("/user/incfans/{userId}/{x}")
    Result incFans(@PathVariable("userId") String userId, @PathVariable("x") int x);

    @GetMapping("/user/{userId}")
    Result findById(@PathVariable("userId") String userId);
}
