package com.alura.looptalk.infra.springdoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearer-key";

        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                )
                .info(new Info()
                        .title("LoopTalk - API de Foro Educativo")
                        .version("v1.0.0")
                        .description("""
                                LoopTalk es una plataforma de foros educativos diseñada para facilitar el intercambio de conocimientos entre estudiantes, profesores y moderadores.
                                
                                ### Funcionalidades principales:
                                - Registro y autenticación de usuarios mediante JWT.
                                - Gestión de tópicos (creación, edición, eliminación).
                                - Sistema de respuestas y participación en discusiones.
                                - Roles de usuario: PROFESOR, ESTUDIANTE y MODERADOR.
                                - Control de acceso basado en roles.
                                
                                Esta API está construida con Java 17, Spring Boot, Spring Security, JPA y documentada con Swagger (OpenAPI 3).
                                
                                """)
                        .contact(new Contact()
                                .name("Equipo Backend - LoopTalk")
                                .url("https://github.com/Miguel-Andrez-MF"))
                        .license(new License()
                                .name("Apache License 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html"))
                );
    }
}
