CREATE TABLE usuarios
(
    id            SERIAL PRIMARY KEY,
    name          VARCHAR(255) NOT NULL,
    cpf           VARCHAR(17)  NOT NULL UNIQUE,
    email         VARCHAR(255) NOT NULL UNIQUE,
    password      VARCHAR(255) NOT NULL,
    role          smallint     NOT NULL,
    department_id INTEGER,
    FOREIGN KEY (department_id) REFERENCES departamentos (id)
);