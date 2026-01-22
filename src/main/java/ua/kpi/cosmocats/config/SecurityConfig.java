package ua.kpi.cosmocats.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ua.kpi.cosmocats.security.ApiKeyAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // +1 бал: Вмикає @PreAuthorize
@Profile("!no-auth")  // Ця конфігурація працює ТІЛЬКИ якщо профіль НЕ "no-auth"
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Вимикаємо CSRF для REST API
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Ніяких сесій, тільки токени
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll() // Дозволяємо H2 консоль
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll() // Дозволяємо Swagger
                        .anyRequest().authenticated() // Все інше — під захист
                )
                // Базова вимога: OAuth2 Resource Server (JWT)
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                // Варіант 1: Додаємо наш API Key фільтр перед стандартним фільтром
                .addFilterBefore(new ApiKeyAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                // Дозвіл для фреймів (потрібно для H2 консолі)
                .headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }
}