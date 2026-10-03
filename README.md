# Épica 4: catálogo de productos y solicitudes de cotización

API REST construida con Java 21, Spring Boot, Spring Data JPA y H2.

## Alcance implementado

- **Registrar productos:** un administrador crea productos con nombre, descripción, categoría y precio base.
- **Consultar el catálogo:** un cliente obtiene todos los productos, ordenados por ID ascendente.
- **Registrar solicitudes de cotización:** un cliente potencial solicita una cotización de un producto existente.

## Ramas y flujo de trabajo

- `main`: base estable del proyecto.
- `registrar`: historia de alta de productos.
- `consultasBranch`: historia de consulta del catálogo, desarrollada por Kevin.
- `QA`: integra las historias de registro y consulta, con pruebas automatizadas conjuntas.
- `developer`: integra las historias revisadas y el registro de solicitudes de cotización.
- No hay merges automáticos. La integración a otras ramas requiere revisión y autorización.

GitHub Actions ejecuta compilación y pruebas al hacer push o abrir un Pull Request hacia `main`, `developer`, `registrar`, `consultasBranch` o `QA`.

## Requisitos y ejecución

Se requiere JDK 21. La aplicación usa H2 en memoria: los datos se pierden al detenerla. Al iniciar, queda disponible en `http://localhost:8080`; la consola H2 está en `/h2-console`.

En Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

En Linux o macOS:

```bash
./mvnw spring-boot:run
```

## API

### Estado del servicio

`GET /api/health` responde `200 OK` con `{"status":"UP"}`.

### Registrar un producto

`POST /api/products` recibe:

```json
{
  "name": "Teclado mecanico",
  "description": "Teclado USB para oficina",
  "category": "Computo",
  "basePrice": 899.90
}
```

Responde `201 Created` con el producto y su ID. Nombre, descripción y categoría son obligatorios. El precio debe ser positivo, con un máximo de 10 dígitos enteros y 2 decimales. Los datos inválidos responden `400 Bad Request`.

### Consultar el catálogo

`GET /api/products` responde `200 OK` con la lista completa ordenada por ID ascendente. Si no hay productos, devuelve `[]`. Cada elemento incluye `id`, `name`, `description`, `category` y `basePrice`.

### Registrar una solicitud de cotización

`POST /api/quotes` recibe `productId`, `customerName`, `customerEmail`, `quantity` y `details` opcional. El producto debe existir, el correo debe ser válido y la cantidad debe ser positiva. Una solicitud creada queda en estado `PENDING` y responde `201 Created`; datos inválidos responden `400 Bad Request` y un producto inexistente responde `404 Not Found`.

## Pruebas automatizadas

Desde la raíz del proyecto:

En Windows:

```powershell
.\mvnw.cmd --batch-mode --no-transfer-progress verify
```

En Linux o macOS:

```bash
./mvnw --batch-mode --no-transfer-progress verify
```

La suite cubre el estado de salud; creación y validaciones de productos; catálogo vacío, completo y ordenado; y el flujo integrado que registra un producto y luego lo consulta.

## Pruebas manuales con Postman

1. Inicia la aplicación.
2. Importa `postman/epica4-product-registration.postman_collection.json`.
3. Confirma que `baseUrl` sea `http://localhost:8080`.
4. Ejecuta **Registrar producto valido**; espera `201 Created`.
5. Ejecuta **Rechazar producto con precio cero**; espera `400 Bad Request`.
6. Ejecuta **Consultar catalogo**; espera `200 OK` y una lista ordenada por ID.

Las aserciones aparecen en **Test Results**.
