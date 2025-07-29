package com.album.controller;


import com.album.common.Result;
import com.album.entity.Admin;
import com.album.service.AdminService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/add")
    public Result addNew(@RequestBody Admin admin) {
        log.info("New Admin : {}", admin);
        adminService.add(admin);
        return Result.success();
    }

    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Admin admin = adminService.selectById(id);
        return Result.success(admin);
    }

    @GetMapping("/selectAll")
    public Result selectAll(@RequestBody Admin admin) {
        List<Admin> list = adminService.selectAll(admin);
        return Result.success(list);
    }

    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Integer id) {
        adminService.deleteById(id);
        return Result.success();
    }

    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        adminService.deleteBatch(ids);
        return Result.success();
    }

    @PutMapping("/update")
    public Result updateAdmin(@RequestBody Admin admin) {
        adminService.update(admin);
        return Result.success();
    }

    /**
     * Pagination
     */
    @GetMapping("/selectPage")
    public Result selectPage(Admin admin,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "5") Integer pageSize) {

        PageInfo<Admin> pageInfo = adminService.selectPage(admin, pageNum, pageSize);
        //PageInfo contains pageNum,pageSize and list info
        //Better than Page
        return Result.success(pageInfo);
    }


}
