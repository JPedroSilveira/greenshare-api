-- Database: SeedShareDB

-- DROP DATABASE "SeedShareDB";

CREATE DATABASE "SeedShareDB"
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Portuguese_Brazil.1252'
    LC_CTYPE = 'Portuguese_Brazil.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
    
CREATE SEQUENCE SQ_CLIMA START 1;	
CREATE SEQUENCE SQ_CRESCIMENTO START 1;	
CREATE SEQUENCE SQ_ENDERECO START 1;	
CREATE SEQUENCE SQ_ESPECIE START 1;	
CREATE SEQUENCE SQ_FLOR START 1;	
CREATE SEQUENCE SQ_FLORICULTURA START 1;	
CREATE SEQUENCE SQ_FRUTO START 1;	
CREATE SEQUENCE SQ_OFERTA START 1;	
CREATE SEQUENCE SQ_PERMISSAO START 1;	
CREATE SEQUENCE SQ_SELO START 1;	
CREATE SEQUENCE SQ_SOLICITACAO START 1;	
CREATE SEQUENCE SQ_SOLO START 1;	
CREATE SEQUENCE SQ_SUGESTAO START 1;	
CREATE SEQUENCE SQ_USUARIO START 1;	
CREATE SEQUENCE SQ_USUARIO_SELO START 1;	

Select * From Usuario;