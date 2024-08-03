package com.app.test.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final CustomAuthenticationManager customAuthenticationManager;
    private final CustomAuthenticationProvider customAuthenticationProvider;

    public SecurityConfig(final CustomAuthenticationManager customAuthenticationManager, final CustomAuthenticationProvider customAuthenticationProvider) {
        this.customAuthenticationManager = customAuthenticationManager;
        this.customAuthenticationProvider = customAuthenticationProvider;
    }
    
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return (
            httpSecurity
                .authorizeHttpRequests((req) -> {
                    req.requestMatchers("/users/hello").authenticated();
                    req.anyRequest().permitAll();
                })
                .authenticationManager(customAuthenticationManager)
                .authenticationProvider(customAuthenticationProvider)
                .formLogin((login) -> {
                    login.disable();
                })
                .sessionManagement((session) -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .csrf((csrf) -> csrf.disable())
                .httpBasic((basic) -> {
                    basic.disable();
                })
            .build()
        );
    }

}
