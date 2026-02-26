# Instruções para Executar o Projeto

Este é um projeto full-stack de gerenciamento fabril com backend em Spring Boot e frontend em Vue 3 + TypeScript.

## Pré-requisitos

- **Docker** e **Docker Compose** instalados
- **Node.js** e **pnpm** instalados
- **Java** instalado
- **Maven** instalado

## 1. Setup do Banco de Dados com Docker

### 1.1 Navegar até a pasta do backend

```bash
cd backend/factory
```

### 1.2 Iniciar o PostgreSQL com Docker Compose

```bash
docker-compose up -d
```

Este comando irá:
- Iniciar um container PostgreSQL
- Criar o banco de dados `factory_db`
- Expor a porta 5432

Verifique se o container está rodando:

```bash
docker-compose ps
```

## 2. Setup e Execução do Backend

### 2.1 Na pasta `backend/factory`

#### Configurar variáveis de ambiente

Copie o arquivo `.env.example` para `.env`:

```bash
cp .env.example .env
```

Verifique ou edite o arquivo `.env` com as configurações do banco:

```env
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/factory_db
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=postgres
SPRING_JPA_HIBERNATE_DDL_AUTO=update
```

Crie o application-dev properties na pasta `src/main/resources` e insira as configurações

```bash
touch application-dev.properties
```

Insira as configurações (nome do banco, usuário e senha que você definiu no docker)

```env
spring.datasource.url=jdbc:postgresql://localhost:5432/nome-do-banco
spring.datasource.username=seu-usuario
spring.datasource.password=sua-senha
```

#### Execução dos testes unitários
```bash
./mvnw test
```

#### Compilar e rodar o backend

```bash
./mvnw clean install
./mvnw spring-boot:run
```


O backend estará disponível em: **http://localhost:8080**

## 3. Setup e Execução do Frontend

### 3.1 Navegar até a pasta do frontend

```bash
cd frontend/factory
```

### 3.2 Configurar variáveis de ambiente

Copie o arquivo `.env.example` para `.env`:

```bash
cp .env.example .env
```

Verifique ou edite o arquivo `.env`:

```env
VITE_API_URL=http://localhost:8080
BASE_URL=http://localhost:5173
```

### 3.3 Instalar dependências

```bash
pnpm install
```

### 3.4 Rodar o servidor de desenvolvimento

```bash
pnpm dev
```

### 3.5 Execução de testes com Cypress

```bash
pnpm cy:run
```

O frontend estará disponível em: **http://localhost:5173**

## 4. Acessar a Aplicação

1. Abra seu navegador
2. Acesse: **http://localhost:5173**
3. A aplicação está pronta para usar!

## Verificação Rápida

| Componente | URL | Status |
|-----------|-----|--------|
| Frontend | http://localhost:5173 | ✅ |
| Backend | http://localhost:8080 | ✅ |
| PostgreSQL | localhost:5432 | ✅ |


## Parar a Aplicação

### Parar o banco de dados

```bash
cd backend/factory
docker compose down
```

### Parar o frontend

Pressione `Ctrl+C` no terminal

### Parar o backend

Pressione `Ctrl+C` no terminal
