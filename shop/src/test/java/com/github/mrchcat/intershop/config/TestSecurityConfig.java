package com.github.mrchcat.intershop.config;

import com.github.mrchcat.intershop.enums.Role;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.session.InMemoryReactiveSessionRegistry;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.ServerMaximumSessionsExceededHandler;
import org.springframework.security.web.server.authentication.SessionLimit;
import reactor.core.publisher.Mono;

@TestConfiguration
public class TestSecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http,
                                                         ServerMaximumSessionsExceededHandler maximumSessionsExceededHandler) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/shop-login").permitAll()
                        .pathMatchers("/admin/**").hasRole(Role.ADMIN.roleName)
                        .pathMatchers("/cart/**", "orders/**", "/buy/**").authenticated()
                        .anyExchange().permitAll()
                )
                .sessionManagement((sessions) -> sessions
                        .concurrentSessions((css) -> css
                                .maximumSessions(SessionLimit.of(1))
                                .maximumSessionsExceededHandler(maximumSessionsExceededHandler)
                                .sessionRegistry(new InMemoryReactiveSessionRegistry())
                        ))
                .oauth2Client(Customizer.withDefaults())
                .formLogin(login -> login.loginPage("/shop-login"))
                .logout(logout -> logout
                        .logoutSuccessHandler(new ShopLogoutHandler()))
                .headers(headers -> headers.frameOptions(ServerHttpSecurity.HeaderSpec.FrameOptionsSpec::disable))
                .exceptionHandling(handling -> handling
                        .accessDeniedHandler((exchange, denied) ->
                                Mono.error(new AccessDeniedException("Access Denied" + denied.getCause())))
                )
                .build();
    }
}
