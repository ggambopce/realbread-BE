package com.jino.realbread.global.security.config;

import com.jino.realbread.global.handler.OAuth2SuccessHandler;
import com.jino.realbread.global.security.filter.JwtAuthenticationFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Configurable
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

        private final JwtAuthenticationFilter jwtAuthenticationFilter;
        private final DefaultOAuth2UserService oAuth2UserService;
        private final OAuth2SuccessHandler oAuth2SuccessHandler;

        @Bean
        protected SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {

                httpSecurity
                                .csrf(csrf -> csrf.disable())
                                .cors(cors -> {
                                }) // default cors 설정
                                .httpBasic(httpBasic -> httpBasic.disable())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/api/edu-bot/counsel/**", "/api/promptchat",
                                                                "/api/chat",
                                                                "/api/test", "/api/test/client-chat",
                                                                "/api/crawler/menus")
                                                .permitAll()
                                                .requestMatchers("/", "/api/auth/**", "/api/search/**", "/oauth2/**",
                                                                "/api/bakery/**", "/api/bakery/search-list/**",
                                                                "/file/**")
                                                .permitAll()
                                                .requestMatchers(HttpMethod.GET, "/api/bakery/**", "/api/user/*",
                                                                "/api/search/**")
                                                .permitAll()
                                                .requestMatchers("/api/admin/edubot/**").hasRole("ADMIN")
                                                .anyRequest().authenticated())
                                .oauth2Login(oauth2 -> oauth2
                                                .authorizationEndpoint(endponit -> endponit.baseUri("/api/auth/oauth2"))
                                                .redirectionEndpoint(endpoint -> endpoint
                                                                .baseUri("/realbread/login/oauth2/code/kakao"))
                                                .userInfoEndpoint(endpoint -> endpoint.userService(oAuth2UserService))
                                                .successHandler(oAuth2SuccessHandler))
                                .exceptionHandling(exception -> exception
                                                .authenticationEntryPoint(new FailedAuthenticationEntryPoint()))
                                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

                return httpSecurity.build();

        }

}

class FailedAuthenticationEntryPoint implements AuthenticationEntryPoint {

        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response,
                        AuthenticationException authException) throws IOException, ServletException {

                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{ \"code\": \"AF\", \"message\": \"Authorization Failed. \" }");
        }
}