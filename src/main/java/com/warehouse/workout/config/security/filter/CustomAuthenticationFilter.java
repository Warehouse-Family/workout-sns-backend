package com.warehouse.workout.config.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Transactional
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager=authenticationManager;
    }

    @Override // 인증작업을 수행하는 메소드
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = "";
        String password = "";

        try {
            ServletInputStream inputStream = request.getInputStream();
            String msgBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            JSONObject root = new JSONObject(msgBody);

            username = root.get("username").toString();
            password = root.get("password").toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);;
        
        return authenticate;

    }

//    @Override // 인증에 성공한 경우 응답할때 JWT를 담아서 반환한다
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
//        User user = (User) authentication.getPrincipal(); // 접속한 사용자의 정보(User Entity아님!!)
//        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
//
//        // 액세스 토큰을 만든다.
//        String accessToken = JWT.create()
//                .withSubject(user.getUsername())
//                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000)) // 현재 시간으로부터 10분 뒤 만료
//                .withIssuer(request.getRequestURL().toString()) // 토큰을 발행한 party가 누군지 설정한다.
//                .withClaim("roles",user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
//                .sign(algorithm);
//
//        String refreshToken = JWT.create()
//                .withSubject(user.getUsername())
//                .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000)) // 현재 시간으로부터 30분 뒤 만료
//                .withIssuer(request.getRequestURL().toString())
//                .sign(algorithm);
//
//        // http Only Cookie에 Refresh Token을 담아서 보낸다.
//        Cookie cookie = new Cookie("refresh_token",refreshToken);
//        cookie.setHttpOnly(true);
//        cookie.setSecure(true);
//        response.addCookie(cookie);
//
//        // 응답 바디에 토큰 세팅
//        Map<String,String> tokens = new HashMap<>();
//        tokens.put("access_token", accessToken);
//        tokens.put("refresh_token", refreshToken);
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        //response.getWriter().println(tokens);
//
//        new ObjectMapper().writeValue(response.getOutputStream(),tokens);
//
//        // 응답헤더에 토큰 세팅
//        //response.setHeader("access_token",accessToken);
//        //response.setHeader("refresh_token",refreshToken);
//
//    }
}
