package com.service;

import com.model.User;

import java.util.List;

public interface UserService {
    /**
     * 根据接口查询所用的用户
     */
    List<User> findAllUser();

    User getById(String id);

    void register(User user);
}
