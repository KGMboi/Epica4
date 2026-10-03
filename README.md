# Épica 4: Catálogo de productos y solicitudes de cotización

API REST para administrar un catálogo de productos y registrar solicitudes de cotización. El proyecto utiliza Java 21, Spring Boot, Spring Data JPA y H2.

## Equipo

1. Derek Enrique Siqueiros Heredia
2. Jesus Gerardo Ojeda Martínez
3. Daniel Leví Encinas Estrada
4. Kevin García Meza
5. Susana Gallegos Corrales

## Alcance de la épica

- **Registrar productos:** un administrador registra productos con nombre, descripción, categoría y precio base.
- **Consultar productos:** un cliente consulta el catálogo completo.
- **Solicitar una cotización:** un cliente potencial registra una solicitud relacionada con un producto existente.

## Estado por rama

- `main` conserva la base estable del proyecto y el endpoint de salud.
- `registrar` contiene el alta de productos.
- `consultasBranch` contiene la consulta del catálogo, desarrollada por Kevin.
- `QA` reúne y prueba las historias de registro y consulta.
- `developer` integra las historias y la funcionalidad de solicitudes de cotización para su validación.
- La integración entre ramas se realiza con revisión; no se hacen merges automáticos.

El código de las historias de productos y cotizaciones está en las ramas de desarrollo indicadas, no en esta versión base de `main`.

## Requisitos

- JDK 21

La aplicación utiliza una base H2 en memoria, así que sus datos se eliminan al detenerla.

## Ejecutar la aplicación

Desde la carpeta del proyecto:

En Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

En Linux o macOS:

```bash
./mvnw spring-boot:run
```

La aplicación inicia en `http://localhost:8080`. En esta base de `main`, el endpoint disponible es `GET /api/health`, que responde `200 OK`:

```json
{
  "status": "UP"
}
```

La consola de H2 se encuentra en `/h2-console` mientras la aplicación está en ejecución.

## API de las historias de la épica

Los siguientes endpoints están implementados en las ramas de desarrollo descritas arriba; todavía no forman parte del código de `main`.

### Registrar un producto

`POST /api/products`

```json
{
  "name": "Teclado mecanico",
  "description": "Teclado USB para oficina",
  "category": "Computo",
  "basePrice": 899.90
}
```

Responde `201 Created` con el producto registrado y su ID. Nombre, descripción y categoría son obligatorios. El precio debe ser positivo, con hasta 10 dígitos enteros y 2 decimales. Los datos inválidos responden `400 Bad Request`.

### Consultar el catálogo

`GET /api/products` responde `200 OK` con todos los productos, ordenados por ID ascendente. Si no hay productos, devuelve `[]`. Cada producto incluye `id`, `name`, `description`, `category` y `basePrice`.

### Registrar una solicitud de cotización

`POST /api/quotes`

```json
{
  "productId": 1,
  "customerName": "Ana Perez",
  "customerEmail": "ana@example.com",
  "quantity": 3,
  "details": "Entrega en sucursal"
}
```

El producto debe existir, el correo debe ser válido y la cantidad debe ser positiva. `details` es opcional. Una nueva solicitud queda en estado `PENDING` y responde `201 Created`. Los datos inválidos responden `400 Bad Request`; si el producto no existe, responde `404 Not Found`.

## Ejecutar pruebas

Desde la raíz del proyecto:

En Windows:

```powershell
.\mvnw.cmd --batch-mode --no-transfer-progress verify
```

En Linux o macOS:

```bash
./mvnw --batch-mode --no-transfer-progress verify
```

La suite de las ramas de desarrollo cubre el estado de salud, validación y registro de productos, catálogo vacío y ordenado, flujo integrado de registro y consulta, y creación de solicitudes de cotización.

## Integración continua

GitHub Actions ejecuta la compilación y las pruebas con Java 21 en cada push y Pull Request hacia `main` y `developer`. Las ramas de trabajo tienen sus propios filtros de CI en sus respectivas versiones del workflow.
