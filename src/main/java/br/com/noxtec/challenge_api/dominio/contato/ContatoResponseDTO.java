package br.com.noxtec.challenge_api.dominio.contato;

import br.com.noxtec.challenge_api.dominio.usuario.Usuario;

import java.time.LocalDateTime;
import java.util.UUID;

public record ContatoResponseDTO(UUID id, String nome, String email, String Celular, String telefone, Character favorito, Character ativo, LocalDateTime dataHoraCriacao, LocalDateTime dataHoraEdicao) {
}
