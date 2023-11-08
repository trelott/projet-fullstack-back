package org.polytech.covid.config;

import org.polytech.covid.user.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;
    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(
                AbstractHttpConfigurer::disable
        ).securityMatcher("/api/**").authorizeHttpRequests((auth) -> auth
                .requestMatchers("/api/superadmin/**").hasAnyAuthority(Role.SUPERADMIN.name())
                .requestMatchers("/api/admin/**").hasAnyAuthority(Role.SUPERADMIN.name(), Role.ADMIN.name())
                .requestMatchers("/api/user/**").hasAnyAuthority(Role.SUPERADMIN.name(), Role.ADMIN.name(), Role.USER.name())
                .requestMatchers("/api/public/**").permitAll()
        ).sessionManagement(smc -> smc
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ).authenticationProvider(
                authenticationProvider
        ).addFilterBefore(
                jwtAuthFilter, UsernamePasswordAuthenticationFilter.class
        );
        return http.build();
    }

}
