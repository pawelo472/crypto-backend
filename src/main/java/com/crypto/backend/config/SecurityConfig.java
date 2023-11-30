package com.crypto.backend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserAuthenticationEntryPoint userAuthenticationEntryPoint;
    private final UserAuthenticationProvider userAuthenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .exceptionHandling().authenticationEntryPoint(userAuthenticationEntryPoint)
                .and()
                .addFilterBefore(new JwtAuthFilter(userAuthenticationProvider), BasicAuthenticationFilter.class)

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests((requests) -> requests


                        .requestMatchers(HttpMethod.POST, "/login", "/register").permitAll()
                        .requestMatchers("/console/**").permitAll()
                        //.anyRequest().authenticated())
                        .anyRequest().permitAll()
                )

                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions().disable())
        ;
        return http.build();
    }
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest()
//                        .permitAll())
//                .headers(headers -> headers.frameOptions().disable())
//                .csrf(AbstractHttpConfigurer::disable);
//
//        return http.build();
//    }





//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
////        return http.authorizeHttpRequests()
////                .requestMatchers(AntPathRequestMatcher.antMatcher("/console/**")).permitAll()
////                .and()
////                .csrf().ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/console/**"))
////                .and()
////                .headers(headers -> headers.frameOptions().sameOrigin())
////                .build();
//        return http.authorizeHttpRequests()
//                .requestMatchers(AntPathRequestMatcher.antMatcher("/console/**")).permitAll()
//                .requestMatchers(AntPathRequestMatcher.antMatcher("/**")).permitAll()  // Add this line to permit access to /loginn
//                .and()
//                .csrf().ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/console/**"))
//                .and()
//                .headers(headers -> headers.frameOptions().sameOrigin())
//                .formLogin() // If you want to enable form-based login
////                .loginPage("/loginn") // Specify the login page
//                .and()
//                .logout() // If you want to enable logout
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // Specify the logout URL
//                .logoutSuccessUrl("/loginn?logout") // Specify the URL to redirect to after logout
//                .and()
//                .build();
//
//
//    }

}
