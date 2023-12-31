package com.crypto.backend.login;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        return http.authorizeHttpRequests()
//                .requestMatchers(AntPathRequestMatcher.antMatcher("/console/**")).permitAll()
//                .and()
//                .csrf().ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/console/**"))
//                .and()
//                .headers(headers -> headers.frameOptions().sameOrigin())
//                .build();
        return http.authorizeHttpRequests()
                .requestMatchers(AntPathRequestMatcher.antMatcher("/console/**")).permitAll()
                .requestMatchers(AntPathRequestMatcher.antMatcher("/loginn")).permitAll()  // Add this line to permit access to /loginn
                .and()
                .csrf().ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/console/**"))
                .and()
                .headers(headers -> headers.frameOptions().sameOrigin())
                .formLogin() // If you want to enable form-based login
                .loginPage("/loginn") // Specify the login page
                .and()
                .logout() // If you want to enable logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // Specify the logout URL
                .logoutSuccessUrl("/loginn?logout") // Specify the URL to redirect to after logout
                .and()
                .build();
    }

}
