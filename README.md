## > > Desafio: Sistema de Gerenciamento de Usuários < <

### Contexto

Você foi contratado para desenvolver um sistema de gerenciamento de usuários para uma empresa. Este sistema deve
permitir que os administradores realizem operações CRUD (Criar, Pesquisar, Atualizar e Deletar) usuários.

### Requisitos Técnicos

1. **Java 17 ou superior**: O backend do sistema deve ser desenvolvido em Java, utilizando as funcionalidades mais
   recentes disponíveis.
2. **Angular 17**: A interface de usuário deve ser construída utilizando Angular 17, com operações CRUD para gerenciar
   usuários.
3. **JPA e Hibernate**: Utilize JPA (Java Persistence API) e Hibernate para mapeamento objeto-relacional e realizar
   operações de banco de dados.
4. **Flyway**: Utilize o Flyway para gerenciar a migração do esquema do banco de dados. Certifique-se de que o esquema
   do banco de dados seja versionado e mantenha-se consistente com as alterações no código.
5. **Spring Boot**: Utilize o Spring Boot para configurar e desenvolver o backend do sistema. Aproveite ao máximo as
   convenções e funcionalidades oferecidas pelo Spring Boot.
6. **Controle de Exceções**: Implemente um controle de exceções para validar e tratar os dados fornecidos pelo usuário.
   Certifique-se de fornecer mensagens de erro significativas e tratamentos adequados para situações inesperadas.
7. **Casos de Uso de Usuários**: Implemente os seguintes casos de uso relacionados a usuários:
    - Cadastro de novo usuário.
    - Listagem de usuários cadastrados.
    - Visualização detalhada de um usuário específico.
    - Atualização dos dados de um usuário.
    - Exclusão de um usuário.
8. **Entidade Departamento**: Implemente uma entidade "Departamento" para separar os usuários no sistema por
   departamento. Cada usuário deve ser associado a um único departamento.

### Requisitos Adicionais e Uso de Outras Tecnologias

Fique à vontade para adicionar requisitos adicionais ou utilizar outras tecnologias que achar adequadas para a
aplicação. Você pode incluir novas funcionalidades, melhorias de desempenho, implementar testes automatizados, ou
qualquer outra coisa que considere relevante para o projeto.

### Entrega

- É uma boa prática fazer commits de forma incremental durante o desenvolvimento, fornecendo uma narrativa clara da
  evolução do projeto.

### Critérios de Avaliação

1. **Funcionalidade Completa**: O sistema deve funcionar conforme os requisitos especificados, permitindo que os
   usuários realizem todas as operações CRUD de forma eficiente.
2. **Qualidade do Código**: O código deve ser limpo, modular e seguir as melhores práticas de desenvolvimento em Java e
   Angular. Deve ser facilmente compreensível e passível de manutenção.
3. **Testabilidade**: O código deve ser testável, com testes unitários e/ou de integração cobrindo as principais
   funcionalidades do sistema.
4. **Documentação**: Forneça uma documentação clara e concisa, descrevendo a arquitetura do sistema, as tecnologias
   utilizadas e instruções para configurar e executar o projeto localmente.