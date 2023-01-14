package fmi.softech.topkino;

import fmi.softech.topkino.services.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private UserAuthService authProvider;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/users").permitAll()
                .requestMatchers(HttpMethod.POST, "/users/authorized").permitAll()
                .requestMatchers(HttpMethod.GET, "/movies", "/rooms", "/projections").permitAll()
                .requestMatchers(HttpMethod.GET, "/movies/**", "/rooms/**", "/projections/**").permitAll()
                .requestMatchers(HttpMethod.PUT, "/users/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/movies", "/rooms", "/projections").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/movies/**", "/rooms/**", "/projections/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/movies/**", "/rooms/**", "/projections/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/users/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN")
                .anyRequest()
                .authenticated()
            .and()
                .cors()
            .and()
                .csrf()
                .disable()
            .httpBasic();
        return http.build();
    }
}