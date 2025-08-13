package com.album.service.impl;

import cn.hutool.core.date.DateUtil;
import com.album.common.enums.RoleEnum;
import com.album.entity.Account;
import com.album.entity.Picture;
import com.album.mapper.PictureMapper;
import com.album.service.PictureService;
import com.album.utils.BaseContext;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class PictureServiceImpl implements PictureService {
    @Autowired
    private PictureMapper pictureMapper;

    public void addPicture(Picture picture) {
        picture.setTime(DateUtil.now());
        pictureMapper.insert(picture);
    }


    public void deleteById(Integer id) {
        pictureMapper.deleteById(id);
    }


    public void deleteBatch(List<Integer> ids) {
        pictureMapper.deleteByIds(ids);
    }


    public void updateById(Picture picture) {
        Account currentUser = BaseContext.getCurrentAccount();
        if (RoleEnum.USER.name().equals(currentUser.getRole())) {
            picture.setStatusRadio("Pending Review");
        }
        pictureMapper.update(picture);
    }


    public Picture selectById(Integer id) {
        return pictureMapper.selectById(id);
    }

    @Override
    public List<Picture> selectAll(Picture picture) {

        return pictureMapper.selectAll(picture);
    }

    @Override
    public PageInfo<Picture> selectPage(Picture picture, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Picture> list = pictureMapper.selectAll(picture);
        return new PageInfo<>(list);
    }


}
