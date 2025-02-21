CREATE TABLE contatos (
    contato_id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    contato_nome VARCHAR(100) NOT NULL,
    contato_email VARCHAR(255),
    contato_celular VARCHAR(11) NOT NULL,
    contato_telefone VARCHAR(10),
    contato_sn_favorito CHARACTER(1),
    contato_sn_ativo CHARACTER(1),
    contato_dh_cad TIMESTAMP WITHOUT TIME ZONE,
    contato_dh_ead TIMESTAMP WITHOUT TIME ZONE,
    usuario_id UUID,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(usuario_id) ON DELETE CASCADE
);