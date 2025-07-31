package com.album.controller;

import com.album.common.Result;
import com.album.common.enums.RoleEnum;
import com.album.entity.Account;
import com.album.service.AdminService;
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
        //TODO If account is User
        return Result.success(loginAccount);
    }
}
