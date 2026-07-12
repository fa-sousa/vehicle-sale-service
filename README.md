# Vehicle Sale Service

Microsserviço responsável pelo cadastro, atualização, consulta e venda de veículos.

O serviço centraliza as regras de negócio da venda, controla o status dos veículos,
registra vendas e processa atualizações de pagamento.

## Responsabilidades

- Cadastrar veículos;
- Atualizar veículos disponíveis;
- Listar veículos disponíveis;
- Listar veículos vendidos;
- Iniciar a compra de um veículo;
- Criar uma venda com pagamento pendente;
- Processar confirmação ou cancelamento de pagamento;
- Persistir veículos e vendas no PostgreSQL.

## Arquitetura

O projeto segue uma organização inspirada em Clean Architecture:

```text
src/main/kotlin/com/fasousa/vehiclesaleservice
├── application
│   ├── service
│   └── usecase
├── domain
│   ├── model
│   └── repository
├── infrastructure
│   └── persistence
│       ├── entity
│       ├── mapper
│       └── repository
├── presentation
│   ├── request
│   ├── response
│   ├── GlobalExceptionHandler.kt
│   └── VehicleController.kt
└── VehicleSaleServiceApplication.kt
```

## Tecnologias

- Kotlin
- Java 17
- Spring Boot 3
- Spring Web
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven
- Docker
- Docker Compose
- Swagger/OpenAPI
- JUnit 5
- Mockito
- JaCoCo

## Regras de negócio

- Apenas veículos com status `AVAILABLE` podem ser comprados;
- Ao iniciar uma compra, o veículo muda para `PENDING_PAYMENT`;
- Uma venda é criada com pagamento `PENDING`;
- Pagamento aprovado altera o veículo para `SOLD`;
- Pagamento cancelado devolve o veículo para `AVAILABLE`;
- Apenas veículos disponíveis podem ser alterados;
- Veículos disponíveis e vendidos devem ser listados conforme as regras da aplicação.

## Status de veículo

```text
AVAILABLE
PENDING_PAYMENT
SOLD
```

## Status de pagamento

```text
PENDING
APPROVED
CANCELLED
```

## Pré-requisitos

- Java 17
- Maven 3.8 ou superior
- Docker
- Docker Compose

## Configuração

A aplicação utiliza:

```yaml
server:
  port: 8080
```

Banco local:

```text
Database: vehicle_sale_service_db
Host: localhost
Porta: 5434
Usuário: vehicle_user
Senha: vehicle_pass
```

Variáveis aceitas:

```text
JDBC_DATABASE_URL
JDBC_DATABASE_USERNAME
JDBC_DATABASE_PASSWORD
```

## Executar durante o desenvolvimento

Recomenda-se executar apenas o PostgreSQL no Docker e a aplicação pelo Maven.

### Subir o PostgreSQL

```bash
docker compose up -d postgres
```

### Executar a aplicação

```bash
mvn spring-boot:run
```

A aplicação ficará disponível em:

```text
http://localhost:8080
```

## Executar tudo pelo Docker

```bash
docker compose up --build -d
```

Neste modo, não execute simultaneamente:

```bash
mvn spring-boot:run
```

Caso contrário, duas instâncias tentarão usar a porta `8080`.

## Swagger

```text
http://localhost:8080/swagger-ui/index.html
```

OpenAPI:

```text
http://localhost:8080/v3/api-docs
```

## Endpoints

### Cadastrar veículo

```http
POST /api/vehicles
```

Exemplo:

```json
{
  "brand": "Honda",
  "model": "Civic",
  "year": 2024,
  "color": "Prata",
  "price": 125000.00
}
```

### Listar veículos disponíveis

```http
GET /api/vehicles/available
```

### Listar veículos vendidos

```http
GET /api/vehicles/sold
```

### Atualizar veículo

```http
PUT /api/vehicles/{id}
```

### Comprar veículo

```http
POST /api/vehicles/{id}/purchase
```

Body:

```json
{
  "cpf": "12345678900"
}
```

Resposta esperada:

```json
{
  "id": 1,
  "vehicleId": 1,
  "cpf": "12345678900",
  "paymentCode": "1855f7f0-3395-455a-bca4-a249695311e0",
  "paymentStatus": "PENDING",
  "saleDate": "2026-07-10T20:30:00"
}
```

## Teste do fluxo

### 1. Cadastrar

```bash
curl -X POST http://localhost:8080/api/vehicles \
  -H "Content-Type: application/json" \
  -d '{
    "brand": "Honda",
    "model": "Civic",
    "year": 2024,
    "color": "Prata",
    "price": 125000.00
  }'
```

### 2. Listar disponíveis

```bash
curl http://localhost:8080/api/vehicles/available
```

### 3. Comprar

```bash
curl -X POST http://localhost:8080/api/vehicles/1/purchase \
  -H "Content-Type: application/json" \
  -d '{"cpf":"12345678900"}'
```

## Testes

```bash
mvn clean test
```

Build com cobertura:

```bash
mvn clean verify
```

Relatório JaCoCo:

```text
target/site/jacoco/index.html
```

## Respostas de erro

### 400 — Bad Request

Usado para corpo inválido, campos obrigatórios ausentes ou valores inválidos.

### 404 — Not Found

Usado quando o veículo ou venda não existe.

### 409 — Conflict

Usado quando uma operação viola o estado atual do recurso, por exemplo:

- comprar veículo não disponível;
- atualizar veículo já vendido;
- processar pagamento em estado incompatível.

## Health check

```text
GET /actuator/health
```

## Integração

Este microsserviço é chamado pelo `vehicle-platform` através de HTTP.

```text
Vehicle Platform :8082
          |
          | HTTP
          v
Vehicle Sale Service :8080
          |
          v
PostgreSQL
```