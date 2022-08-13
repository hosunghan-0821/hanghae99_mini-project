package com.hanghae.mini_project.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.hanghae.mini_project.security.UserDetailsImpl;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


public final class JwtTokenUtils {

    private static final int SEC = 1;
    private static final int MINUTE = 60 * SEC;
    private static final int HOUR = 60 * MINUTE;

    private static final int DAY = 24 * HOUR;

    // JWT 토큰의 유효기간: 3일 (단위: seconds)
    private static final int JWT_TOKEN_VALID_SEC = 3 * DAY;

    private static final int JWT_TOKEN_VALID_MILLI_SEC = JWT_TOKEN_VALID_SEC * 1000;

    private static final int JWT_REFRESH_TOKEN_VALID_MILLI_SEC = 7*DAY*1000;

    public static final String CLAIM_EXPIRED_DATE = "EXPIRED_DATE";
    public static final String CLAIM_USER_NAME = "USER_NAME";

    public static final String CLAIM_USER_ROLE = "USER_ROLE";
    public static final String JWT_SECRET = "jwt_secret_!@#$%";

    public static String generateJwtToken(UserDetailsImpl userDetails){
        String token = null;

        try{
            token= JWT.create()
                    .withIssuer("RECRUIT_PAGE")
                    .withClaim(CLAIM_USER_NAME,userDetails.getUsername())
                    .withClaim(CLAIM_USER_ROLE,userDetails.getRole())
                    .withClaim(CLAIM_EXPIRED_DATE,new Date(System.currentTimeMillis() + JWT_TOKEN_VALID_MILLI_SEC))
                    .sign(generateAlgorithm());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return token;
    }

    public static String generateJwtRefreshToken(){
        String refreshToken = null;

        try{
            refreshToken=JWT.create()
                    .withClaim(CLAIM_EXPIRED_DATE,new Date(System.currentTimeMillis()+ JWT_REFRESH_TOKEN_VALID_MILLI_SEC))
                    .sign(generateAlgorithm());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return refreshToken;
    }

    private static Algorithm generateAlgorithm(){
        return Algorithm.HMAC256(JWT_SECRET);
    }
}
