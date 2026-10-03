# Épica 4: catálogo de productos y solicitudes de cotización

API REST para administrar un catálogo de productos, construida con Java 21, Spring Boot, Spring Data JPA y H2.

## Estado del alcance

Esta versión integra y valida las dos historias repartidas:

1. **Registrar productos:** un administrador crea productos con nombre, descripción, categoría y precio base.
2. **Consultar el catálogo:** un cliente obtiene todos los productos registrados.

El registro de solicitudes de cotización descrito en el nombre y la descripción general de la épica todavía no forma parte de estas dos historias implementadas.

## Ramas y colaboración

- `main`: base estable del proyecto.
- `developer`: rama de desarrollo existente; no se modifica al probar esta integración.
- `registrar`: historia de alta de productos.
- `consultasBranch`: historia de consulta del catálogo, desarrollada por Kevin.
- `QA`: reúne `registrar` y `consultasBranch` y ejecuta sus pruebas en conjunto.
- No se realizan merges automáticos. La integración a otras ramas queda sujeta a revisión y autorización.

GitHub Actions ejecuta `mvnw verify` al hacer push o abrir un Pull Request hacia `main`, `developer`, `registrar`, `consultasBranch` o `QA`.

## Requisitos

- JDK 21

## Ejecutar la aplicación

En Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

En Linux o macOS:

```bash
./mvnw spring-boot:run
```

La aplicación inicia en `http://localhost:8080`. Usa H2 en memoria: los datos se pierden al detenerla. La consola H2 está disponible en `/h2-console` durante la ejecución.

## API

### Comprobar estado

`GET /api/health` responde `200 OK`:

```json
{
  "status": "UP"
}
```

### Registrar un producto

`POST /api/products` recibe JSON:

```json
{
  "name": "Teclado mecanico",
  "description": "Teclado USB para oficina",
  "category": "Computo",
  "basePrice": 899.90
}
```

Responde `201 Created` con el producto y su ID. El nombre, la descripción y la categoría son obligatorios. El precio debe ser positivo, tener como máximo 10 dígitos enteros y 2 decimales. Los datos inválidos responden `400 Bad Request`.

Ejemplo con PowerShell:

```powershell
$product = @{
    name = "Teclado mecanico"
    description = "Teclado USB para oficina"
    category = "Computo"
    basePrice = 899.90
} | ConvertTo-Json

Invoke-RestMethod -Method Post `
    -Uri http://localhost:8080/api/products `
    -ContentType "application/json" `
    -Body $product
```

### Consultar el catálogo

`GET /api/products` devuelve `200 OK` con una lista ordenada por ID ascendente. Si todavía no hay productos, responde con `[]`.

Cada elemento incluye `id`, `name`, `description`, `category` y `basePrice`:

```json
[
  {
    "id": 1,
    "name": "Teclado mecanico",
    "description": "Teclado USB para oficina",
    "category": "Computo",
    "basePrice": 899.90
  }
]
```

Ejemplo con PowerShell:

```powershell
Invoke-RestMethod -Method Get -Uri http://localhost:8080/api/products
```

## Pruebas automatizadas

Desde la raíz del proyecto, ejecuta todas las pruebas:

En Windows:

```powershell
.\mvnw.cmd --batch-mode --no-transfer-progress verify
```

En Linux o macOS:

```bash
./mvnw --batch-mode --no-transfer-progress verify
```

La suite cubre:

- Estado de salud de la aplicación.
- Alta de producto con respuesta, ID y campos esperados.
- Recorte de espacios en los campos de texto y validación de campos requeridos.
- Rechazo de precio cero, con más de dos decimales o fuera de la precisión admitida.
- Catálogo vacío y consulta de múltiples productos con todos sus campos.
- Flujo integrado de alta por `POST /api/products` y consulta posterior por `GET /api/products`.

## Pruebas manuales con Postman

1. Inicia la aplicación.
2. Importa `postman/epica4-product-registration.postman_collection.json`.
3. Comprueba que la variable `baseUrl` tenga el valor `http://localhost:8080`.
4. Ejecuta **Registrar producto válido**; espera `201 Created`.
5. Ejecuta **Rechazar producto con precio cero**; espera `400 Bad Request`.
6. Ejecuta **Consultar catálogo** para confirmar la lista, incluido el producto creado.

Los resultados de cada aserción aparecen en **Test Results**.
