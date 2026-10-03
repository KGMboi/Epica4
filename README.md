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

La creación responde `201 Created`; los datos inválidos responden `400 Bad Request`. El nombre, la descripción y la categoría son obligatorios. El precio debe ser positivo, tener como máximo 10 dígitos enteros y 2 decimales.

### Prueba manual con Postman

1. Desde la carpeta del proyecto, inicia la API con `.\mvnw.cmd spring-boot:run`.
2. En Postman, importa `postman/epica4-product-registration.postman_collection.json`.
3. Confirma que la variable `baseUrl` de la colección apunte a `http://localhost:8080`.
4. Ejecuta `Registrar producto valido`; debe responder `201 Created` con un ID y los datos del producto.
5. Ejecuta `Rechazar producto con precio cero`; debe responder `400 Bad Request`.

Cada solicitud incluye pruebas que Postman muestra en la pestaña **Test Results**.
