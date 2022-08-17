package com.warehouse.workout.config.security.handler;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

        // 액세스 토큰을 만든다.
        String accessToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeConstant.MILLISECOND_OF_TEN_MINUTE)) // 현재 시간으로부터 10분 뒤 만료
                .withIssuer(request.getRequestURL().toString()) // 토큰을 발행한 party가 누군지 설정한다.
                .withClaim("roles",user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        String refreshToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeConstant.MILLISECOND_OF_HALF_HOUR)) // 현재 시간으로부터 30분 뒤 만료
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);

        UserEntity findUserEntity = userRepository.findByusername(user.getUsername());
        Optional<UserRefreshTokenEntity> userRefreshTokenEntityByUser = userRefreshTokenRepository.findUserRefreshTokenEntityByUser(findUserEntity);

        userRefreshTokenEntityByUser.ifPresent(userRefreshTokenRepository::delete);

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
