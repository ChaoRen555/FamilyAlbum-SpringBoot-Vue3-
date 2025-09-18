package com.album.mapper;

import com.album.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from user where id = #{id}")
    User selectById(Integer id);

    List<User> selectAll(User user);

    @Select("select * from user where username=#{username}")
    User selectByUsername(String username);

    void insert(User user);
    @Delete("delete from user where id=#{id}")
    void deleteById(Integer id);


    void updateById(User user);

    void deleteIds(List<Integer> ids);
}
