package com.github.mrchcat.intershop.config;

import com.github.mrchcat.intershop.enums.Role;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.session.InMemoryReactiveSessionRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.InvalidateLeastUsedServerMaximumSessionsExceededHandler;
import org.springframework.security.web.server.authentication.ServerMaximumSessionsExceededHandler;
import org.springframework.security.web.server.authentication.SessionLimit;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;
import org.springframework.web.server.session.DefaultWebSessionManager;
import org.springframework.web.server.session.WebSessionManager;
import reactor.core.publisher.Mono;

import java.security.SecureRandom;

@Configuration(proxyBeanMethods = false)
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http,
                                                         ServerMaximumSessionsExceededHandler maximumSessionsExceededHandler) {
        return http
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
                .formLogin(login -> login.loginPage("/shop-login"))
//                .logout(Customizer.withDefaults())
                .logout(logout -> logout
                        .logoutSuccessHandler(new ShopLogoutHandler()))
                .headers(headers -> headers.frameOptions(ServerHttpSecurity.HeaderSpec.FrameOptionsSpec::disable))
                .exceptionHandling(handling -> handling
                        .accessDeniedHandler((exchange, denied) ->
                                Mono.error(new AccessDeniedException("Access Denied" + denied.getCause())))
                )
                .build();
    }

    @Bean
    ServerMaximumSessionsExceededHandler maximumSessionsExceededHandler(
            @Qualifier(WebHttpHandlerBuilder.WEB_SESSION_MANAGER_BEAN_NAME) WebSessionManager webSessionManager) {
        return new InvalidateLeastUsedServerMaximumSessionsExceededHandler(
                ((DefaultWebSessionManager) webSessionManager).getSessionStore());
    }

    @Bean
    BCryptPasswordEncoder getEncoder() {
        int strength = 10;
        return new BCryptPasswordEncoder(strength, new SecureRandom());
    }
}


