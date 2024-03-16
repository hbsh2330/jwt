package com.hbsh.jwt.config;

import com.hbsh.jwt.filter.MyFilter;
import com.hbsh.jwt.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Autowired
    private CorsFilter corsFilter;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.addFilterBefore(new MyFilter(), SecurityContextPersistenceFilter.class);
        http.csrf(CsrfConfigurer::disable);
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // 세션을 사용하지 않는다.

        http.addFilter(corsFilter); // 인증이 있을 경우 filter로 넘김

        http.formLogin(
                login -> login.disable() // 폼태그 로그인을 안한다.
        );
        http.httpBasic(
                basic -> {
                    basic
                            .disable() // 기본인증방식 해더에 유저의 ID,PW를 달고감
                            .addFilter(new JwtAuthenticationFilter()); // AuthenticationManger
                }
        );
        http.authorizeHttpRequests(
                authorize -> {
                    authorize
                            .requestMatchers("/api/v1/user/**").hasAnyRole("USER", "MANAGER", "ADMIN")
                            .requestMatchers("/api/v1/manager/**").hasAnyRole( "MANAGER", "ADMIN")
                            .requestMatchers("/api/v1/admin/**").hasAnyRole("ADMIN")
                            .anyRequest().permitAll();
                }
        );


        return http.build();
    }
}
