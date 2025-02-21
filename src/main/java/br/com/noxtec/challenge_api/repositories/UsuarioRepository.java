package br.com.noxtec.challenge_api.repositories;

import br.com.noxtec.challenge_api.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
}
