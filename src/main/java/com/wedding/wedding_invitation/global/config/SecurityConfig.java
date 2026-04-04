package com.wedding.wedding_invitation.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration     // 스프링 설정 파일 Bean 어노테이션 메서드 스프링컨테이너에 등록
@EnableWebSecurity // 스프링시큐리티 기능 활성화 기능 (스위치)
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable()) // CSRF 공격
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()); // 경로 전체허용
        return httpSecurity.build();
    }

}
