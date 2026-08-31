-- V2__insert_grupos_padrao.sql

INSERT INTO groups (id, name) VALUES (gen_random_uuid(), 'ARTISTA');
INSERT INTO groups (id, name) VALUES (gen_random_uuid(), 'ESPECTADOR_COMUM');
INSERT INTO groups (id, name) VALUES (gen_random_uuid(), 'MODERADOR');