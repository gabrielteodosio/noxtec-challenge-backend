package br.com.noxtec.challenge_api.repositorios;

import br.com.noxtec.challenge_api.dominio.contato.Contato;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface RepositorioContato extends JpaRepository<Contato, UUID> {

    @Query("SELECT c from Contato c WHERE c.id = :contatoId")
    Optional<Contato> buscarContatoPorId(@Param("contatoId") UUID contatoId);

    @Query("SELECT c FROM Contato c WHERE c.usuario.id = :usuarioId")
    Page<Contato> findContatosByUsuario(Pageable paginacao,
                                        @Param("usuarioId") UUID usuarioId);

}
