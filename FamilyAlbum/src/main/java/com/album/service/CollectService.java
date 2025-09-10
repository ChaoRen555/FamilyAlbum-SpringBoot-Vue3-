package com.album.service;

import com.album.entity.Collect;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface CollectService {
    void add(Collect collect);

    void deleteById(Integer id);

    Collect selectById(Integer id);

    List<Collect> selectAll(Collect collect);

    void update(Collect collect);

    void deleteBatch(List<Integer> ids);

    PageInfo<Collect> pageQuery(Collect collect, Integer pageNum, Integer pageSize);

}
