package com.ezen.spring.config;

import com.ezen.spring.security.CustomUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    /* springSecurity6 에서는 bcEncoder가 createDelegatingPasswordEncoder로 바뀜 */

    @Bean
    PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // SecurityFilterChain 객체로 설정
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http.csrf(csrf-> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize.requestMatchers("/user/list").hasAnyRole("ADMIN")
                        .requestMatchers("/", "/index", "/dist/**", "/js/**", "/css/**",
                                "/upload/**", "/user/login", "/user/register",
                                "/board/list", "/board/detail", "/comment/list/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                ).formLogin(login -> login
                        .usernameParameter("email")
                        .passwordParameter("pwd")
                        .loginPage("/user/login")
                        .defaultSuccessUrl("/board/list").permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/user/logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/")
                )
                .build();
    }

    // userDetailsService : Spring에서 만든 클래스와 같은 객체
    @Bean
    UserDetailsService userDetailsService(){
        return new CustomUserService();
    }

    // authenticationManager 객체
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


}
