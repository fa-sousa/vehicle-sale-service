# Vehicle Sale Service

A Kotlin + Spring Boot microservice for managing vehicle sales with payment processing.

## Project Structure

- **domain**: Business logic, models (Vehicle, Sale), exceptions, and repository interfaces
- **application**: Use-case implementations (CreateVehicle, Purchase, ListAvailable, etc.)
- **infrastructure**: JPA repositories, data persistence, entity mappers
- **presentation**: REST controllers, request/response DTOs, global exception handler

## Quick Start (Local)

### Prerequisites
- Java 17+
- Maven 3.9+
- PostgreSQL 16+ (via Docker or local)

### Run with Docker Compose
```bash
docker-compose up --build
```

The app will be available at `http://localhost:8080`  
API docs (Swagger UI): `http://localhost:8080/swagger-ui.html`

### Run Locally

1. Start PostgreSQL:
```bash
docker-compose up postgres -d
```

2. Build and run:
```bash
mvn clean package
java -jar target/vehicle-sale-service-0.0.1-SNAPSHOT.jar
```

3. Access API docs:
   - OpenAPI: `http://localhost:8080/v3/api-docs`
   - Swagger UI: `http://localhost:8080/swagger-ui.html`

## API Endpoints

- `GET /api/vehicles/available` - List available vehicles
- `GET /api/vehicles/sold` - List sold vehicles
- `POST /api/vehicles` - Create vehicle
- `PUT /api/vehicles/{id}` - Update vehicle
- `POST /api/vehicles/{id}/purchase` - Purchase vehicle (initiates payment flow)

## Running Tests

```bash
mvn test
```

All 13 unit tests pass with proper mocking of repositories and use-case logic.

## Database Configuration

Datasource is configured via environment variables (with local defaults in `application.properties`):
- `JDBC_DATABASE_URL` (default: `jdbc:postgresql://localhost:5434/vehicle_sale_service_db`)
- `JDBC_DATABASE_USERNAME` (default: `vehicle_user`)
- `JDBC_DATABASE_PASSWORD` (default: `vehicle_pass`)

Hibernates auto-updates schema on startup (`spring.jpa.hibernate.ddl-auto=update`).
