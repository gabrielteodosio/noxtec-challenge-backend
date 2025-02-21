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
import java.util.UUID;

@Service
public class ServicoContato {

    @Autowired
    private RepositorioContato repositorioContato;

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

    public List<ContatoResponseDTO> listarContatosPorUsusario(int pagina, int tamanho, UUID usuarioId) {
        Pageable paginacao = PageRequest.of(pagina, tamanho);
        Page<Contato> contatos = this.repositorioContato.findContatosByUsuario(paginacao, usuarioId);

        return contatos.map(contato -> new ContatoResponseDTO(
                contato.getId(), contato.getNome(), contato.getEmail(),
                contato.getCelular(), contato.getTelefone(),
                contato.getFavorito(), contato.getAtivo(),
                contato.getDataHoraCriacao(), contato.getDataHoraEdicao()
        )).stream().toList();
    }
}
