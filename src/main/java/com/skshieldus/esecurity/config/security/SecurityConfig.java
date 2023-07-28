package com.skshieldus.esecurity.config.security;

import com.skshieldus.esecurity.common.utils.HttpUtils;
import com.skshieldus.esecurity.model.common.CoEmpDTO;
import com.skshieldus.esecurity.repository.common.AuthRepository;
import java.util.Map;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Slf4j
@Configuration
public class SecurityConfig {

    @Autowired
    private CustomAuthenticationProvider authProvider;

    @Autowired
    private AuthRepository authRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(request ->
                request.antMatchers("/login", "/login/**", "/assets/**").permitAll()
                    .anyRequest().authenticated()
            )
            .formLogin(login -> login
                .loginPage("/login")
                .loginProcessingUrl("/login/process")
                .usernameParameter("id")
                .successHandler(authenticationSuccessHandler())
                .failureHandler(authenticationFailureHandler())
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .addLogoutHandler((request, response, authentication) -> {
                    HttpSession session = request.getSession();
                    if (session != null)
                        session.invalidate();
                })
                .logoutSuccessHandler((request, response, authentication) -> {
                    response.sendRedirect(request.getContextPath() + "/login");
                })
                .deleteCookies("remember-me")
            );
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) ->
            web.ignoring()
                .antMatchers(
                    // -- Static resources
//                    "/assets/**"
                    // -- Swagger UI v3 (Open API)
                    "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html"
                );
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(authProvider);
        return authenticationManagerBuilder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PlainTextPasswordEncoder.getInstance();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            log.debug("login success = {}", authentication);
            HttpSession session = request.getSession();
            Map<String, Object> userInfo = (Map) authentication.getPrincipal();
            session.setAttribute("Login", userInfo);

            log.info("userInfo = {}", userInfo);

            // log 저장
            CoEmpDTO logInfo = new CoEmpDTO();
            logInfo.setId(userInfo.get("ID").toString());
            logInfo.setAcIp(HttpUtils.getClientIP(request));
            logInfo.setSessionId(session.getId());
            authRepository.insertLoginUserLog(logInfo);
            authRepository.insertLoginUserInfo(logInfo);

            response.sendRedirect(request.getContextPath() + "/main");
        };
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, exception) -> {
            log.debug("login fail = {}", exception.getClass().getName());
            response.sendRedirect(request.getContextPath() + "/login?error=" + (exception instanceof UsernameNotFoundException
                ? "FAIL_ID"
                : exception.getMessage()));
        };
    }

}
