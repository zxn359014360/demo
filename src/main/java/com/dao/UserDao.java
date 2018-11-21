package com.dao;

import com.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {

    List<User> findAll();

    void save(User user);

    User getById(String id);
}
