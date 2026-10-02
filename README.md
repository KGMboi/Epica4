# Épica 4: Catálogo de productos y solicitudes de cotización

API REST construida con Java 21, Spring Boot, Spring Data JPA y H2.

## Ramas y flujo de trabajo

- `main` contiene la base funcional y estable del proyecto.
- `developer` parte de `main` y contiene el desarrollo de la épica.
- Los cambios de `developer` se revisan y validan antes de integrarlos en `main`.
- El workflow de GitHub Actions ejecuta compilación y pruebas en cada push y Pull Request hacia `main` o `developer`.
- No hay merges automáticos: integra `developer` en `main` solo después de revisar los cambios y recibir tu aprobación.

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

## API de la épica

- `POST /api/products`: registra un producto (`name`, `description`, `category`, `basePrice`).
- `GET /api/products`: consulta el catálogo completo.
- `POST /api/quotes`: registra una solicitud (`productId`, `customerName`, `customerEmail`, `quantity` y `details` opcional). Las solicitudes nuevas quedan en estado `PENDING`.

Las operaciones de creación responden `201 Created`; los datos inválidos responden `400 Bad Request` y las referencias a productos inexistentes responden `404 Not Found`.
