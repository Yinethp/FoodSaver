# FoodSaver Backend API

## Overview

The FoodSaver backend is a REST API built with Spring Boot, Kotlin, and H2 in-memory database. It provides endpoints for user authentication and product management.

## Technologies

- **Spring Boot 3.1.4**: Web framework
- **Kotlin 1.9.10**: Programming language
- **Spring Data JPA**: Data persistence
- **Spring Security**: Authentication and authorization
- **H2 Database**: In-memory database for development
- **JWT (JSON Web Tokens)**: Token-based authentication
- **BCrypt**: Password encryption

## Running the Backend

### Prerequisites
- Java 17 or higher
- Gradle (included via wrapper)

### Build and Run

```bash
cd backend
./gradlew build
./gradlew bootRun
```

Or run the JAR directly:

```bash
cd backend
./gradlew build
java -jar build/libs/backend-0.0.1-SNAPSHOT.jar
```

The server will start on `http://localhost:8080`

### H2 Console

Access the H2 database console at: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:foodsaver`
- Username: `sa`
- Password: (leave empty)

## API Endpoints

### Authentication

#### Register User
```http
POST /api/auth/register
Content-Type: application/json

{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "securepass123",
  "role": "SELLER",  // or "BUYER"
  "phone": "1234567890"  // optional
}
```

Response:
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "userId": 1,
  "name": "John Doe",
  "email": "john@example.com",
  "role": "SELLER"
}
```

#### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "john@example.com",
  "password": "securepass123"
}
```

Response:
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "userId": 1,
  "name": "John Doe",
  "email": "john@example.com",
  "role": "SELLER"
}
```

### Products

**Note:** All product endpoints require authentication. Include `X-User-Id` header with the seller's user ID.

#### Create Product
```http
POST /api/products
Content-Type: application/json
X-User-Id: 1

{
  "name": "Tomates",
  "price": 2500.00,  // optional
  "imageUrl": "https://example.com/image.jpg",  // optional
  "expirationDate": "2025-12-31"  // optional
}
```

Response:
```json
{
  "id": 1,
  "sellerId": 1,
  "name": "Tomates",
  "price": 2500.00,
  "state": "AVAILABLE",
  "imageUrl": "https://example.com/image.jpg",
  "expirationDate": "2025-12-31"
}
```

#### List All Products
```http
GET /api/products
X-User-Id: 1
```

Optional query parameters:
- `state`: Filter by product state (AVAILABLE, SOLD, DONATED, EXPIRED)

Response:
```json
[
  {
    "id": 1,
    "sellerId": 1,
    "name": "Tomates",
    "price": 2500.00,
    "state": "AVAILABLE",
    "imageUrl": "https://example.com/image.jpg",
    "expirationDate": "2025-12-31"
  }
]
```

#### Get Product by ID
```http
GET /api/products/{id}
X-User-Id: 1
```

#### Get Products by Seller
```http
GET /api/products/seller/{sellerId}
X-User-Id: 1
```

#### Update Product
```http
PUT /api/products/{id}
Content-Type: application/json
X-User-Id: 1

{
  "name": "Tomates Frescos",  // optional
  "price": 2000.00,  // optional
  "state": "SOLD",  // optional
  "imageUrl": "https://example.com/new-image.jpg",  // optional
  "expirationDate": "2025-12-30"  // optional
}
```

**Note:** Only the seller who created the product can update it.

#### Delete Product
```http
DELETE /api/products/{id}
X-User-Id: 1
```

**Note:** Only the seller who created the product can delete it.

## Data Models

### User
- `id`: Long (auto-generated)
- `name`: String (required)
- `email`: String (required, unique)
- `passwordHash`: String (required, encrypted)
- `role`: Enum (SELLER, BUYER, ADMIN)
- `phone`: String (optional)
- `createdAt`: LocalDateTime (auto-generated)

### Product
- `id`: Long (auto-generated)
- `sellerId`: Long (required)
- `name`: String (required)
- `price`: BigDecimal (optional)
- `state`: Enum (AVAILABLE, SOLD, DONATED, EXPIRED)
- `imageUrl`: String (optional)
- `expirationDate`: LocalDate (optional)
- `createdAt`: LocalDateTime (auto-generated)

## Security

The API uses JWT (JSON Web Tokens) for authentication. When you register or login, you receive a token that should be used in subsequent requests.

### Protected Endpoints
All endpoints except `/api/auth/**` and `/h2-console/**` require authentication.

### Password Security
Passwords are encrypted using BCrypt before being stored in the database.

### JWT Configuration
- Secret key: Configured in `application.properties` (change in production!)
- Token expiration: 24 hours (86400000 milliseconds)

## Error Handling

The API returns appropriate HTTP status codes:
- `200 OK`: Successful request
- `201 Created`: Resource successfully created
- `400 Bad Request`: Invalid request data
- `403 Forbidden`: Authentication required or insufficient permissions
- `404 Not Found`: Resource not found

## Example Usage with cURL

### Register a seller
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Maria Garcia",
    "email": "maria@fruver.com",
    "password": "maria123",
    "role": "SELLER",
    "phone": "3001234567"
  }'
```

### Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "maria@fruver.com",
    "password": "maria123"
  }'
```

### Create a product
```bash
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -H "X-User-Id: 1" \
  -d '{
    "name": "Naranjas",
    "price": 1500,
    "expirationDate": "2025-10-15"
  }'
```

### List all available products
```bash
curl http://localhost:8080/api/products?state=AVAILABLE \
  -H "X-User-Id: 1"
```

## Next Steps

For production deployment, consider:
1. Replace H2 with a persistent database (MySQL, PostgreSQL)
2. Update JWT secret key
3. Configure CORS for frontend access
4. Add rate limiting
5. Implement proper logging
6. Add API documentation (Swagger/OpenAPI)
7. Set up SSL/TLS certificates
