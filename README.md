# Vehicle Sale Service

Microsserviço responsável pelo cadastro, atualização, consulta e venda de veículos.

Este serviço concentra todas as regras de negócio relacionadas ao processo de venda, controle do status dos veículos, registro das vendas e processamento do pagamento.

---

# Arquitetura

O projeto segue uma organização inspirada em Clean Architecture.

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

---

# Responsabilidades

- Cadastrar veículos
- Atualizar veículos disponíveis
- Listar veículos disponíveis
- Listar veículos vendidos
- Iniciar a compra de um veículo
- Registrar uma venda
- Processar confirmação ou cancelamento de pagamento
- Persistir veículos e vendas em PostgreSQL

---

# Tecnologias

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
- Swagger / OpenAPI
- JUnit 5
- Mockito
- JaCoCo

---

# Regras de negócio

- Apenas veículos com status `AVAILABLE` podem ser comprados.
- Ao iniciar uma compra o veículo passa para `PENDING_PAYMENT`.
- Uma venda é criada com status de pagamento `PENDING`.
- Quando o pagamento é aprovado o veículo passa para `SOLD`.
- Quando o pagamento é cancelado o veículo volta para `AVAILABLE`.
- Apenas veículos disponíveis podem ser atualizados.
- Os veículos disponíveis são listados do menor preço para o maior.
- Os veículos vendidos são listados do menor preço para o maior.

---

# Status dos veículos

```text
AVAILABLE
PENDING_PAYMENT
SOLD
```

# Status do pagamento

```text
PENDING
APPROVED
CANCELLED
```

---

# Pré-requisitos

- Java 17
- Docker
- Docker Compose

---

# Configuração

Banco utilizado durante o desenvolvimento:

| Propriedade | Valor |
|------------|-------|
| Database | vehicle_sale_service_db |
| Host | localhost |
| Porta | 5434 |
| Usuário | vehicle_user |

Variáveis de ambiente aceitas:

```text
JDBC_DATABASE_URL
JDBC_DATABASE_USERNAME
JDBC_DATABASE_PASSWORD
SERVER_PORT
```

---

# Executando durante o desenvolvimento

## Subir somente o PostgreSQL

```bash
docker compose up -d postgres
```

## Executar a aplicação

```bash
./mvnw spring-boot:run
```

A aplicação ficará disponível em:

```
http://localhost:8080
```

---

# Executando toda a aplicação com Docker

```bash
docker compose up --build -d
```

Verificar containers:

```bash
docker compose ps
```

Logs da aplicação:

```bash
docker compose logs -f app
```

Parar containers:

```bash
docker compose down
```

> Não execute `./mvnw spring-boot:run` enquanto a aplicação estiver rodando via Docker, pois ambas utilizarão a porta 8080.

---

# Documentação da API

Swagger

```
http://localhost:8080/swagger-ui/index.html
```

OpenAPI

```
http://localhost:8080/v3/api-docs
```

---

# Endpoints

## Cadastrar veículo

```
POST /api/vehicles
```

Body

```json
{
  "brand": "Honda",
  "model": "Civic",
  "year": 2024,
  "color": "Prata",
  "price": 125000.00
}
```

---

## Atualizar veículo

```
PUT /api/vehicles/{id}
```

---

## Listar veículos disponíveis

```
GET /api/vehicles/available
```

---

## Listar veículos vendidos

```
GET /api/vehicles/sold
```

---

## Comprar veículo

```
POST /api/vehicles/{id}/purchase
```

Body

```json
{
  "cpf": "12345678900"
}
```

Resposta

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

---

## Webhook de pagamento

```
POST /api/payments/webhook
```

Body

```json
{
  "paymentCode": "1855f7f0-3395-455a-bca4-a249695311e0",
  "status": "APPROVED"
}
```

Status aceitos

```
APPROVED
CANCELLED
```

Quando aprovado

- venda → APPROVED
- veículo → SOLD

Quando cancelado

- venda → CANCELLED
- veículo → AVAILABLE

---

# Fluxo completo da aplicação

## 1 - Cadastrar veículo

```bash
curl -X POST http://localhost:8080/api/vehicles \
-H "Content-Type: application/json" \
-d '{
"brand":"Honda",
"model":"Civic",
"year":2024,
"color":"Prata",
"price":125000
}'
```

---

## 2 - Listar disponíveis

```bash
curl http://localhost:8080/api/vehicles/available
```

---

## 3 - Comprar veículo

```bash
curl -X POST http://localhost:8080/api/vehicles/1/purchase \
-H "Content-Type: application/json" \
-d '{
"cpf":"12345678900"
}'
```

---

## 4 - Confirmar pagamento

```bash
curl -X POST http://localhost:8080/api/payments/webhook \
-H "Content-Type: application/json" \
-d '{
"paymentCode":"CODIGO_RETORNADO",
"status":"APPROVED"
}'
```

---

## 5 - Listar vendidos

```bash
curl http://localhost:8080/api/vehicles/sold
```

---

# Testes

Os testes utilizam o perfil **test** com banco H2 em memória.

Não é necessário iniciar PostgreSQL nem Docker para executá-los.

Executar testes

```bash
./mvnw clean test
```

Executar testes + build + cobertura

```bash
./mvnw clean verify
```

Relatório JaCoCo

```
target/site/jacoco/index.html
```

O projeto possui cobertura mínima configurada para **80%**.

---

# Health Check

```
GET /actuator/health
```

---

# Respostas de erro

## 400 - Bad Request

- corpo inválido
- campos obrigatórios ausentes
- validações

## 404 - Not Found

- veículo inexistente
- venda inexistente

## 409 - Conflict

- compra de veículo indisponível
- atualização de veículo vendido
- pagamento em estado inválido

---

# Integração

O serviço é consumido pelo **vehicle-platform** através de requisições HTTP.

```text
               HTTP

Vehicle Platform (8082)
          │
          ▼
Vehicle Sale Service (8080)
          │
          ▼
 PostgreSQL
```

---

# Estrutura da solução

```text
Frontend
     │
     ▼
Vehicle Platform
(MongoDB)
     │ HTTP
     ▼
Vehicle Sale Service
(PostgreSQL)
     │
     ▼
Gateway de Pagamento
```

---

# Autor

**Fátima F. Sousa**

Pós-Tech SOAT – Arquitetura de Software

Tech Challenge – Fase 4```