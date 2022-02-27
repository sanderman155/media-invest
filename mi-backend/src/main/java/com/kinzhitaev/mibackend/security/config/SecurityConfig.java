package com.kinzhitaev.mibackend.security.config;

import com.kinzhitaev.mibackend.security.config.jwt.AuthEntryPointJwt;
import com.kinzhitaev.mibackend.security.config.jwt.JwtConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    AuthEntryPointJwt unauthorizedHandler;

    @Autowired
    JwtConfigurer jwtConfigurer;

    @Override
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                cors().and().
                csrf().disable().
                exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and().
                sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
                and().
//                addFilterBefore(jwtAuthTokenFilter(), UsernamePasswordAuthenticationFilter.class).
//                authorizeRequests().
//                antMatchers("/api/v1/auth/**").permitAll().
//                anyRequest().
//                authenticated();
                authorizeRequests().
                    antMatchers("/api/v1/auth/**").permitAll().
                    antMatchers("/api/test/**").permitAll().
                    anyRequest().authenticated().
                and().
                apply(jwtConfigurer);
        http.headers().cacheControl();
    }
}



