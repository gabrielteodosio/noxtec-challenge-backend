CREATE TABLE usuarios (
    usuario_id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    usuario_nome VARCHAR(100) NOT NULL,
    usuario_email VARCHAR(255) UNIQUE NOT NULL,
    usuario_senha VARCHAR(255),
    usuario_dh_cad TIMESTAMP WITHOUT TIME ZONE,
    usuario_dh_ead TIMESTAMP WITHOUT TIME ZONE
);