-- V3__create_palco_tables.sql

-- 1. Criação da tabela de Canais
CREATE TABLE channels (
    id UUID PRIMARY KEY,
    
    -- owner_id representa o usuário do módulo Identity. 
    -- UNIQUE garante que 1 usuário só pode ter 1 canal.
    owner_id UUID NOT NULL UNIQUE, 
    
    name VARCHAR(255) NOT NULL,
    description VARCHAR(500)
);

-- 2. Criação da tabela de Apresentações
CREATE TABLE presentations (
    id UUID PRIMARY KEY,
    channel_id UUID NOT NULL,
    title VARCHAR(255) NOT NULL,
    
    -- O Postgres vai salvar o momento exato no tempo em UTC.
    scheduled_time TIMESTAMPTZ NOT NULL,
    
    status VARCHAR(50) NOT NULL,
    
    -- A coluna de versão para o Optimistic Locking (@Version)
    version BIGINT NOT NULL DEFAULT 0,

    -- Se um canal for deletado, a plataforma derruba todas as apresentações dele
    CONSTRAINT fk_presentation_channel 
        FOREIGN KEY (channel_id) REFERENCES channels (id) ON DELETE CASCADE
);