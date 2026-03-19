# UNIVERSIDAD DE CARTAGENA

## INGENIERÍA DE SOFTWARE

### TRABAJO COLABORATIVO CONTEXTUALIZADO

---

# Sistema de Gestión Comunitaria de Casos, Incidencias y Servicios

## TICKETS

---

## Descripción

TICKETS es una plataforma de gestión comunitaria orientada al registro, seguimiento y control de casos, incidencias y servicios dentro de una comunidad. El sistema permite administrar usuarios, responsables, comunidades, localizaciones, clasificaciones de incidencias, estados de caso, chats de seguimiento e historial de cambios, brindando una estructura organizada para atender solicitudes y dar trazabilidad a cada proceso.

La aplicación está desarrollada con Spring Boot y PostgreSQL, bajo una arquitectura por capas que favorece el orden del sistema, la mantenibilidad del código y la escalabilidad del proyecto. Además, su estructura permite representar relaciones jerárquicas en módulos como localizaciones y clasificaciones de incidencias, facilitando una gestión más completa y cercana al contexto real de una comunidad.

---

## Tecnologías

* **Spring Boot**
* **PostgreSQL**
* **Java 17**
* **Maven**

---

## Dependencias

### Spring Boot Starters

* **spring-boot-starter-data-jpa** → Manejo de base de datos con JPA.
* **spring-boot-starter-web** → Construcción de APIs REST.
* **spring-boot-starter-security** → Seguridad y autenticación.
* **spring-boot-starter-test** → Pruebas unitarias e integración.

### Base de datos

* **PostgreSQL Driver** → Conector para PostgreSQL.

### Autenticación y JWT

* **jjwt-api 0.11.5** → API de JWT.
* **jjwt-impl 0.11.5** (runtime) → Implementación de JWT.
* **jjwt-jackson 0.11.5** (runtime) → Serialización y deserialización JSON.

### Validación y documentación

* **hibernate-validator** → Validaciones de datos.
* **springdoc-openapi-starter-webmvc-ui** → Documentación Swagger UI.

### Utilidades

* **lombok** → Anotaciones para reducir código repetitivo.
* **spring-security-test** (test) → Pruebas de seguridad.

---

## Requisitos

* Java 17
* PostgreSQL
* Maven

---

## Cómo empezar

1. Clona este repositorio git@github.com:AlexaPatrici31/tickets-backend.git
2. Configura la conexión a la base de datos en el archivo de configuración `application.properties`.
3. Crea la base de datos correspondiente en PostgreSQL.
4. Ejecuta la aplicación.

---

## Información adicional

Este proyecto utiliza el plugin `spring-boot-maven-plugin` para compilar y empaquetar la aplicación. Su estructura está organizada para facilitar el desarrollo de módulos relacionados con usuarios, comunidades, casos, incidencias, servicios comunitarios, chats e historial de cambios.

---

## Principios de Diseño y Flujo de Trabajo en el Backend

En este proyecto, hemos utilizado uno de los principios SOLID, específicamente el principio de responsabilidad única (SRP). Este principio establece que cada módulo o clase debe tener una única responsabilidad, es decir, debe encargarse de una sola función dentro del sistema. Esto permite construir un código más limpio, entendible y fácil de mantener.

Dividimos el proyecto en varias capas para asegurar que cada una cumpla una función específica dentro del backend:

* **Controladores**: Manejan las solicitudes HTTP y responden con la información necesaria.
* **DTOs (Data Transfer Objects)**: Encapsulan los datos que se transfieren entre las distintas capas de la aplicación.
* **Entidades**: Representan las estructuras persistentes de la base de datos.
* **Repositorios**: Encargados de la interacción con la base de datos.
* **Servicios**: Contienen la lógica de negocio de la aplicación.

---

## Arquitectura por Capas

El sistema TICKETS sigue una arquitectura por capas, lo que proporciona una estructura organizada y modular para el desarrollo de la aplicación. Cada capa tiene una responsabilidad específica y se comunica con las capas adyacentes de manera controlada, lo que promueve la escalabilidad, el mantenimiento y la reutilización del código.

### Capas Principales:

1. **Controladores (Controllers)**:

* Responsabilidad: Manejar las solicitudes HTTP y las respuestas asociadas.
* Interacción: Se comunican con los servicios para realizar operaciones de negocio y devolver los resultados adecuados a los clientes.

2. **Servicios (Services)**:

* Responsabilidad: Contener la lógica de negocio de la aplicación.
* Interacción: Utilizan los repositorios para acceder a los datos persistentes y aplicar las reglas de negocio requeridas.

3. **Repositorios (Repositories)**:

* Responsabilidad: Encargarse de la interacción con la base de datos.
* Interacción: Realizan operaciones de lectura y escritura en la base de datos para almacenar y recuperar la información requerida por la aplicación.

4. **Entidades (Entities)**:

* Responsabilidad: Representar las estructuras de datos persistentes en la base de datos.
* Interacción: Definen los objetos de datos que se almacenan y recuperan de la base de datos, reflejando la estructura del dominio de la aplicación.

5. **DTOs (Data Transfer Objects)**:

* Responsabilidad: Encapsular los datos que se transfieren entre las distintas capas de la aplicación.
* Interacción: Facilitan la comunicación entre los controladores, servicios y repositorios al transportar datos de un lugar a otro de manera eficiente y estructurada.

### Beneficios:

* **Separación de Responsabilidades**: Cada capa tiene una función específica, lo que facilita la comprensión y el mantenimiento del código.
* **Escalabilidad**: La arquitectura por capas permite agregar nuevas funcionalidades de manera modular sin afectar otras partes del sistema.
* **Reutilización del Código**: Los componentes están diseñados para ser independientes y pueden ser reutilizados en diferentes partes de la aplicación o en proyectos futuros.
* **Facilita el Testing**: Las capas separadas permiten una mejor organización de las pruebas unitarias y de integración, lo que facilita la identificación y corrección de errores.

---

## Módulos Funcionales de TICKETS

1. **Gestión de Usuarios** → Permite registrar y administrar la información de los usuarios de la plataforma, incluyendo datos personales, acceso y estado dentro del sistema.
2. **Gestión de Responsables** → Permite controlar los responsables asociados a la atención y seguimiento de procesos dentro de la comunidad.
3. **Gestión de Comunidades** → Permite registrar la información general de cada comunidad, incluyendo nombre, ubicación principal y estado.
4. **Gestión de Localizaciones** → Permite organizar ubicaciones y espacios asociados a cada comunidad, incluyendo estructuras jerárquicas de localización.
5. **Clasificación de Incidencias** → Permite categorizar incidencias en distintos niveles, con jerarquías, descripciones e identificación visual.
6. **Gestión de Estados de Caso** → Permite definir y controlar los estados por los que puede pasar un caso según su tipo.
7. **Gestión de Casos** → Permite crear, consultar, actualizar y dar seguimiento a los casos registrados dentro del sistema.
8. **Gestión de Incidencias** → Permite administrar los casos de tipo incidencia, relacionándolos con categorías, subcategorías y condiciones como el anonimato.
9. **Gestión de Servicios Comunitarios** → Permite registrar y controlar solicitudes de servicios dentro de la comunidad, junto con su programación y ejecución.
10. **Chat de Casos** → Permite mantener comunicación asociada a cada caso mediante mensajes, seguimiento e intercambio de información.
11. **Historial de Cambios** → Permite registrar la trazabilidad de acciones, cambios de estado y responsables involucrados en los diferentes procesos del sistema.

---

## Créditos

Proyecto desarrollado como parte del **Trabajo Colaborativo Contextualizado** del Programa de **Ingeniería de Software** de la **Universidad de Cartagena**.

---