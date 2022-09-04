package com.warehouse.workout.config.security.common;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.warehouse.workout.constant.number.TimeConstant;
import org.springframework.security.core.GrantedAuthority;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TokenCommon {

    private static final Algorithm algorithm = Algorithm.HMAC256("123thisismykey321".getBytes());

    public static String publishAccessToken(String username, String issuer, List<String> roles){

        // 액세스 토큰을 만든다.
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeConstant.MILLISECOND_OF_TEN_MINUTE)) // 현재 시간으로부터 10분 뒤 만료
                .withIssuer(issuer) // 토큰을 발행한 party가 누군지 설정한다.
                .withClaim("roles",roles)
                .sign(algorithm);
    }

    public static String publishRefreshToken(String username, String issuer){

        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeConstant.MILLISECOND_OF_HALF_HOUR)) // 현재 시간으로부터 30분 뒤 만료
                .withIssuer(issuer)
                .sign(algorithm);
    }

    public static boolean isAccessTokenExpired(String authorizationHeader){
        boolean result = true;
        if(authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring("Bearer ".length());// prefix를 잘라낸다.
            Algorithm algorithm = Algorithm.HMAC256("secret".getBytes()); // 암호화 알고리즘을 만든다.
            JWTVerifier verifier = JWT.require(algorithm).build();//JWT 검증 인스턴스를 만든다.
            DecodedJWT decodedJWT = verifier.verify(token); //Token을 디코딩한다.

            Date expiresAt = decodedJWT.getExpiresAt();
            Date issuedAt = decodedJWT.getIssuedAt();
            Date currentAt = new Date();
            if(expiresAt.before(currentAt) && currentAt.after(issuedAt)){
                result = false; //만료전
            }
        }

        return result;


    }


}
