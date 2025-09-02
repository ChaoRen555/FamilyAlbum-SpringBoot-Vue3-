package com.album.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.album.common.enums.RoleEnum;
import com.album.entity.Account;
import com.album.entity.Category;
import com.album.entity.User;
import com.album.exception.CustomerException;
import com.album.mapper.CategoryMapper;
import com.album.service.CategoryService;
import com.album.service.UserService;
import com.album.utils.BaseContext;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private UserService userService;

    public List<Category> selectAll(Category category) {
        return categoryMapper.selectAll(category);
    }

    @Override
    public void add(Category category) {
        category.setTime(DateUtil.now());
        category.setStatusRadio("Normal");
        categoryMapper.insert(category);
    }

    @Override
    public void deleteById(Integer id) {
        categoryMapper.deleteById(id);
    }

    @Override
    public void deleteBatch(List<Integer> ids) {
        categoryMapper.deleteByIds(ids);
    }

    @Override
    public void updateById(Category category) {
        categoryMapper.update(category);
    }

    @Override
    public Category selectById(Integer id) {
        Category category = categoryMapper.selectById(id);
        if ("violation".equals(category.getStatusRadio())) {
            throw new CustomerException("500", "This album is unavailable due to a violation.");
        }
        User user = userService.selectById(category.getUserId());
        if (ObjectUtil.isNotEmpty(user)) {
            category.setUserName(user.getName());
            category.setUserAvatar(user.getAvatar());
        }
        return category;
    }

    @Override
    public PageInfo<Category> selectPage(Category category, Integer pageNum, Integer pageSize) {
        Account currentUser = BaseContext.getCurrentAccount();
        if (RoleEnum.USER.name().equals(currentUser.getRole())) {
            category.setUserId(currentUser.getId());
        }
        //Users can only access the date that belongs to him
        PageHelper.startPage(pageNum, pageSize);
        List<Category> list = this.selectAll(category);
        for (Category dbCategory : list) {
            User user = userService.selectById(dbCategory.getUserId());
            if (ObjectUtil.isNotEmpty(user)) {
                dbCategory.setUserName(user.getName());
                dbCategory.setUserAvatar(user.getAvatar());
            }
        }
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<Category> selectAlbumPage(Category category, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Category> list = this.selectAll(category);
        for (Category dbCategory : list) {
            User user = userService.selectById(dbCategory.getUserId());
            if (ObjectUtil.isNotEmpty(user)) {
                dbCategory.setUserName(user.getName());
                dbCategory.setUserAvatar(user.getAvatar());
            }
        }
        Collections.shuffle(list);
        return new PageInfo<>(list);
    }
}
