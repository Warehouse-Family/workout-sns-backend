package com.warehouse.workout.config.security;

import com.warehouse.workout.config.security.filter.CustomAuthorizationFilter;
import com.warehouse.workout.config.security.filter.CustomAuthenticationFilter;
import com.warehouse.workout.config.security.handler.CustomAuthenticationFailureHandler;
import com.warehouse.workout.user.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
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

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // /api/login 요청을 처리하기 위한 필터를 생성한다
        CustomAuthenticationFilter customAuthenticationFilter =
                new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/api/v1/login"); // 로그인 요청 API
        customAuthenticationFilter.setAuthenticationFailureHandler(customAuthenticationFailureHandler);

        http.csrf().disable();
        http.authorizeRequests().antMatchers("/api/v1/login/**","/api/v1/token/refresh").permitAll();

//        http.authorizeRequests().antMatchers("/api/v1/**").permitAll();
//        http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/v1/user/**").hasAnyAuthority("ROLE_USER");
//        http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/v1/user/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(customAuthenticationFilter); // http 요청에 filter를 적용한다.

        // UsernamePasswordAuthenticationFilter filter에 앞서 직접 구현한 CustomAuthorizationFilter 적용
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);



    }

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}