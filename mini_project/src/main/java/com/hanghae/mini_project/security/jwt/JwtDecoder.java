package com.hanghae.mini_project.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

import static com.hanghae.mini_project.security.jwt.JwtTokenUtils.*;

@Component
public class JwtDecoder {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public String decodeUsername(String token){
        DecodedJWT decodedJWT = isValidToken(token).orElseThrow(()->new AuthenticationServiceException("토큰이 유효하지 않습니다"));
        Date expiredDate = decodedJWT.getClaim(CLAIM_EXPIRED_DATE).asDate();


        Date now = new Date();
        if(expiredDate.before(now)){
            throw new AuthenticationServiceException("유효시간이 지난 토큰입니다 ");
        }
        String username = decodedJWT
                .getClaim(CLAIM_USER_NAME)
                .asString();
        return username;
    }

    private Optional<DecodedJWT> isValidToken(String token){

        DecodedJWT jwt = null;
        try{
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
            JWTVerifier verifier = JWT
                                    .require(algorithm)
                                    .build();

            jwt = verifier.verify(token);
        }catch (Exception e){
            log.error(e.getMessage());
        }

        return Optional.ofNullable(jwt);
    }
    public boolean isValidRefreshToken(String refreshToken){
        DecodedJWT decodedJWT = isValidToken(refreshToken).orElseThrow(()->new IllegalArgumentException("유효한 토큰이 아닙니다."));
        Date expiredDate = decodedJWT.getClaim(CLAIM_EXPIRED_DATE).asDate();

        Date now = new Date();
        if(expiredDate.before(now)){
            throw new IllegalArgumentException("유효시간이 지난 토큰 입니다");
        }
        return true;
    }
}
