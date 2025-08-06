![LoopTalk Logo](./assets/looptalk-banner.svg)

<div align="center">

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.4-green?style=for-the-badge&logo=spring-boot)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=for-the-badge&logo=mysql)
![Maven](https://img.shields.io/badge/Maven-3.8+-red?style=for-the-badge&logo=apache-maven)
![JWT](https://img.shields.io/badge/JWT-Auth0-yellow?style=for-the-badge&logo=json-web-tokens)

**Una plataforma de foros y discusiones construida con Spring Boot**

[Características](#-características) • [Tecnologías](#-tecnologías) • [Instalación](#-instalación) • [API](#-api) • [Contribuir](#-contribuir)

</div>

---

## 📋 Descripción

LoopTalk es una API REST robusta y escalable para una plataforma de foros y discusiones. Permite a los usuarios crear tópicos, responder a discusiones, gestionar cursos y mantener un sistema de autenticación seguro con JWT.

### 🎯 Funcionalidades Principales

- **🔐 Autenticación JWT**: Sistema seguro de login y registro de usuarios
- **💬 Gestión de Tópicos**: Crear, editar, eliminar y listar tópicos de discusión
- **💭 Sistema de Respuestas**: Responder a tópicos con paginación
- **📚 Gestión de Cursos**: Administrar cursos y categorías
- **👥 Gestión de Usuarios**: Perfiles de usuario y autenticación
- **📖 Documentación API**: Swagger/OpenAPI integrado
- **🛡️ Seguridad**: Spring Security con validaciones robustas

---

## 🛠️ Tecnologías

### Backend
- **Java 17** - Lenguaje de programación
- **Spring Boot 3.5.4** - Framework principal
- **Spring Security** - Autenticación y autorización
- **Spring Data JPA** - Persistencia de datos
- **Spring Validation** - Validación de datos
- **Lombok** - Reducción de código boilerplate

### Base de Datos
- **MySQL 8.0** - Base de datos relacional
- **Flyway** - Migración de base de datos

### Autenticación
- **JWT (Auth0)** - Tokens de autenticación
- **Spring Security** - Framework de seguridad

### Documentación
- **Swagger/OpenAPI 3** - Documentación de API
- **SpringDoc** - Integración de OpenAPI

### Herramientas
- **Maven** - Gestión de dependencias
- **Git** - Control de versiones

---

## 🚀 Instalación

### Prerrequisitos

- Java 17 o superior
- MySQL 8.0 o superior
- Maven 3.8+

### Configuración

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/tu-usuario/looptalk.git
   cd looptalk
   ```

2. **Configurar variables de entorno**
   
   Crea un archivo `.env` en la raíz del proyecto:
   ```env
   HOST_DB=localhost:3306
   NAME_DB=looptalk
   USER_DB=tu_usuario
   PASSWORD_DB=tu_password
   ```

3. **Crear la base de datos**
   ```sql
   CREATE DATABASE looptalk CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```

4. **Ejecutar la aplicación**
   ```bash
   # Compilar y ejecutar
   mvn spring-boot:run
   
   # O compilar primero
   mvn clean install
   java -jar target/looptalk-0.0.1-SNAPSHOT.jar
   ```

5. **Verificar la instalación**
   
   La aplicación estará disponible en:
   - **API**: http://localhost:8080
   - **Swagger UI**: http://localhost:8080/swagger-ui.html

---

## 📚 API

### Endpoints Principales

#### 🔐 Autenticación
```
POST /login          - Iniciar sesión
POST /registro       - Registrar nuevo usuario
```

#### 👥 Usuarios
```
GET    /usuario      - Listar usuarios
GET    /usuario/{id} - Obtener usuario
PUT    /usuario/{id} - Actualizar usuario
DELETE /usuario/{id} - Eliminar usuario
```

#### 💬 Tópicos
```
GET    /topico                    - Listar tópicos
GET    /topico/{id}              - Obtener tópico
POST   /topico                   - Crear tópico
PUT    /topico/{id}              - Actualizar tópico
DELETE /topico/{id}              - Eliminar tópico
GET    /topico/{id}/respuestas   - Obtener respuestas
POST   /topico/{id}/respuestas   - Crear respuesta
```

#### 💭 Respuestas
```
GET    /respuestas               - Listar respuestas
GET    /respuestas/{id}          - Obtener respuesta
PUT    /respuestas/{id}          - Actualizar respuesta
DELETE /respuestas/{id}          - Eliminar respuesta
```

#### 📚 Cursos
```
GET    /curso        - Listar cursos
GET    /curso/{id}   - Obtener curso
POST   /curso        - Crear curso
PUT    /curso/{id}   - Actualizar curso
DELETE /curso/{id}   - Eliminar curso
```

### Autenticación

La API utiliza JWT para la autenticación. Incluye el token en el header de las peticiones:

```
Authorization: Bearer <tu-jwt-token>
```

---

## 🏗️ Estructura del Proyecto

```
src/
├── main/
│   ├── java/com/alura/looptalk/
│   │   ├── controller/          # Controladores REST
│   │   ├── domain/              # Lógica de negocio
│   │   │   ├── usuario/         # Gestión de usuarios
│   │   │   ├── topico/          # Gestión de tópicos
│   │   │   ├── respuesta/       # Gestión de respuestas
│   │   │   └── curso/           # Gestión de cursos
│   │   └── infra/               # Configuración e infraestructura
│   └── resources/
│       ├── db/migration/        # Migraciones de base de datos
│       └── application.properties
└── test/                        # Tests unitarios e integración
```

---


## 📖 Documentación

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

---

## 👨‍💻 Autor

**Miguel Andres Marin**

- 🐙 GitHub: [@Miguel-Andrez-MF](https://github.com/Miguel-Andrez-MF)
- 🔗 [LinkedIn](https://www.linkedin.com/in/miguel-andres-marin-fernandez-044574341)

---

<div align="center">

**⭐ Si te gusta este proyecto, ¡dale una estrella!**

*Made with ❤️ and ☕*

</div> 