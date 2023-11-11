package com.sidof.task.security.config;

import com.sidof.task.security.model.Role;
import com.sidof.task.security.service.LogoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.sidof.task.security.model.Role.ROLE_ADMIN;
import static com.sidof.task.security.model.Role.ROLE_USER;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

/**
 * @Author sidof
 * @Since 10/4/23
 * @Version v1.0
 * @YouTube @sidof8065
 */
@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutService logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authHttpReq -> authHttpReq
                        .requestMatchers("api/v1/task/auth/**")
                        .permitAll()
                        .requestMatchers(GET, "api/v1/task/user/**").hasAnyAuthority(ROLE_ADMIN.name(), Role.ROLE_USER.name())
                        .requestMatchers(PUT, "api/v1/task/user/edit/**").hasAnyAuthority(ROLE_ADMIN.name())
                        .requestMatchers(POST, "api/v1/task/addTask/**").hasAnyAuthority(ROLE_ADMIN.name())
                        .requestMatchers(PUT, "api/v1/task/**").hasAnyAuthority(ROLE_ADMIN.name())
                        .requestMatchers(DELETE, "api/v1/task/**").hasAnyAuthority(ROLE_ADMIN.name())
                        .requestMatchers(GET, "api/v1/task/**").hasAnyAuthority(ROLE_ADMIN.name(), ROLE_USER.name())
                        .anyRequest()
                        .authenticated()
                )
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .logout()
                .logoutUrl("/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                .permitAll();

        return http.build();
    }
}
