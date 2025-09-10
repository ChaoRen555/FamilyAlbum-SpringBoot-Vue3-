package com.album.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.album.common.enums.RoleEnum;
import com.album.entity.Account;
import com.album.entity.Category;
import com.album.entity.Collect;
import com.album.mapper.CollectMapper;
import com.album.service.CategoryService;
import com.album.service.CollectService;
import com.album.utils.BaseContext;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectServiceImpl implements CollectService {
    @Autowired
    private CollectMapper collectMapper;
    @Autowired
    private CategoryService categoryService;

    @Override
    public void add(Collect collect) {
        collectMapper.insert(collect);
    }

    @Override
    public void deleteById(Integer id) {
        collectMapper.deleteById(id);
    }

    @Override
    public Collect selectById(Integer id) {
        return collectMapper.selectById(id);
    }

    @Override
    public List<Collect> selectAll(Collect collect) {
        return collectMapper.selectAll(collect);
    }

    @Override
    public void update(Collect collect) {
        collectMapper.update(collect);
    }

    @Override
    public void deleteBatch(List<Integer> ids) {
        collectMapper.deleteBatch(ids);
    }

    @Override
    public PageInfo<Collect> pageQuery(Collect collect, Integer pageNum, Integer pageSize) {
        Account currentUser = BaseContext.getCurrentAccount();
        if (RoleEnum.USER.name().equals(currentUser.getRole())) {
            collect.setUserId(currentUser.getId());
        }
        PageHelper.startPage(pageNum, pageSize);

        List<Collect> list = this.selectAll(collect);
        for (Collect dbCollect : list) {
            try {
                Category category = categoryService.selectById(dbCollect.getCategoryId());
                if (ObjectUtil.isNotEmpty(category)) {
                    collect.setCategory(category);
                }
            } catch (Exception e) {
                continue;
            }

        }
        return new PageInfo<>(list);
    }
}
