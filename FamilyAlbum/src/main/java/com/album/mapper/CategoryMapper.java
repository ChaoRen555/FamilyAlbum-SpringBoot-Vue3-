package com.album.mapper;

import com.album.entity.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {
    List<Category> selectAll(Category category);

    void insert(Category category);

    @Delete("delete from category where id=#{id}")
    void deleteById(Integer id);

    void deleteByIds(List<Integer> ids);

    void update(Category category);

    @Select("select * from category where id=#{id}")
    Category selectById(Integer id);
}
