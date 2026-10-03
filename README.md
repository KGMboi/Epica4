# Épica 4: Catálogo de productos y solicitudes de cotización

Equipo:
1.-Derek Enrique Siqueiros Heredia
2.-Jesus Gerardo Ojeda Martínez
3.-Daniel Leví Encinas Estrada
4.-Kevin García Meza
5.-Susana Gallegos Corrales

API REST construida con Java 21, Spring Boot, Spring Data JPA y H2.

## Ramas y flujo de trabajo

- `main` contiene la base funcional y estable del proyecto.
- `developer` parte de `main` y contiene el desarrollo de la épica.
- Los cambios de `developer` se revisan y validan antes de integrarlos en `main`.
- El workflow de GitHub Actions ejecuta compilación y pruebas en cada push y Pull Request hacia `main` o `developer`.
- La integración de `developer` en `main` requiere aprobación explícita; el workflow no hace merges automáticos.

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
