package br.com.noxtec.challenge_api.servicos;

import br.com.noxtec.challenge_api.dominio.contato.Contato;
import br.com.noxtec.challenge_api.dominio.contato.ContatoRequestDTO;
import br.com.noxtec.challenge_api.dominio.contato.ContatoResponseDTO;
import br.com.noxtec.challenge_api.repositorios.RepositorioContato;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ServicoContato {

    @Autowired
    private RepositorioContato repositorioContato;

    public Contato buscarContatoPorId(UUID contatoId) {
        Optional<Contato> optionalUsuario = this.repositorioContato.buscarContatoPorId(contatoId);
        return optionalUsuario.orElse(null);
    }

    public Contato buscarContatoPorId(UUID contatoId, String usuarioEmail) {
        Optional<Contato> optionalUsuario =
                this.repositorioContato.buscarContatoPorIdPorUsuario(contatoId, usuarioEmail);
        return optionalUsuario.orElse(null);
    }

    public Contato criarContato(ContatoRequestDTO dados) {
        Contato novoContato = new Contato();

        novoContato.setNome(dados.nome());
        novoContato.setEmail(dados.email());
        novoContato.setCelular(dados.celular());
        novoContato.setTelefone(dados.telefone());
        novoContato.setFavorito(dados.favorito());
        novoContato.setAtivo(dados.ativo());
        novoContato.setDataHoraCriacao(LocalDateTime.now());
        novoContato.setUsuario(dados.usuario());

        this.repositorioContato.save(novoContato);
        return novoContato;
    }

    public List<ContatoResponseDTO> listarContatos(int pagina, int tamanho) {
        Pageable paginacao = PageRequest.of(pagina, tamanho);
        Page<Contato> contatos = this.repositorioContato.findAll(paginacao);

        return contatos.map(contato -> new ContatoResponseDTO(
                contato.getId(), contato.getNome(), contato.getEmail(),
                contato.getCelular(), contato.getTelefone(),
                contato.getFavorito(), contato.getAtivo(),
                contato.getDataHoraCriacao(), contato.getDataHoraEdicao()
        )).stream().toList();
    }

    public List<ContatoResponseDTO> listarContatosPorUsusario(int pagina, int tamanho, String email) {
        Pageable paginacao = PageRequest.of(pagina, tamanho);
        Page<Contato> contatos = this.repositorioContato.findContatosByUsuario(paginacao, email);

        return contatos.map(contato -> new ContatoResponseDTO(
                contato.getId(), contato.getNome(), contato.getEmail(),
                contato.getCelular(), contato.getTelefone(),
                contato.getFavorito(), contato.getAtivo(),
                contato.getDataHoraCriacao(), contato.getDataHoraEdicao()
        )).stream().toList();
    }

    public Contato atualizarContato(UUID contatoId, ContatoRequestDTO dados) {
        Contato contato = this.buscarContatoPorId(contatoId);

        if (contato != null) {
            contato.setNome(dados.nome() != null ? dados.nome() : contato.getNome());
            contato.setEmail(dados.email() != null ? dados.email() : contato.getEmail());
            contato.setCelular(dados.celular() != null ? dados.celular() : contato.getCelular());
            contato.setTelefone(dados.telefone() != null ? dados.telefone() : contato.getTelefone());
            contato.setFavorito(dados.favorito() != null ? dados.favorito() : contato.getFavorito());
            contato.setAtivo(dados.ativo() != null ? dados.ativo() : contato.getAtivo());

            this.repositorioContato.save(contato);
        }

        return contato;
    }

    public Contato apagarContatoPorId(UUID contatoId) {
        Contato contato = this.buscarContatoPorId(contatoId);
        this.repositorioContato.delete(contato);
        return contato;
    }
}
