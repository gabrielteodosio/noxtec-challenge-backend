package br.com.noxtec.challenge_api.dominio.usuario;

import java.time.LocalDateTime;
import java.util.UUID;

public record UsuarioResponseDTO(UUID id,
                                 String nome,
                                 String email,
                                 LocalDateTime dataHoraCriacao,
                                 LocalDateTime dataHoraEdicao) {
}
