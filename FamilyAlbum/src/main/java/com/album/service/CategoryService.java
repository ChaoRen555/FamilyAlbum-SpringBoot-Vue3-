package com.album.service;

import com.album.entity.Category;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface CategoryService {
    List<Category> selectAll(Category category);

    void add(Category category);

    void deleteById(Integer id);

    void deleteBatch(List<Integer> ids);

    void updateById(Category category);

    Category selectById(Integer id);

    PageInfo<Category> selectPage(Category category, Integer pageNum, Integer pageSize);

    PageInfo<Category> selectAlbumPage(Category category, Integer pageNum, Integer pageSize);
}
