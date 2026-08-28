# Finances


Backend service responsible for financial transactions and history in the Lab05 project.

## Tech Stack

- Java 25
- Spring Boot 4.1.0
- Spring Web
- Spring Data JPA
- Spring Validation
- Spring Boot Actuator
- PostgreSQL Driver
- Lombok
- Maven( Maven Wrapper )


## Requirements

- Java 25

## Run the application

### Windows

```powershell
.\mvnw.cmd spring-boot:run
```

### Linux / macOS

```bash
./mvnw spring-boot:run
```

## Health Check

```
GET http://localhost:8080/actuator/health
```

## Autenticacao

As rotas de negocio, incluindo `GET /transacoes`, exigem um JWT no header HTTP:

```http
Authorization: Bearer <seu-jwt>
```

Este servico nao possui login/registro. Use um token emitido pelo servico de autenticacao configurado com o mesmo `JWT_SECRET`.