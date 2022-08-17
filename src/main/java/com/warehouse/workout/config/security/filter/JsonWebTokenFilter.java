package com.warehouse.workout.config.security.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Transactional
public class JsonWebTokenFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if(request.getServletPath().equals("/api/v1/refresh-token") && HttpMethod.GET.matches(request.getMethod())){
            // TODO - refresh-token을 통해 Access Token을 재발급하는 로직 작성하기. 아래 주석에 따라 작성할것.
            // 사용자 정보를 얻는다.

            // Refresh Token이 만료되기 전이면 Access Token을 새로 만들어서 발급한다.

            // Refresh Token이 만료되었다면 로그인 화면으로 Redirect 하도록 한다.


        } else{
            // TODO - Access Token이 만료되지 않았는지 검사한다. 만료 되었다면 토큰 완료 메시지를 Response에 담아 보낸다.

        }




    }
}
