package br.com.noxtec.challenge_api.dominio.contato;

import br.com.noxtec.challenge_api.dominio.usuario.Usuario;

public record ContatoRequestDTO(String nome, String email, String celular, String telefone, Character favorito, Character ativo, Usuario usuario) {
}
