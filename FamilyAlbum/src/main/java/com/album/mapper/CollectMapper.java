package com.album.mapper;

import com.album.entity.Collect;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CollectMapper {

    void insert(Collect collect);

    @Delete("delete from collect where id =#{id}")
    void deleteById(Integer id);

    @Select("select * from collect where id =#{id}")
    Collect selectById(Integer id);

    List<Collect> selectAll(Collect collect);

    void update(Collect collect);

    void deleteBatch(List<Integer> ids);
}
