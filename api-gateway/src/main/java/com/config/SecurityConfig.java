// package com.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
// import org.springframework.security.config.web.server.ServerHttpSecurity;
// import org.springframework.security.web.server.SecurityWebFilterChain;
// import org.springframework.web.cors.CorsConfiguration;
// import org.springframework.web.cors.reactive.CorsConfigurationSource;
// import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

// import java.util.Arrays;

// @Configuration
// @EnableWebFluxSecurity
// public class GatewaySecurityConfig {

//     @Bean
//     public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
//         http
//             .csrf(ServerHttpSecurity.CsrfSpec::disable)
//             .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
//             .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
//             .authorizeExchange(exchanges -> exchanges

//                 // 🔓 All known API routes are allowed past Spring Security
//                 // Auth & products
//                 .pathMatchers("/api/v1/auth/**").permitAll()
//                 .pathMatchers("/api/v1/products/**").permitAll()

//                 // Cart, orders, payments: auth handled by JwtAuthenticationFilter (via Authorization header)
//                 .pathMatchers("/api/v1/cart/**").permitAll()
//                 .pathMatchers("/api/v1/orders/**").permitAll()
//                 .pathMatchers("/api/v1/payments/**").permitAll()
//                 .pathMatchers("/api/v1/shipping-addresses/**").permitAll()	
//                 // Admin APIs (we will still rely on JwtAuthenticationFilter to check role)
//                 .pathMatchers("/admin/api/v1/**").permitAll()

//                 // Anything else → blocked
//                 .anyExchange().denyAll()
//             );

//         return http.build();
//     }

//     @Bean
//     public CorsConfigurationSource corsConfigurationSource() {
//         CorsConfiguration config = new CorsConfiguration();
//         //config.setAllowedOrigins(Arrays.asList("https://e-commerce-project-frontend-xi.vercel.app"));
//         config.setAllowedOrigins(Arrays.asList("http://localhost:3000"));

//         config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//         config.setAllowedHeaders(Arrays.asList("*"));
//         config.setExposedHeaders(Arrays.asList("X-USER-ID", "X-USER-ROLE"));
//         config.setAllowCredentials(true);

//         UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//         source.registerCorsConfiguration("/**", config);
//         return source;
//     }
// }

package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@Order(-1)
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/ws-notifications/**").permitAll() // ✅ allow websocket
                        .anyExchange().permitAll() // JWT filter will handle auth
                )
                .build();
    }
}
