package br.com.noxtec.challenge_api.servicos;

import br.com.noxtec.challenge_api.dominio.usuario.Usuario;
import br.com.noxtec.challenge_api.dominio.usuario.UsuarioRequestDTO;
import br.com.noxtec.challenge_api.dominio.usuario.UsuarioResponseDTO;
import br.com.noxtec.challenge_api.dominio.usuario.UsuarioRole;
import br.com.noxtec.challenge_api.repositorios.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    public Usuario buscarUsuarioPorEmail(String email) {
        Optional<Usuario> optionalUsuario = this.repositorioUsuario.findByEmail(email);
        return optionalUsuario.orElse(null);
    }

    public Usuario criarUsuario(UsuarioRequestDTO dados) {
        Usuario novoUsuario = new Usuario();

        novoUsuario.setNome(dados.nome());
        novoUsuario.setEmail(dados.email());

        String hashSenha = new BCryptPasswordEncoder().encode(dados.senha());
        novoUsuario.setSenha(hashSenha);

        novoUsuario.setRole(dados.role() != null ? dados.role() : UsuarioRole.ADMIN);
        novoUsuario.setDataHoraCriacao(LocalDateTime.now());

        this.repositorioUsuario.save(novoUsuario);

        return novoUsuario;
    }

    public List<UsuarioResponseDTO> listarUsuarios(int pagina, int tamanho) {
        Pageable paginacao = PageRequest.of(pagina, tamanho);
        Page<Usuario> usuarios = this.repositorioUsuario.findAll(paginacao);

        return usuarios.map(usuario -> new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(), usuario.getEmail(),
                usuario.getRole(),
                usuario.getDataHoraCriacao(), usuario.getDataHoraEdicao()
        )).stream().toList();
    }

    public Usuario atualizarUsuario(UUID usuarioId, UsuarioRequestDTO dados) {
        Usuario usuario = this.buscarUsuarioPorId(usuarioId);

        if (usuario != null) {
            usuario.setNome(dados.nome() != null ? dados.nome() : usuario.getNome());
            usuario.setEmail(dados.email() != null ? dados.email() : usuario.getEmail());
            usuario.setSenha(dados.senha() != null ? dados.senha() : usuario.getSenha());
            usuario.setRole(dados.role() != null ? dados.role() : usuario.getRole());

            this.repositorioUsuario.save(usuario);
        }

        return usuario;
    }

    public Usuario apagarUsuarioPorId(UUID userId) {
        Usuario usuario = this.buscarUsuarioPorId(userId);
        this.repositorioUsuario.delete(usuario);
        return usuario;
    }
}
