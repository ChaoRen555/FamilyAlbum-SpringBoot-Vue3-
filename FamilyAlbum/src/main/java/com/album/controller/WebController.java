package com.album.controller;

import com.album.common.Result;
import com.album.common.enums.RoleEnum;
import com.album.entity.Account;
import com.album.service.AdminService;
import com.album.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class WebController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public Result hello() {
        return Result.success();
    }

    @PostMapping("/login")
    public Result Login(@RequestBody Account account) {
        log.info("Account:{}", account);
        Account loginAccount = null;
        if (RoleEnum.ADMIN.name().equals(account.getRole())) {
            loginAccount = adminService.login(account);
        }
        if (RoleEnum.USER.name().equals(account.getRole())) {
            loginAccount = userService.login(account);
        }
        return Result.success(loginAccount);
    }

    @PostMapping("/register")
    public Result register(@RequestBody Account account) {
        userService.register(account);
        return Result.success();
    }
}
