# API de naves - Prueba Técnica Spring Boot

Este proyecto es una API construida con **Spring Boot** que permite gestionar naves espaciales de series y películas. La API soporta operaciones CRUD (Crear, Leer, Actualizar y Eliminar) sobre una base de datos de naves espaciales. Además, incluye funcionalidades adicionales como paginación, búsquedas personalizadas, manejo centralizado de excepciones, y caché.

## Contenidos

- [Requisitos](#requisitos)
- [Características del Proyecto](#características-del-proyecto)
  - [Funcionalidades Obligatorias](#funcionalidades-obligatorias)
  - [Mejoras Opcionales](#mejoras-opcionales)
- [Instrucciones de Instalación](#instrucciones-de-instalación)
- [Ejecución y Uso](#ejecución-y-uso)

## Requisitos

- **Docker**
- **Docker Compose**

## Características del Proyecto

### Funcionalidades Obligatorias

- Se ha realizado el crud de naves tal como se solicitó. Para probar los endpoint puedes acceder a la ruta `/swagger-ui/index.html#/` o utilizar [la librería de postman](./postman_collection.json)
- **Test unitarios**: Se han realizado pruebas en los servicios de [usuario](./src/test/java/com/ivan/naves/service/usuario/UsuarioServiceTest.java) y [nave](./src/test/java/com/ivan/naves/service/nave/NaveServiceTest.java) que, aunque no proporcionan una cobertura completa de la aplicación cubren los puntos centrales de la misma.
- **Aspecto para logs en IDs negativos**: [Implementado aquí](./src/main/java/com/ivan/naves/aspect/LoggingAspect.java).
- **Manejo centralizado de excepciones**: [Implementado aquí](./src/main/java/com/ivan/naves/advice/ExceptionControllerAdvice.java).
- **Uso de caché**: Se ha implementado caché en el método delete del [NaveService](./src/main/java/com/ivan/naves/service/nave/NaveService.java). Aunque no es de gran utilidad, podría tenerla por ejemplo para evitar lanzar un error en caso de que el cliente llame dos veces seguidas al endpoint.

### Mejoras Opcionales

- **Gestión de scripts DDL**: Se ha utilizado `Flyway` que, aunque no se utiliza en este caso para los scripts DDL ya que la gestión de la base de datos la realiza JPA, podría utilizarse para este propósito. En este caso se está utilizando para la ejecución de ciertos scripts que nutren de datos la aplicación al inicio y de cara a tener datos coherentes para los test.
- **Test de integración**: Se ha realizado una prueba de integración para comprobar que la integración con kafka sea correcta en el [NaveServiceTest](./src/test/java/com/ivan/naves/service/nave/NaveServiceTest.java). Además se prueba también en todos los tests la integración entre servicio, repositorio y aplicación de forma completa ya que estamos levantando todo el contexto de Spring en cada uno de ellos.
- **Dockerización**: El proyecto está construido con `Docker` y `Docker Compose`.
- **Documentación de la API**: La API está documentada utilizando `Swagger`. Se puede acceder a esta documentación en la ruta `/swagger-ui/index.html#/`.
- **Seguridad en la API**: Se ha implementado autenticación utilizando `JWT` y añadiendo un pequeño sistema de usuarios.
- **Mensajería con Kafka**: Se ha añadido un producer que envía los datos de creación de nuevas naves a un topic.

## Instrucciones de Instalación

```bash
git clone https://github.com/ivannavas/naves.git
cd naves
```

```bash
mvn clean install
```

```bash
docker build -t naves .
```

```bash
docker-compose up
```

## Ejecución y Uso

- La aplicación corre en el puerto `8080`.
- Utiliza la base de datos H2 en memoria para almacenar las naves espaciales.
- Para una mejor experiencia se recomienda importar [la librería de postman](./postman_collection.json)
- Para poder utilizar la api necesitas obtener un bearer token con el endpoint `(POST) /api/usuario/login`. Puedes utilizar el usuario por defecto (nombre: admin / contraseña: admin) o crear uno con `(POST) /api/usuario`.
