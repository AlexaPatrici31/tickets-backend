# UNIVERSIDAD DE CARTAGENA
## INGENIERÍA DE SOFTWARE
### TRABAJO COLABORATIVO CONTEXTUALIZADO

---

# Sistema de Gestión Comunitaria de Casos, Incidencias y Servicios
## TICKETS — Backend

---

## Descripción
TICKETS es una plataforma de gestión comunitaria orientada al registro, seguimiento y control de casos, incidencias y servicios dentro de una comunidad. El backend expone una API REST que permite administrar usuarios, responsables, comunidades, localizaciones, clasificaciones de incidencias, estados de caso, chats de seguimiento, historial de cambios y reportes del sistema.

La aplicación está desarrollada con Spring Boot y PostgreSQL, bajo una arquitectura por capas que favorece la organización del código, la mantenibilidad y la escalabilidad. Además, el modelo de datos contempla relaciones jerárquicas en módulos como localizaciones y categorías de incidencias, junto con trazabilidad completa de los cambios de estado y actividad asociada a cada caso.

---

## Características
- API REST organizada por módulos funcionales.
- Gestión de usuarios con roles y permisos diferenciados.
- Administración de comunidades y localizaciones jerárquicas.
- Creación y seguimiento de casos de tipo incidencia y servicio comunitario.
- Asignación de responsables y control de carga operativa.
- Chat por caso con mensajes públicos, internos y eventos del sistema.
- Historial de cambios para trazabilidad de estados y acciones.
- Módulo de reportes con métricas operativas y dashboard comunitario.

---

## Tecnologías
- Java 17
- Spring Boot
- PostgreSQL
- Maven
- Spring Security + JWT
- Spring Data JPA
- Swagger / OpenAPI

---

## Dependencias
Spring Boot Starters:
- spring-boot-starter-data-jpa
- spring-boot-starter-web
- spring-boot-starter-security
- spring-boot-starter-test

Base de datos:
- PostgreSQL Driver

Autenticación y JWT:
- jjwt-api 0.11.5
- jjwt-impl 0.11.5
- jjwt-jackson 0.11.5

Validación y documentación:
- hibernate-validator
- springdoc-openapi-starter-webmvc-ui

Utilidades:
- lombok
- spring-security-test

---

## Requisitos
- Java 17
- PostgreSQL
- Maven

---

## Cómo empezar

1. Clonar repositorio:
   git clone git@github.com:AlexaPatrici31/tickets-backend.git

2. Crear base de datos:
   CREATE DATABASE tickets;

3. Configurar application.properties:
   spring.datasource.url=jdbc:postgresql://localhost:5432/tickets
   spring.datasource.username=tu_usuario
   spring.datasource.password=tu_contraseña

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

server.port=8080

4. Ejecutar:
   mvn spring-boot:run

5. Swagger:
   http://localhost:8080/swagger-ui/index.html

---

## Arquitectura
El backend sigue una arquitectura por capas, donde cada capa cumple una responsabilidad específica y se comunica de forma controlada con las capas adyacentes.

Capas:
- Controllers: Manejan solicitudes HTTP
- Services: Lógica de negocio
- Repositories: Acceso a datos
- Entities: Modelos persistentes
- DTOs: Transporte de datos

Principio aplicado:
- Responsabilidad única (SRP)

---

## Roles del sistema

| Rol | Descripción |
|---|---|
| ADMINGENERAL | Administra todo el sistema, crea comunidades y usuarios |
| ADMINCOMUNIDAD | Administra únicamente su comunidad asignada |
| RESPONSABLE | Atiende y gestiona los casos asignados |
| USUARIOGENERAL | Ciudadano que reporta incidencias y solicita servicios |

---

## Módulos funcionales

| Módulo | Endpoints |
|---|-----------:|
| Usuario | 23 |
| Auth | 8 |
| Comunidad | 15 |
| Responsable | 12 |
| ResponsableUsuario | 5 |
| Localización | 13 |
| CategoríaIncidencia | 12 |
| EstadoCaso | 11 |
| Caso | 24 |
| Incidencia | 15 |
| ServicioComunitario | 18 |
| ChatCaso | 13 |
| HistorialCambioEstado | 10 |
| Reporte | 16 |

Resumen:
- Usuario y Auth: autenticación y gestión
- Comunidad y responsables: administración operativa
- Localización y categorías: estructuras jerárquicas
- Casos e incidencias: núcleo funcional
- Chat e historial: seguimiento
- Reportes: métricas y dashboard

---

## Reglas de negocio
- ADMINCOMUNIDAD no accede a otras comunidades
- USUARIOGENERAL solo ve sus casos
- RESPONSABLE solo gestiona sus asignaciones
- Todo cambio genera historial y evento en chat
- Mensajes internos no visibles para usuario general
- bootstrap-admin se bloquea automáticamente
- Se prioriza eliminación lógica

---

## Modelo de datos
Incluye usuarios, comunidades, responsables, localizaciones, categorías, estados, casos, incidencias, servicios, chats e historial.

Relaciones clave:
- Jerarquía de localizaciones
- Jerarquía de categorías
- Especialización de caso
- Asociación de chat e historial

---

## Endpoints destacados
- POST /api/v1/usuario/bootstrap-admin
- POST /api/v1/auth/login
- POST /api/v1/incidencia/crear
- POST /api/v1/servicio-comunitario/crear
- PATCH /api/v1/caso/actualizar/{id}/estado
- POST /api/v1/chat-caso/crear/{idCaso}
- GET /api/v1/reporte/comunidad/{id}/dashboard

---

## Documentación API
http://localhost:8080/swagger-ui/index.html

---

## Orden de implementación
Usuarios → Auth → Comunidad → Responsables → Catálogos → Casos → Especializaciones → Chat → Historial → Reportes

---

## Información adicional
Este backend es el núcleo del sistema TICKETS, integrable con frontend móvil, con JWT, control por roles, trazabilidad y análisis de datos.

---

## Créditos
Proyecto desarrollado como parte del Trabajo Colaborativo Contextualizado del Programa de Ingeniería de Software de la Universidad de Cartagena.