# Finances API — Documentação

Serviço backend responsável pelas transações financeiras e histórico do projeto **Lab05**.

- **Base URL (local):** `http://localhost:8080`
- **Stack:** Java 25 · Spring Boot 4.1.0 · Spring Web · Spring Data JPA · Spring Validation · Spring Boot Actuator · PostgreSQL · Lombok

> ⚠️ **Status atual do projeto:** este serviço está em estágio inicial. Apenas um endpoint REST está implementado até o momento (`HomeController`). As entidades `shoppingList` e `shoppingItem` já existem no domínio (JPA), mas ainda **não possuem Controller/Service/Repository** — ou seja, não há endpoints para criá-las, listá-las, editá-las ou removê-las ainda. Essa parte da documentação (seção 3) descreve os *dados modelados*, não uma API funcional.

---

## 1. Autenticação

O `application-dev.properties` já define `jwt.secret` e `jwt.expiration`, indicando que autenticação via JWT está planejada. No entanto, **não há filtro de segurança, endpoint de login/registro nem dependência de Spring Security no `pom.xml`** — a autenticação ainda não está implementada no código atual. Nenhum endpoint exige token hoje.

---

## 2. Endpoints implementados

### 2.1 `GET /`

Endpoint de teste/health simples do serviço.

| | |
|---|---|
| **Método** | `GET` |
| **Path** | `/` |
| **Autenticação** | Não requer |
| **Parâmetros** | Nenhum |

**Resposta — 200 OK**

Content-Type: `text/plain`

```
Hello, Finances! Pipe ✅
```

**Exemplo (curl)**
```bash
curl http://localhost:8080/
```

---

### 2.2 `GET /actuator/health`

Endpoint padrão do **Spring Boot Actuator**, usado para health check da aplicação (ex.: liveness/readiness em pipelines de deploy).

| | |
|---|---|
| **Método** | `GET` |
| **Path** | `/actuator/health` |
| **Autenticação** | Não requer |
| **Parâmetros** | Nenhum |

**Resposta — 200 OK**
```json
{
  "status": "UP"
}
```

> Como o Actuator está com a dependência padrão (sem configuração extra em `application.properties`), apenas o endpoint `/actuator/health` fica exposto por padrão — outros endpoints do Actuator (`/actuator/info`, `/actuator/metrics` etc.) não estão habilitados publicamente.

---

## 3. Modelo de dados (ainda sem endpoints expostos)

Estas entidades já existem no código (`@Entity`, mapeadas para PostgreSQL via JPA/Hibernate), mas **ainda não há uma camada REST sobre elas**. Documentado aqui como referência de domínio, para quando os endpoints forem criados.

### 3.1 `ShoppingList` (tabela `shopping_list`)

| Campo | Tipo | Coluna | Obrigatório | Observação |
|---|---|---|---|---|
| `id` | `UUID` | `id_shopping_list` | gerado automaticamente | chave primária |
| `shoppingListName` | `String` | `shopping_list_name` | sim | |
| `shoppingListStatus` | `Boolean` | `shopping_list_status` | sim | |
| `companyId` | `UUID` | `id_company` | sim | referência à empresa dona da lista |
| `items` | `List<ShoppingItem>` | — | — | relação `@OneToMany`, cascade `ALL`, `orphanRemoval` |

### 3.2 `ShoppingItem` (tabela `shopping_item`)

| Campo | Tipo | Coluna | Obrigatório | Observação |
|---|---|---|---|---|
| `id` | `UUID` | `id_item` | gerado automaticamente | chave primária |
| `shoppingList` | `ShoppingList` | `id_shopping_list` | sim | `@ManyToOne`, `FetchType.LAZY` |
| `itemName` | `String` | `item_name` | sim | |
| `itemValue` | `BigDecimal` | `item_value` | sim | |
| `itemDescription` | `String` | `item_description` | não | |
| `itemCategory` | `String` | `item_category` | não | |
| `itemStatus` | `Boolean` | `item_status` | sim | indica se o item foi comprado |
| `purchaseDate` | `LocalDate` | `purchase_date` | não | |
| `transactionId` | `UUID` | `id_transaction` | não | referência à transação financeira gerada quando o item é marcado como comprado |

**Relacionamento:** uma `ShoppingList` possui vários `ShoppingItem` (1:N), com exclusão em cascata.

---

## 4. Configuração / Ambiente

| Propriedade | Valor (dev) | Descrição |
|---|---|---|
| `spring.profiles.active` | `dev` | perfil ativo por padrão |
| `spring.datasource.url` | `jdbc:postgresql://localhost:5432/spring_database` | conexão PostgreSQL local |
| `spring.jpa.hibernate.ddl-auto` | `update` | Hibernate atualiza o schema automaticamente |
| `jwt.secret` / `jwt.expiration` | — | reservados para autenticação futura (ainda não usados no código) |

**Executar localmente:**
```bash
# Linux / macOS
./mvnw spring-boot:run

# Windows
.\mvnw.cmd spring-boot:run
```

Requisito: Java 25 e um PostgreSQL rodando localmente na porta `5432` com o banco `spring_database`.

---

## 5. Próximos passos sugeridos para a documentação

Quando o time implementar os endpoints de `ShoppingList`/`ShoppingItem` (CRUD) e a autenticação JWT, esta documentação deve ganhar seções novas com: método, path, request body (DTO), response body, códigos de status e exemplos — sigo o mesmo padrão usado nas seções 2.1/2.2 acima.
