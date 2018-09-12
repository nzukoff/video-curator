package com.zukoff.security;

import com.zukoff.filters.JwtAuthenticationFilter;
import com.zukoff.filters.JwtLoginFilter;
import com.zukoff.services.UserService;
import com.zukoff.utilities.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = {"com.zukoff.filters", "com.zukoff.utilities", "com.zukoff.services"})
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private JwtUtil jwtUtil;

    @Bean
    public PasswordEncoder bcryptEncoder() {
        System.out.println("WEBSECURITYCONFIG BCRYPTENCODER");
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        System.out.println("WEBSECURITYCONFIG CORS CONFIGURATION SOURCE");
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.addExposedHeader("Authorization");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("WEBSECURITYCONFIG CONFIGURE HTTP");
        http
                .csrf().disable().cors().and()
                .authorizeRequests()
                .antMatchers("/authenticate",
                        "/users",
                        "/comments/{id}",
                        "/comments/{id}/video",
                        "/comments/{id}/user",
                        "/users",
                        "/users/",
                        "/users/{id}",
                        "/users/{id}/comments/count",
                        "/users/{id}/videos",
                        "/users/{id}/videos/count",
                        "/videos/{id}",
                        "/videos",
                        "/videos/",
                        "/videos/{id}/comments",
                        "/videos/{id}/comments/count",
                        "/videos/{id}/user").permitAll()
                .anyRequest().authenticated()
                .and()
                .logout()
                .permitAll();

        http.addFilterBefore(new JwtLoginFilter("/authenticate", jwtUtil, userService, authenticationManager()), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        System.out.println("WEBSECURITYCONFIG CONFIGURE AUTH");
        auth.userDetailsService(userService).passwordEncoder(bcryptEncoder());
    }
}
