package com.album.service;

import com.album.entity.Picture;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface PictureService {
    void addPicture(Picture picture);

    void deleteById(Integer id);

    void deleteBatch(List<Integer> ids);

    void updateById(Picture picture);

    Picture selectById(Integer id);

    List<Picture> selectAll(Picture picture);


    PageInfo<Picture> selectPage(Picture picture, Integer pageNum, Integer pageSize);
}
