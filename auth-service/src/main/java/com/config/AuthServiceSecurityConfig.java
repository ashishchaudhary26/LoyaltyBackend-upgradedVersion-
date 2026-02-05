package com.config;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
// import org.springframework.security.config.web.server.ServerHttpSecurity;
// import org.springframework.security.web.server.SecurityWebFilterChain;


// @Configuration
// //@EnableWebFluxSecurity
// public class AuthServiceSecurityConfig {

//     @Bean
//     public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
//         http
//             .csrf(csrf -> csrf.disable())
//             .httpBasic(httpBasic -> httpBasic.disable())
//             .formLogin(formLogin -> formLogin.disable())
//             .authorizeExchange(exchanges -> exchanges
//                 .pathMatchers("/api/v1/auth/**").permitAll()
//                 .anyExchange().authenticated()
//             );

//         return http.build();
//     }
// }

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class AuthServiceSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf().disable()
            .formLogin().disable() 
            .httpBasic().disable()  
            .authorizeRequests()
            .antMatchers("/api/v1/auth/login",
             "/api/v1/auth/register",
             "/api/v1/auth/logout",
             "/api/v1/auth/token/blacklisted").permitAll()
            .anyRequest().authenticated();

        return http.build();
    }
}
