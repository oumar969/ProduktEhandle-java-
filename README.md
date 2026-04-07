# E-Handle Produkt API 🛒

En produktionsklart REST API for e-commerce product inventory management bygget med Spring Boot, PostgreSQL og JWT authentication.

## 📋 Features

✅ **User Management**

- User registration og login
- JWT token-baserede authentication
- Role-based access control (USER/ADMIN)
- Sikker password hashing med BCrypt

✅ **Product Management**

- CRUD operationer på produkter
- Kategorisering af produkter
- Inventory management (stock tracking)
- SKU-baseret søgning
- Pagination og søg-funktionalitet

✅ **Order System**

- Order oprettelse og tracking
- Order item management
- Order status workflow (PENDING → CONFIRMED → SHIPPED → DELIVERED)

✅ **API Documentation**

- Swagger/OpenAPI 3.0 dokumentation
- Interaktiv API explorer
- Automatisk endpoint dokumentation

✅ **Security**

- JWT authentication
- CORS support
- Role-based authorization
- Request validation

✅ **Error Handling**

- Global exception handling
- Detaljerede error responses
- Validation error messages

## 🏗️ Arkitektur

```
ProduktEhandle/
├── src/main/java/com/ehandle/
│   ├── model/                 # JPA entities
│   │   ├── User.java
│   │   ├── Category.java
│   │   ├── Product.java
│   │   ├── Order.java
│   │   └── OrderItem.java
│   ├── dto/                   # Data Transfer Objects
│   │   ├── UserDTO.java
│   │   ├── ProductDTO.java
│   │   ├── CategoryDTO.java
│   │   ├── LoginRequest.java
│   │   ├── RegisterRequest.java
│   │   └── JwtResponse.java
│   ├── repository/            # Spring Data JPA repositories
│   ├── service/               # Business logic
│   ├── controller/            # REST controllers
│   ├── security/              # JWT security components
│   ├── config/                # Configuration classes
│   ├── exception/             # Custom exceptions & handlers
│   └── ProduktApiApplication.java
├── src/main/resources/
│   └── application.properties  # Database & JWT config
├── pom.xml                    # Maven dependencies
├── Dockerfile                 # Container image
├── docker-compose.yml         # PostgreSQL service
└── README.md
```

## 🛠️ Technology Stack

**Backend:**

- Java 17
- Spring Boot 3.2.0
- Spring Security
- Spring Data JPA / Hibernate
- PostgreSQL 15
- Maven 3.6+

**Security & JWT:**

- JJWT 0.12.3
- BCrypt password encoding

**API Documentation:**

- Springdoc OpenAPI 2.2.0 (Swagger 3.0)

**Build & Deployment:**

- Docker & Docker Compose
- Maven multi-stage builds

## 📦 Installation

### Forudsætninger

- Java 17 eller nyere
- Maven 3.6+
- Docker & Docker Compose (for PostgreSQL)
- PostgreSQL 15 eller Docker

### 1. Clone Repository

```bash
cd c:\Users\oamma\OneDrive\Desktop\java\ProduktEhandle
```

### 2. Start PostgreSQL

```bash
docker-compose up -d
```

### 3. Build & Run

```bash
# Maven build
mvn clean install

# Run application
mvn spring-boot:run

# Eller direct Java
java -jar target/produkt-api-1.0.0.jar
```

Appen starter på: **http://localhost:8080**

## 🔌 API Endpoints

### Authentication

```
POST   /api/auth/register       - User registration
POST   /api/auth/login          - User login (returns JWT)
GET    /api/auth/me             - Get current user (requires auth)
```

### Products

```
GET    /api/products            - Get all products (paginated)
GET    /api/products/{id}       - Get product by ID
GET    /api/products/search     - Search products by name
GET    /api/products/category/{categoryId} - Products by category
POST   /api/products            - Create product (ADMIN)
PUT    /api/products/{id}       - Update product (ADMIN)
DELETE /api/products/{id}       - Delete product (ADMIN)
PATCH  /api/products/{id}/stock - Update stock quantity (ADMIN)
```

### Categories

```
GET    /api/categories          - Get all categories
GET    /api/categories/{id}     - Get category by ID
POST   /api/categories          - Create category (ADMIN)
PUT    /api/categories/{id}     - Update category (ADMIN)
DELETE /api/categories/{id}     - Delete category (ADMIN)
```

### Orders

```
GET    /api/orders              - Get user's orders
GET    /api/orders/{id}         - Get order by ID
```

## 📖 API Documentation

**Swagger UI:** http://localhost:8080/swagger-ui.html

API dokumentation er automatisk genereret og interaktiv. Du kan teste alle endpoints direkte fra browseren.

## 🔐 Authentication

### Login & Get Token

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "password123"
  }'
```

Response:

```json
{
  "token": "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "id": 1,
  "username": "admin",
  "email": "admin@example.com",
  "role": "ADMIN"
}
```

### Use Token in Requests

```bash
curl -X GET http://localhost:8080/api/products \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9..."
```

## 📊 Database Schema

### Users Table

```sql
- id (PK)
- username (UNIQUE)
- email (UNIQUE)
- password (hashed)
- full_name
- role (USER/ADMIN)
- is_enabled
- created_at
- updated_at
```

### Categories Table

```sql
- id (PK)
- name (UNIQUE)
- description
- created_at
- updated_at
```

### Products Table

```sql
- id (PK)
- name
- description
- price (DECIMAL)
- quantity_in_stock
- sku (UNIQUE)
- category_id (FK)
- is_active
- created_at
- updated_at
```

### Orders Table

```sql
- id (PK)
- user_id (FK)
- order_number (UNIQUE)
- status (ENUM)
- total_amount
- created_at
- updated_at
```

### OrderItems Table

```sql
- id (PK)
- order_id (FK)
- product_id (FK)
- quantity
- price_at_purchase
```

## 🧪 Testing

### Example: Registrer ny bruger

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "email": "john@example.com",
    "password": "Secret123!",
    "fullName": "John Doe"
  }'
```

### Example: Søg efter produkter

```bash
curl -X GET "http://localhost:8080/api/products/search?name=laptop&page=0&size=20" \
  -H "Authorization: Bearer YOUR_TOKEN"
```

### Example: Opret kategori (ADMIN)

```bash
curl -X POST http://localhost:8080/api/categories \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer ADMIN_TOKEN" \
  -d '{
    "name": "Electronics",
    "description": "Electronic products"
  }'
```

## 🐳 Docker Deployment

### Build Docker Image

```bash
docker build -t ehandle-api:1.0.0 .
```

### Run Container

```bash
docker run -d \
  --name ehandle-api \
  -p 8080:8080 \
  --network ehandle-network \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/ehandle_db \
  ehandle-api:1.0.0
```

### With Docker Compose

```bash
# Start all services
docker-compose -f docker-compose.yml up -d

# Stop
docker-compose down

# View logs
docker-compose logs -f
```

## ⚙️ Configuration

### application.properties

```properties
# Server
server.port=8080

# PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/ehandle_db
spring.datasource.username=postgres
spring.datasource.password=postgres

# JWT
app.jwt.secret=mySecretKeyForJWTTokenGenerationThatIsLongEnoughFor256BitHS256Algorithm
app.jwt.expiration=86400000  # 24 hours

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false

# Swagger
springdoc.swagger-ui.path=/swagger-ui.html
```

## 🔍 Error Handling

Alle endpoints returnerer strukturerede error responses:

```json
{
  "status": 404,
  "message": "Product not found with id: 999",
  "error": "Resource Not Found",
  "timestamp": "2024-04-07T10:30:45.123456",
  "path": "/api/products/999"
}
```

## 🚀 Production Tips

1. **Change JWT Secret**: Ændr den i application.properties
2. **Database**: Brug managed PostgreSQL (AWS RDS, Azure, etc.)
3. **HTTPS**: Enable SSL/TLS certificates
4. **CORS**: Konfigurer CORS efter behov
5. **Logging**: Setup centralized logging (ELK, Splunk)
6. **Monitoring**: Setup health checks og metrics
7. **Backups**: Implement database backup strategy

## 📝 API Response Examples

### Success Response

```json
{
  "id": 1,
  "name": "Laptop",
  "description": "High-performance laptop",
  "price": 999.99,
  "quantityInStock": 50,
  "sku": "LAPTOP-001",
  "categoryId": 1,
  "categoryName": "Electronics",
  "isActive": true
}
```

### Error Response

```json
{
  "status": 400,
  "message": "Validation failed",
  "errors": {
    "name": "Product name is required",
    "price": "Price must be positive"
  },
  "timestamp": "2024-04-07T10:30:45.123456",
  "path": "/api/products"
}
```

## 🔗 Portfolio Highlights

✨ **Enterprise-grade features:**

- JWT authentication with role-based access
- Comprehensive error handling
- API documentation with Swagger
- Database relationships (One-to-Many, Many-to-One)
- Pagination and filtering
- Security best practices

✨ **Production-ready:**

- Docker containerization
- Proper logging
- Transaction management
- Input validation
- SQL injection prevention (via JPA)

## 📞 Support

For spørgsmål eller issues, kontakt: [your-email@example.com]

## 📄 License

MIT License - see LICENSE file for details

---

**Status:** ✅ Production Ready
**Version:** 1.0.0
**Last Updated:** April 7, 2024
