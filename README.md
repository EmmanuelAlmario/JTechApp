# JTech Ecommerce

![Java](https://img.shields.io/badge/Java_21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot_3.5-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![H2](https://img.shields.io/badge/H2_Database-0000FF?style=for-the-badge&logo=databricks&logoColor=white)
![React](https://img.shields.io/badge/React_18-20232A?style=for-the-badge&logo=react&logoColor=61DAFB)
![Vite](https://img.shields.io/badge/Vite-646CFF?style=for-the-badge&logo=vite&logoColor=white)
![TailwindCSS](https://img.shields.io/badge/Tailwind_CSS-38B2AC?style=for-the-badge&logo=tailwind-css&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=JSON%20web%20tokens&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)

> Plataforma de comercio electronico especializada en productos tecnologicos — limpia, rapida y moderna.

---

## Descripcion

JTech es una plataforma de comercio electronico especializada en productos tecnologicos.
Permite a los clientes explorar un catalogo completo de productos, filtrarlos por categoria,
marca y precio, gestionar su carrito de compras y realizar pedidos. Los administradores
cuentan con un panel completo para gestionar productos, categorias, marcas y ordenes.

---

## Stack Tecnologico

### Backend

| Tecnologia | Version | Uso |
|---|---|---|
| Java | 21 | Lenguaje principal |
| Spring Boot | 3.5.x | Framework principal |
| Spring Data JPA | - | Acceso a datos |
| Spring Security | - | Autenticacion y autorizacion |
| JWT | 0.12.3 | Tokens de autenticacion |
| Hibernate | - | ORM |
| H2 File | - | Base de datos con persistencia |
| Lombok | - | Reduccion de boilerplate |
| Bean Validation | - | Validacion de datos |
| Maven | - | Gestion de dependencias |

### Frontend

| Tecnologia | Version | Uso |
|---|---|---|
| React | 18 | Framework UI |
| Vite | - | Build tool |
| Tailwind CSS | - | Estilos |
| React Bits | - | Componentes animados |
| React Router | - | Navegacion |
| Axios | - | Peticiones HTTP |

---

## Arquitectura

```
Frontend (React + Vite)
        HTTP / REST
Backend (Spring Boot)
        JPA / Hibernate
Base de datos (H2 File)
```

### Estructura del Backend

```
com.jtech.JtechApp
├── categoria/
|   ├── exception/
│   ├── entity/
│   ├── repository/
│   └── service/
├── orden/
|   ├── exception/
│   ├── entity/
│   ├── repository/
│   └── service/
├── pago/
|   ├── exception/
│   ├── entity/
│   ├── repository/
│   └── service/
├── producto/
|   ├── exception/
│   ├── entity/
│   ├── repository/
│   └── service/
└── usuario/
    ├── exception/
    ├── entity/
    ├── repository/
    └── service/
```

---

## Conceptos POO Aplicados

| Concepto | Implementacion |
|---|---|
| Herencia | `Usuario` -> `Cliente` / `Administrador`, `MetodoPago` -> `PagoEfectivo` / `PagoTarjetaCredito` / `PagoTarjetaDebito` |
| Abstraccion | Clases abstractas `Usuario` y `MetodoPago` |
| Polimorfismo | `@Override` en `getRol()` y `procesarPago()` por cada subclase |
| Encapsulacion | Todos los atributos privados con getters/setters via Lombok |

---

## Roles de Usuario

### Cliente (con registro)
- Registro e inicio de sesion
- Ver catalogo completo de productos
- Filtrar por categoria, subcategoria, marca y rango de precios
- Buscar productos por nombre
- Ver detalle de producto con variantes y fotos
- Agregar productos al carrito (localStorage)
- Ver y editar carrito
- Realizar checkout con metodo de pago
- Ver historial de ordenes propias

### Administrador
- Login seguro con JWT
- Dashboard con estadisticas (ventas, ordenes, productos)
- Gestion completa de productos (crear, editar, eliminar)
- Gestion de variantes e imagenes de productos
- Gestion de categorias, subcategorias y marcas
- Ver y gestionar todas las ordenes
- Cambiar estado de ordenes (pendiente, procesando, enviado, entregado)
- Gestion de usuarios administradores

### Super Administrador
- Todo lo del Administrador
- Crear y desactivar administradores

---

## Modelos de Datos

| Tabla | Descripcion |
|---|---|
| `usuarios` | Base de clientes y administradores |
| `clientes` | Datos especificos del cliente |
| `administradores` | Datos especificos del administrador |
| `categorias` | Categorias principales de productos |
| `subcategorias` | Subcategorias dentro de cada categoria |
| `marcas` | Marcas de los productos |
| `productos` | Productos del catalogo |
| `variantes_producto` | Variantes de cada producto |
| `imagenes_producto` | Imagenes asociadas a cada producto |
| `metodos_pago` | Base de metodos de pago |
| `pagos_efectivo` | Pagos realizados en efectivo |
| `pagos_tarjeta_credito` | Pagos con tarjeta de credito |
| `pagos_tarjeta_debito` | Pagos con tarjeta de debito |
| `ordenes` | Ordenes realizadas por los clientes |
| `detalle_ordenes` | Productos incluidos en cada orden |

---

## Endpoints

### Publicos (sin autenticacion)
```
GET  /productos              -> listar productos
GET  /productos/{id}         -> detalle de producto
GET  /productos/buscar       -> buscar por nombre
GET  /productos/filtrar      -> filtrar por categoria, marca, precio
GET  /categorias             -> listar categorias
GET  /marcas                 -> listar marcas
POST /auth/login             -> login
POST /auth/register          -> registro de cliente
```

### Cliente (token CLIENTE)
```
GET  /mis-ordenes            -> historial de ordenes del cliente
POST /ordenes                -> crear orden
```

### Administrador (token ADMIN)
```
POST   /productos            -> crear producto
PUT    /productos/{id}       -> actualizar producto
DELETE /productos/{id}       -> eliminar producto
GET    /ordenes              -> ver todas las ordenes
PUT    /ordenes/{id}/estado  -> cambiar estado de orden
GET    /dashboard/stats      -> ver estadisticas
POST   /categorias           -> crear categoria
PUT    /categorias/{id}      -> actualizar categoria
DELETE /categorias/{id}      -> eliminar categoria
POST   /marcas               -> crear marca
PUT    /marcas/{id}          -> actualizar marca
DELETE /marcas/{id}          -> eliminar marca
POST   /administradores      -> crear administrador (solo SUPER_ADMIN)
DELETE /administradores/{id} -> desactivar administrador (solo SUPER_ADMIN)
```

---

## Seguridad
- Autenticacion mediante JWT (JSON Web Token)
- Contrasenas encriptadas con BCrypt
- Rutas protegidas segun rol con Spring Security
- CORS configurado para comunicacion Frontend <-> Backend
- Roles: `SUPER_ADMIN`, `ADMIN`, `CLIENTE`

---

## Como ejecutar el proyecto

### Requisitos
- Java 21
- Maven
- Node.js 20+

### Backend
```bash
cd backend
mvn spring-boot:run
```

La base de datos H2 se crea automaticamente en `data/jtechdb`.
Consola H2 disponible en: `http://localhost:8080/h2-console`

### Frontend
```bash
cd frontend
npm install
npm run dev
```

---

## Documentacion

```
documentation/
├── diagrama_clases.puml
└── casos_de_uso.puml
└── DiagramaER.pdf
```

---

## Diseño
- Estetica limpia y minimalista inspirada en Apple Store
- Paleta de colores: por definir
- Componentes animados con React Bits

---

## Equipo

| Nombre | Programa | Rol | Seccion (Clase) |
|---|---|---|---|
| Emmanuel Almario | Desarrollo de Software - 3er semestre | Desarrollador | 1604 |

---
