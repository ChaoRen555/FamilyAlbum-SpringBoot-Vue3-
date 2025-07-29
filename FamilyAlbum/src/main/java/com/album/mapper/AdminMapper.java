package com.album.mapper;

import com.album.entity.Admin;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface AdminMapper {

    @Select("select * from admin where username =#{username}")
    Admin getByUsername(String username);

    void insert(Admin admin);

    @Select("select * from admin where id=#{id}")
    Admin selectById(Integer id);

    @Delete("delete from admin where id =#{id}")
    void deleteById(Integer id);

    void update(Admin admin);

    List<Admin> selectAll(Admin admin);
}
