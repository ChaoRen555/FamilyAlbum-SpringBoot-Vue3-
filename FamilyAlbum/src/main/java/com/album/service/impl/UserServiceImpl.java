package com.album.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.album.common.Constants;
import com.album.common.enums.ResultCodeEnum;
import com.album.common.enums.RoleEnum;
import com.album.entity.User;
import com.album.exception.CustomerException;
import com.album.mapper.UserMapper;
import com.album.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    public User selectById(Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    public List<User> selectAll(User user) {
        return userMapper.selectAll(user);
    }

    @Override
    public PageInfo<User> selectPage(User user, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> pageInfo = userMapper.selectAll(user);
        return new PageInfo<>(pageInfo);
    }

    @Override
    public void add(User user) {
        User dbUser = userMapper.selectByUsername(user.getUsername());
        if (ObjectUtil.isNotNull(dbUser)) {
            throw new CustomerException(ResultCodeEnum.USER_EXIST_ERROR);
        }
        if (ObjectUtil.isEmpty(user.getPassword())) {
            user.setPassword(Constants.USER_DEFAULT_PASSWORD);
        }
        if (ObjectUtil.isEmpty(user.getName())) {
            user.setName(user.getUsername());
        }
        user.setRole(RoleEnum.USER.name());
        userMapper.insert(user);

    }

    @Override
    public void deleteById(Integer id) {
        userMapper.deleteById(id);
    }

    @Override
    public void deleteBatch(List<Integer> ids) {
       userMapper.deleteIds(ids);
    }

    @Override
    public void updateById(User user) {
        userMapper.updateById(user);
    }


}
