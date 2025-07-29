package com.album.service.impl;

import com.album.common.Constants;
import com.album.common.enums.ResultCodeEnum;
import com.album.common.enums.RoleEnum;
import com.album.entity.Admin;
import com.album.exception.CustomerException;
import com.album.mapper.AdminMapper;
import com.album.service.AdminService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;


    public void add(Admin admin) {

        Admin user = adminMapper.getByUsername(admin.getUsername());
        if (user != null) {
            throw new CustomerException(ResultCodeEnum.USER_EXIST_ERROR);
        }
        if (admin.getPassword() == null || admin.getPassword().equals("")) {
            admin.setPassword(Constants.USER_DEFAULT_PASSWORD);
        }
        if (admin.getUsername() == null || admin.getUsername().equals("")) {
            admin.setName(admin.getUsername());
        }
        admin.setRole(RoleEnum.ADMIN.name());
        adminMapper.insert(admin);
    }


    public Admin selectById(Integer id) {
        return adminMapper.selectById(id);
    }


    public void deleteById(Integer id) {
        adminMapper.deleteById(id);
    }


    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            adminMapper.deleteById(id);
        }
    }


    public void update(Admin admin) {
        adminMapper.update(admin);
    }


    public List<Admin> selectAll(Admin admin) {
        return adminMapper.selectAll(admin);
    }

    /**
     * Pagination
     */
    public PageInfo<Admin> selectPage(Admin admin, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Admin> page = adminMapper.selectAll(admin);
        return new PageInfo<>(page);
    }


}
