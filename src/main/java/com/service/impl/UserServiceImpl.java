package com.service.impl;

import java.util.List;

import com.constant.RedisConst;
import com.dao.UserDao;
import com.model.User;
import com.service.UserService;
import com.utils.redis.CacheForKeyExpire;
import com.utils.redis.CacheUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;


    @Override
    public List<User> findAllUser() {
        CacheForKeyExpire cache = CacheUtils.getCacheForKeyExpire();
        List<User> list = cache.getKeyExpireList("AllUser",User.class);
        if(CollectionUtils.isEmpty(list)){
            list = userDao.findAll();
            cache.addKeyExpireList("AllUser",list, RedisConst.ExpireTime.HALF_HOUT);
        }
        return list;
    }

    @Override
    public User getById(String id) {
        User user = this.getUserCacheById(id);
        if(user == null){
            user = userDao.getById(id);
            addUserCache(user,id);
        }
        return user;
    }

    @Override
    public void register(User user) {
        userDao.save(user);
        this.addUserCache(user,user.getId());
    }

    public void addUserCache(User user,String id){
       StringBuffer key =  new StringBuffer(RedisConst.CacheKey.USER).append(id);
        CacheUtils.getCacheForKeyExpire().addKeyExpireObject(key.toString(),user,RedisConst.ExpireTime.ONE_HOUT);
    }

    public User getUserCacheById(String id){
        StringBuffer key =  new StringBuffer(RedisConst.CacheKey.USER).append(id);
        return (User) CacheUtils.getCacheForKeyExpire().getKeyExpireObject(key.toString(),User.class);
    }
}