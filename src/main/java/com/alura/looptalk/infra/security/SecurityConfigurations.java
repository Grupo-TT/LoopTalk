package com.alura.looptalk.infra.security;

import com.alura.looptalk.infra.exceptions.CustomAccessDeniedHandler;
import com.alura.looptalk.infra.exceptions.CustomAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter;

    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authorizeHttpRequests) ->
                        authorizeHttpRequests

                                // Rutas públicas
                                .requestMatchers(HttpMethod.POST, "/login").permitAll()
                                .requestMatchers(HttpMethod.POST, "/registro").permitAll()
                                .requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()

                                // === Cursos ===
                                .requestMatchers(HttpMethod.GET, "/curso", "/curso/**").hasAnyRole("PROFESOR", "ESTUDIANTE", "MODERADOR")
                                .requestMatchers("/curso/**").hasAnyRole("PROFESOR", "MODERADOR")

                                // === Tópicos (todos pueden hacer todo) ===
                                .requestMatchers("/topico/**").hasAnyRole("PROFESOR", "ESTUDIANTE", "MODERADOR")

                                // === Respuestas (todos pueden hacer todo) ===
                                .requestMatchers("/respuesta/**").hasAnyRole("PROFESOR", "ESTUDIANTE", "MODERADOR")

                                // === Usuarios ===
                                .requestMatchers(HttpMethod.GET, "/usuario", "/usuario/**").hasAnyRole("PROFESOR", "MODERADOR")
                                .requestMatchers("/usuario/**").hasAnyRole("PROFESOR", "ESTUDIANTE", "MODERADOR")

                                // Todo lo demás requiere autenticación
                                .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler)
                )

                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
