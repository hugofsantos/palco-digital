-- V1__create_identidade_tables.sql

-- 1. Criação da tabela de grupos (Cadastros de referência)
CREATE TABLE groups (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

-- 2. Criação da tabela de usuários (Raiz de Agregação)
CREATE TABLE users (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
);

-- 3. Criação da tabela associativa (A ponte Many-to-Many)
CREATE TABLE user_groups (
    user_id UUID NOT NULL,
    group_id UUID NOT NULL,
    
    -- A chave primária composta garante que o mesmo usuário 
    -- não seja inserido duas vezes no mesmo grupo.
    PRIMARY KEY (user_id, group_id),
    
    -- Se um usuário for deletado, a relação dele com o grupo apaga automaticamente.
    CONSTRAINT fk_user_group_user 
        FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
        
    -- Proteção de Arquitetura: Impede que um grupo seja apagado se houver usuários nele.
    CONSTRAINT fk_user_group_group 
        FOREIGN KEY (group_id) REFERENCES groups(id) ON DELETE RESTRICT
);