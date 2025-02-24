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

    @Query("SELECT c from Contato c JOIN c.usuario u WHERE c.usuario.email = :usuarioEmail" +
            " AND  c.id = :contatoId")
    Optional<Contato> buscarContatoPorIdPorUsuario(@Param("contatoId") UUID contatoId,
                                                   @Param("usuarioEmail") String usuarioEmail);

    @Query("SELECT c FROM Contato c JOIN c.usuario u WHERE c.usuario.email = :usuarioEmail")
    Page<Contato> findContatosByUsuario(Pageable paginacao, @Param("usuarioEmail") String usuarioEmail);

}
