package com.album.service;

import com.album.entity.Account;
import com.album.entity.Admin;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface AdminService {
    void add(Admin admin);

    Admin selectById(Integer id);

    void deleteById(Integer id);

    void deleteBatch(List<Integer> ids);

    void update(Admin admin);

    List<Admin> selectAll(Admin admin);

    PageInfo<Admin> selectPage(Admin admin, Integer pageNum, Integer pageSize);

    Account login(Account account);

    void updatePassword(Account account);

}
