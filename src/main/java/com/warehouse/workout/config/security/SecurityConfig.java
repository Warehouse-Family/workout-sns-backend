package com.warehouse.workout.config.security;

import com.warehouse.workout.config.constant.UrlPath;
import com.warehouse.workout.config.security.filter.CustomAuthorizationFilter;
import com.warehouse.workout.config.security.filter.CustomAuthenticationFilter;

import com.warehouse.workout.config.security.filter.JsonWebTokenFilter;
import com.warehouse.workout.config.security.handler.CustomAuthenticationFailureHandler;
import com.warehouse.workout.config.security.handler.CustomAuthenticationSuccessHandler;
import com.warehouse.workout.user.repository.UserRefreshTokenRepository;
import com.warehouse.workout.user.repository.UserRepository;
import com.warehouse.workout.user.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final PasswordEncoder passwordEncoder;

    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    private final UserRefreshTokenRepository userRefreshTokenRepository;
    private final UserRepository userRepository;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsServiceImpl);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);

        auth.authenticationProvider(daoAuthenticationProvider);
    }




    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http.csrf().disable();
        http.authorizeRequests().antMatchers(UrlPath.LOGIN_URL, UrlPath.TOKEN_REFRESH_URL).permitAll();
        http.authorizeRequests().anyRequest().authenticated();


        // 필터 생성 및 순서 부여
        CustomAuthenticationFilter customAuthenticationFilter =
                new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl(UrlPath.LOGIN_URL); // 로그인 요청 API
        customAuthenticationFilter.setAuthenticationFailureHandler(customAuthenticationFailureHandler);
        customAuthenticationFilter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);

        // AuthenticationFilter -> JsonWebTokenFilter -> AuthorizationFilter
        http.addFilter(new JsonWebTokenFilter(userRefreshTokenRepository ,userRepository));
        http.addFilterBefore(customAuthenticationFilter, JsonWebTokenFilter.class);
        http.addFilterAfter(new CustomAuthorizationFilter(),JsonWebTokenFilter.class);

    }

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}