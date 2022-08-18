package com.warehouse.workout.config.security.filter;

import com.warehouse.workout.config.constant.UrlPath;
import com.warehouse.workout.config.security.common.TokenCommon;
import com.warehouse.workout.user.entity.UserEntity;
import com.warehouse.workout.user.entity.UserRefreshTokenEntity;
import com.warehouse.workout.user.repository.UserRefreshTokenRepository;
import com.warehouse.workout.user.repository.UserRepository;
import com.warehouse.workout.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@RequiredArgsConstructor
public class JsonWebTokenFilter extends OncePerRequestFilter {

    private final UserRefreshTokenRepository userRefreshTokenRepository;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 사용자 정보를 얻는다.
        String username = "";

        try {
            ServletInputStream inputStream = request.getInputStream();
            String msgBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            JSONObject root = new JSONObject(msgBody);

            username = root.get("username").toString();
        } catch (IOException e) {
            e.printStackTrace();
        }


        // 토큰 Refresh 요청
        if(request.getServletPath().equals(UrlPath.TOKEN_REFRESH_URL) && HttpMethod.GET.matches(request.getMethod())){


            UserEntity userEntity = userRepository.findByusername(username);
            // Refresh Token이 만료되기 전이면 Access Token을 새로 만들어서 발급한다.
            UserRefreshTokenEntity userRefreshTokenEntity =
                    userRefreshTokenRepository.findUserRefreshTokenEntityByUser(userEntity);

            if(userRefreshTokenEntity != null && !userRefreshTokenEntity.isNowExpired()){

                String accessToken = TokenCommon.publishAccessToken(username, request.getServletPath(),
                        userEntity.getRoles().stream().map(userRoleEntity -> userRoleEntity.getUserRoleCode().toString()).collect(Collectors.toList()));
                // TODO - 새롭게 만든 accessToken을 Response에 담아 반환한다


            } else { // 만료된 Refresh Token
                // TODO - 리다이렉트 Response 반환

            }

        } else{
            // TODO - Access Token이 만료되지 않았는지 검사한다. 만료 되었다면 토큰 만료 메시지를 Response에 담아 보낸다.

        }

    }



}
