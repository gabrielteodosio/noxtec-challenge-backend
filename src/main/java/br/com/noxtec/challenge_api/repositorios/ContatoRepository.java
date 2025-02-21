package br.com.noxtec.challenge_api.repositorios;

import br.com.noxtec.challenge_api.dominio.contato.Contato;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContatoRepository extends JpaRepository<Contato, UUID> {
}
