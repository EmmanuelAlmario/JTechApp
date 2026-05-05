# <img src="https://img.shields.io/badge/JTech-Ecommerce-black?style=for-the-badge&logo=shopify&logoColor=white"/>

> Plataforma de comercio electrónico especializada en productos tecnológicos — limpia, rápida y moderna.

---

## 📋 Descripción
JTech es una plataforma de comercio electrónico especializada en productos tecnológicos.
Permite a los clientes explorar un catálogo completo de productos, filtrarlos por categoría,
marca y precio, gestionar su carrito de compras y realizar pedidos. Los administradores
cuentan con un panel completo para gestionar productos, categorías, marcas y órdenes.

---

## 🛠️ Stack Tecnológico

### Backend
![Java](https://img.shields.io/badge/Java_21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot_3.5-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL_16-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=JSON%20web%20tokens&logoColor=white)

| Tecnología | Versión | Uso |
|---|---|---|
| Java | 21 | Lenguaje principal |
| Spring Boot | 3.5.14 | Framework principal |
| Spring Data JPA | - | Acceso a datos |
| Spring Security | - | Autenticación y autorización |
| JWT | 0.12.3 | Tokens de autenticación |
| Hibernate | - | ORM |
|| H2 | - | Base de datos desarrollo |
| Lombok | - | Reducción de boilerplate |
| Bean Validation | - | Validación de datos |
| Maven | - | Gestión de dependencias |

### Frontend
![React](https://img.shields.io/badge/React_18-20232A?style=for-the-badge&logo=react&logoColor=61DAFB)
![Vite](https://img.shields.io/badge/Vite-646CFF?style=for-the-badge&logo=vite&logoColor=white)
![TailwindCSS](https://img.shields.io/badge/Tailwind_CSS-38B2AC?style=for-the-badge&logo=tailwind-css&logoColor=white)
![Axios](https://img.shields.io/badge/Axios-5A29E4?style=for-the-badge&logo=axios&logoColor=white)

| Tecnología | Versión | Uso |
|---|---|---|
| React | 18 | Framework UI |
| Vite | - | Build tool |
| Tailwind CSS | - | Estilos |
| React Bits | - | Componentes animados |
| React Router | - | Navegación |
| Axios | - | Peticiones HTTP |

---

## Arquitectura

```
Frontend (React + Vite)
        ↓ HTTP / REST
Backend (Spring Boot)
        ↓ JPA / Hibernate
Base de datos (PostgreSQL)
```

### Estructura del Backend
```
com.jtech.jtech-backend
├── entity/         → Modelos de datos (entidades JPA)
├── repository/     → Interfaces de acceso a datos
├── service/        → Lógica de negocio
├── controller/     → Endpoints REST
├── security/       → Configuración JWT y Spring Security
├── dto/            → Objetos de transferencia de datos
└── exception/      → Manejo global de excepciones
```

---

## 👥 Roles de Usuario

### Cliente (con registro)
- Registro e inicio de sesión
- Ver catálogo completo de productos
- Filtrar por categoría, subcategoría, marca y rango de precios
- Buscar productos por nombre
- Ver detalle de producto con variantes y fotos
- Agregar productos al carrito (localStorage)
- Ver y editar carrito
- Realizar checkout
- Ver historial de órdenes propias

### Administrador
- Login seguro con JWT
- Dashboard con estadísticas (ventas, órdenes, productos)
- Gestión completa de productos (crear, editar, eliminar)
- Gestión de variantes e imágenes de productos
- Gestión de categorías, subcategorías y marcas
- Ver y gestionar todas las órdenes
- Cambiar estado de órdenes (pendiente, procesando, enviado, entregado)
- Gestión de usuarios administradores

---

## Modelos de Datos

| Tabla | Descripción |
|---|---|
| `usuarios` | Clientes y administradores del sistema |
| `categorias` | Categorías principales de productos |
| `subcategorias` | Subcategorías dentro de cada categoría |
| `marcas` | Marcas de los productos |
| `productos` | Productos del catálogo |
| `variantes_producto` | Variantes de cada producto (color, almacenamiento, etc.) |
| `imagenes_producto` | Imágenes asociadas a cada producto |
| `ordenes` | Órdenes realizadas por los clientes |
| `detalle_ordenes` | Productos incluidos en cada orden |
| 'administradores' | Administradores del sistema |
        |'clientes' | Clientes del sistma| 

---

## 🔌 Endpoints

### Públicos (sin autenticación)
```
GET  /productos              → listar productos
GET  /productos/{id}         → detalle de producto
GET  /productos/buscar       → buscar por nombre
GET  /productos/filtrar      → filtrar por categoría, marca, precio
GET  /categorias             → listar categorías
GET  /marcas                 → listar marcas
POST /auth/login             → login
POST /auth/register          → registro de cliente
```

### Cliente (token CLIENTE)
```
GET  /mis-ordenes            → historial de órdenes del cliente
POST /ordenes                → crear orden
```

### Administrador (token ADMIN)
```
POST   /productos            → crear producto
PUT    /productos/{id}       → actualizar producto
DELETE /productos/{id}       → eliminar producto
GET    /ordenes              → ver todas las órdenes
PUT    /ordenes/{id}/estado  → cambiar estado de orden
GET    /dashboard/stats      → ver estadísticas
POST   /categorias           → crear categoría
PUT    /categorias/{id}      → actualizar categoría
DELETE /categorias/{id}      → eliminar categoría
POST   /marcas               → crear marca
PUT    /marcas/{id}          → actualizar marca
DELETE /marcas/{id}          → eliminar marca
```

---

## Seguridad
- Autenticación mediante **JWT (JSON Web Token)**
- Contraseñas encriptadas con **BCrypt**
- Rutas protegidas según rol con **Spring Security**
- CORS configurado para comunicación Frontend ↔ Backend
- Roles: `ADMIN`, `CLIENTE`

---

## Diseño
- Estética limpia y minimalista inspirada en Apple Store
- Paleta de colores: por definir
- Componentes animados con React Bits
- Diseño responsive para escritorio hasta el momento.

---

## Equipo

| Nombre | Programa | Rol |
|---|---|---|
| Emmanuel | Desarrollo de Software — 3er semestre | Desarrollador |
