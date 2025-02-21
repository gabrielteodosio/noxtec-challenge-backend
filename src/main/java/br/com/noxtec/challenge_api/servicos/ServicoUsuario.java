package br.com.noxtec.challenge_api.servicos;

import br.com.noxtec.challenge_api.dominio.usuario.Usuario;
import br.com.noxtec.challenge_api.dominio.usuario.UsuarioRequestDTO;
import br.com.noxtec.challenge_api.dominio.usuario.UsuarioResponseDTO;
import br.com.noxtec.challenge_api.repositorios.RepositorioUsuario;
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
public class ServicoUsuario {

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    public Usuario buscarUsuarioPorId(UUID usuarioId) {
        Optional<Usuario> optionalUsuario = this.repositorioUsuario.buscarUsuarioPorId(usuarioId);
        return optionalUsuario.orElse(null);
    }

    public Usuario criarUsuario(UsuarioRequestDTO dados) {
        Usuario novoUsuario = new Usuario();

        novoUsuario.setNome(dados.nome());
        novoUsuario.setEmail(dados.email());
        novoUsuario.setSenha(dados.senha());
        novoUsuario.setDataHoraCriacao(LocalDateTime.now());

        this.repositorioUsuario.save(novoUsuario);

        return novoUsuario;
    }

    public List<UsuarioResponseDTO> listarUsuarios(int pagina, int tamanho) {
        Pageable paginacao = PageRequest.of(pagina, tamanho);
        Page<Usuario> usuarios = this.repositorioUsuario.findAll(paginacao);

        return usuarios.map(usuario -> new UsuarioResponseDTO(
                usuario.getId(), usuario.getNome(), usuario.getEmail(),
                usuario.getDataHoraCriacao(), usuario.getDataHoraEdicao()
        )).stream().toList();
    }
}
