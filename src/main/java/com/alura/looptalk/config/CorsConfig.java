package com.alura.looptalk.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Cambiado de "/api/**" a "/**" para cubrir todas las rutas
                .allowedOrigins(
                        // Desarrollo local Next.js
                        "http://localhost:3000",
                        "http://localhost:3001",

                        // Desarrollo HTTPS local (Next.js con SSL)
                        "https://localhost:3000",
                        "https://localhost:3001",

                        // URLs típicas de producción Next.js (ajusta según tu dominio)
                        "https://tu-dominio.com",
                        "https://www.tu-dominio.com",

                        // Vercel (plataforma común para Next.js)
                        "https://*.vercel.app"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH", "HEAD")
                .allowedHeaders(
                        "Authorization",
                        "Content-Type",
                        "X-Requested-With",
                        "Accept",
                        "Origin",
                        "Access-Control-Request-Method",
                        "Access-Control-Request-Headers"
                )
                .exposedHeaders(
                        "Access-Control-Allow-Origin",
                        "Access-Control-Allow-Credentials"
                )
                .allowCredentials(true)
                .maxAge(3600); // Cache preflight por 1 hora
    }
}