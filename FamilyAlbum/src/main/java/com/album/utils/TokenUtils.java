package com.album.utils;


import cn.hutool.core.date.DateUtil;
import com.album.common.Constants;
import com.album.common.enums.RoleEnum;
import com.album.entity.Account;
import com.album.service.AdminService;
import com.album.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;


public class TokenUtils {

    /**
     * Create token
     * @param data is userId
     * @param sign is password
     * @return token string
     */
    public static String createToken(String data, String sign) {
        return JWT.create().withAudience(data) // Store the userId in the token as the payload
                .withExpiresAt(DateUtil.offsetDay(new Date(), 1)) // Expire in 1 day
                .sign(Algorithm.HMAC256(sign)); // Use the password as the secret key for the token
    }


}
