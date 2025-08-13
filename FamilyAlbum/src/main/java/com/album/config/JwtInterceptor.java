package com.album.config;

import cn.hutool.core.util.ObjectUtil;
import com.album.common.Constants;
import com.album.common.enums.ResultCodeEnum;
import com.album.common.enums.RoleEnum;
import com.album.entity.Account;
import com.album.exception.CustomerException;
import com.album.service.AdminService;
import com.album.service.UserService;
import com.album.utils.BaseContext;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;
    @Autowired
    private AdminService adminService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // Get token from header of Request
        String token = request.getHeader(Constants.TOKEN);
        if (token == null) {
            token = request.getParameter(Constants.TOKEN);
        }

        // Start authentication
        if (ObjectUtil.isEmpty(token)) {
            throw new CustomerException(ResultCodeEnum.INVALID_TOKEN_ERROR);
        }

        Account account = null;//Both User and Admin extend to Account
        try {
            // Parse the token to extract stored info
            String userRole = JWT.decode(token).getAudience().get(0);
            String userId = userRole.split("-")[0];
            String role = userRole.split("-")[1];
            // Select by users id
            if (RoleEnum.ADMIN.name().equals(role)) {
                account = adminService.selectById(Integer.valueOf(userId));
            }
            if (RoleEnum.USER.name().equals(role)) {
                account = userService.selectById(Integer.valueOf(userId));
            }
        } catch (Exception e) {
            throw new CustomerException(ResultCodeEnum.INVALID_TOKEN_ERROR);
        }
        if (ObjectUtil.isNull(account)) {
            throw new CustomerException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        try {
            // Token verification using user's password for signing
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(account.getPassword())).build();
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new CustomerException(ResultCodeEnum.INVALID_TOKEN_ERROR);
        }
        //Set account in the local thread
        BaseContext.setCurrentAccount(account);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) {

        BaseContext.removeCurrentAccount();
    }
}
