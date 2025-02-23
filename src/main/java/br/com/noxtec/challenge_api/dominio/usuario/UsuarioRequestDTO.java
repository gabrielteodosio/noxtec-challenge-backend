package br.com.noxtec.challenge_api.dominio.usuario;

public record UsuarioRequestDTO(String nome, String email, String senha, UsuarioRole role) {
}
