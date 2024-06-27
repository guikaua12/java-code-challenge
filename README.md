# java-code-challenge

Link do desafio: https://gitlab.com/code.dev/code-challenge

### Requisitos Técnicos:

- [x] Java 17 ou superior
- [x] Angular 17
- [x] JPA e Hibernate
- [x] Flyway
- [x] Spring Boot
- [x] Controle de Exceções

### Casos de Uso de Usuários:

- [x] Cadastro de novo usuário.
- [x] Listagem de usuários cadastrados.
- [x] Visualização detalhada de um usuário específico.
- [x] Atualização dos dados de um usuário.
- [x] Exclusão de um usuário.

## Back-end

Implementei um sistema seguindo os requisitos, fiz uma autenticação usando email/senha que gera um token JWT para
acessar as rotas protegidas, a documentação foi feita usando swagger e pode ser consultada
em `http://localhost:8080/swagger-ui.html`.

### Como rodar o projeto:

```sh
cd backend
./mvnw clean install
./mvnw spring-boot:run
```

Pra rodar os testes:

```sh
./mvnw test
```

### Tecnologias usadas:

- Flyway - Para versionamento do banco de dados
- Spring Boot - Para criação da aplicação
- Spring Data JPA (Hibernate) - Para persistência de dados
- Spring Security - Para autenticação e autorização
- Lombok - Para reduzir a verbosidade do código
- Swagger - Para documentação da API
- PostgreSQL - Banco de dados
- ModelMapper - Para mapeamento de entidades

## Front-end

### Como rodar o projeto:

Implementei uma interface simples para consumir a API, foi usado Angular Material pra fazer os Modals.

```sh
cd frontend
npm install
npm start
```