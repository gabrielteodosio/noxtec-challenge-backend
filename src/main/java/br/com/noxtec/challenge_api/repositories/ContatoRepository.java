package br.com.noxtec.challenge_api.repositories;

import br.com.noxtec.challenge_api.domain.Contato;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContatoRepository extends JpaRepository<Contato, UUID> {
}
