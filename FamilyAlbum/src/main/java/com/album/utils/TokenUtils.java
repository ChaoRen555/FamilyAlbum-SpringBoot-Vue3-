package com.album.utils;


import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

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
