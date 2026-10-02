# Épica 4: Catálogo de productos y solicitudes de cotización

API REST construida con Java 21, Spring Boot, Spring Data JPA y H2.

## Ramas y flujo de trabajo

- `main` contiene la base funcional y estable del proyecto.
- `registrar` contiene la historia de registro de productos.
- `consultar` contiene la historia de consulta del catálogo.
- `developer` se conserva como rama de integración existente.
- El workflow de GitHub Actions ejecuta compilación y pruebas en cada push y Pull Request hacia `main`, `developer`, `registrar` o `consultar`.
- No hay merges automáticos: integra cambios solo después de revisar las ramas y recibir autorización.

## Requisitos

- JDK 21

## Ejecutar pruebas

```bash
./mvnw --batch-mode verify
```

En Windows:

```powershell
.\mvnw.cmd --batch-mode verify
```

## Ejecutar la aplicación

```powershell
.\mvnw.cmd spring-boot:run
```

La base H2 en memoria se inicializa al arrancar. El endpoint de estado es `GET /api/health`.

## Historia: registrar productos

- `POST /api/products`: registra un producto (`name`, `description`, `category`, `basePrice`).

La creación responde `201 Created`; los datos inválidos responden `400 Bad Request`.
