package com.alura.looptalk.infra.springdoc;

//@Configuration
//public class SpringDocConfiguration {
//
//    @Bean
//    public OpenAPI customOpenAPI() {
//        return new OpenAPI()
//                .components(new Components()
//                        .addSecuritySchemes("bearer-key",
//                                new SecurityScheme()
//                                        .type(SecurityScheme.Type.HTTP)
//                                        .scheme("bearer")
//                                        .bearerFormat("JWT"))
//                )
//                .info(new Info()
//                        .title("Voll.med API")
//                        .description("API Rest de la aplicación Voll.med, que contiene las funcionalidades CRUD de médicos y de pacientes, además de reserva y cancelamiento de consultas")
//                        .contact(new Contact()
//                                .name("Equipo Backend")
//                                .email("backend@voll.med"))
//                        .license(new License()
//                                .name("Apache 2.0")
//                                .url("http://voll.med/api/licencia")));
//    }
//}