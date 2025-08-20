package com.album.service;

import com.album.entity.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface UserService {
    User selectById(Integer id);

    List<User> selectAll(User user);

    PageInfo<User> selectPage(User user, Integer pageNum, Integer pageSize);

    void add(User user);

    void deleteById(Integer id);

    void deleteBatch(List<Integer> ids);

    void updateById(User user);
}
