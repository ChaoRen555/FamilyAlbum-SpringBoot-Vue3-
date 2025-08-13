package com.album.mapper;

import com.album.entity.Picture;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PictureMapper {

    void insert(Picture picture);

    @Delete("delete from picture where id=#{id}")
    void deleteById(Integer id);

    void deleteByIds(List<Integer> ids);

    void update(Picture picture);

    @Select("select * from picture where id = #{id}")
    Picture selectById(Integer id);

    List<Picture> selectAll(Picture picture);
}
