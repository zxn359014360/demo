package com.controller;

import com.constant.RedisConst;
import com.model.User;
import com.utils.redis.CacheForKeyExpire;
import com.utils.redis.CacheUtils;
import com.utils.redis.RedisCache;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.service.UserService;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("list")
    public List<User> list(){
        List<User> list = userService.findAllUser();
        return list;
    }




}
