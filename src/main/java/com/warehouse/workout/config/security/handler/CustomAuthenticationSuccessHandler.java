package com.warehouse.workout.config.security.handler;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.warehouse.workout.config.security.common.TokenCommon;
import com.warehouse.workout.constant.number.TimeConstant;
import com.warehouse.workout.user.entity.UserEntity;
import com.warehouse.workout.user.entity.UserRefreshTokenEntity;
import com.warehouse.workout.user.repository.UserRefreshTokenRepository;
import com.warehouse.workout.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRefreshTokenRepository userRefreshTokenRepository;
    private final UserRepository userRepository;



    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        User user = (User) authentication.getPrincipal(); // 접속한 사용자의 정보(User Entity아님!!)

        String accessToken = TokenCommon.publishAccessToken(user.getUsername(),request.getRequestURL().toString(),
                user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));

        String refreshToken = TokenCommon.publishRefreshToken(user.getUsername(),request.getRequestURL().toString());

        UserEntity findUserEntity = userRepository.findByusername(user.getUsername());
        UserRefreshTokenEntity userRefreshTokenEntityByUser = userRefreshTokenRepository.findUserRefreshTokenEntityByUser(findUserEntity);
        if(userRefreshTokenEntityByUser != null) userRefreshTokenRepository.delete(userRefreshTokenEntityByUser);

        userRefreshTokenRepository.save(UserRefreshTokenEntity.builder()
                .user(userRepository.findByusername(user.getUsername()))
                .issueTime(LocalDateTime.now())
                .expiredTime(LocalDateTime.now().plusHours(1))
                .refreshToken(refreshToken)
                .build());

        // http Only Cookie에 Refresh Token을 담아서 보낸다.
        Cookie cookie = new Cookie("refresh_token",refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);

        // 응답 바디에 토큰 세팅
        Map<String,String> tokens = new HashMap<>();
        tokens.put("access_token", accessToken);
        tokens.put("refresh_token", refreshToken);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        //response.getWriter().println(tokens);

        new ObjectMapper().writeValue(response.getOutputStream(),tokens);

    }
}
