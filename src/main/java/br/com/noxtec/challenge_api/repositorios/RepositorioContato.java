package br.com.noxtec.challenge_api.repositorios;

import br.com.noxtec.challenge_api.dominio.contato.Contato;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface RepositorioContato extends JpaRepository<Contato, UUID> {

    @Query("SELECT c FROM Contato c WHERE c.usuario.id = :usuarioId")
    public Page<Contato> findContatosByUsuario(Pageable paginacao,
                                               @Param("usuarioId") UUID usuarioId);

}
