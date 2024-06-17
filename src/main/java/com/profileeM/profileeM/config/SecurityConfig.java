package com.profileeM.profileeM.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/admin/**").authenticated() // /admin/** 경로는 인증 필요
                                .anyRequest().permitAll() // 그 외의 경로는 모두 접근 가능
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login") // 로그인 페이지
                                .defaultSuccessUrl("/admin")
                                .permitAll()
                )
                .logout(logout ->
                        logout
                                .logoutSuccessUrl("/login")
                                .invalidateHttpSession(true)
                                .permitAll()
                )
                .csrf(csrf -> csrf.disable()); // 개발 중이라면 비활성화 가능. 운영 환경에서는 활성화 필요

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
