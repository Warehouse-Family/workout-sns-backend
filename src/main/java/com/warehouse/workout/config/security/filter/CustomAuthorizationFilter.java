package com.warehouse.workout.config.security.filter;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 로그인 또는 토큰 Refresh 요청인 경우 Filter를 통과시킨다
        if(request.getServletPath().equals("/api/login") || request.getServletPath().equals("/api/token/refresh")){
            filterChain.doFilter(request,response);
        } else{ // 검증
            // Request 헤더의 Authorization의 값을 가져온다
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            // authorization이 존재하며, authorization 헤더의 값이 "Bearer"로 시작하면 디코딩 작업 수행
            if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){

                try {
                    String token = authorizationHeader.substring("Bearer ".length());// prefix를 잘라낸다.
                    Algorithm algorithm = Algorithm.HMAC256("secret".getBytes()); // 암호화 알고리즘을 만든다.
                    JWTVerifier verifier = JWT.require(algorithm).build();//JWT 검증 인스턴스를 만든다.
                    DecodedJWT decodedJWT = verifier.verify(token); //Token을 디코딩한다.
                    String username = decodedJWT.getSubject(); // Token에서 username을 가져온다
                    String[] roles = decodedJWT.getClaim("roles").asArray(String.class); //Token에서 역할을 가져온다.
                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    stream(roles).forEach(role -> { // 역할을 SimpleGrantedAuthority형태로 변환한다.
                        authorities.add(new SimpleGrantedAuthority(role));
                    });
                    // username과 password를 사용한 인증방식에서 사용할 UsernamePasswordAuthenticationToken 클래스
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
                    // SecurityContextHolder에 사용자 인증정보를 담아 다음 filter로 넘긴다
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request,response);

                } catch (Exception e){
                    log.error("Error logging in : {}" ,e.getMessage());
                    response.setHeader("error",e.getMessage());
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    Map<String,String> error = new HashMap<>();
                    error.put("error_message" , e.getMessage());
                    response.setContentType(APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(),error);

                }
            } else {
                filterChain.doFilter(request,response);
            }
        }

    }
}
