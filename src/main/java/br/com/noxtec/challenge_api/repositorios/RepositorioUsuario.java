package br.com.noxtec.challenge_api.repositorios;

import br.com.noxtec.challenge_api.dominio.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface RepositorioUsuario extends JpaRepository<Usuario, UUID> {

    @Query("SELECT u from Usuario u WHERE u.id = :usuarioId")
    public Optional<Usuario> buscarUsuarioPorId(@Param("usuarioId") UUID usuarioId);
}
