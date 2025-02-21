package br.com.noxtec.challenge_api.repositorios;

import br.com.noxtec.challenge_api.dominio.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
}
