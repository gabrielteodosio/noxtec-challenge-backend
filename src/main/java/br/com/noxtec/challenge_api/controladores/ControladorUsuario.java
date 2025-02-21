package br.com.noxtec.challenge_api.controladores;

import br.com.noxtec.challenge_api.dominio.usuario.Usuario;
import br.com.noxtec.challenge_api.dominio.usuario.UsuarioRequestDTO;
import br.com.noxtec.challenge_api.dominio.usuario.UsuarioResponseDTO;
import br.com.noxtec.challenge_api.servicos.ServicoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class ControladorUsuario {

    @Autowired
    private ServicoUsuario servicoUsuario;

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listar(@RequestParam(defaultValue = "0", required = false) int pagina,
                                                           @RequestParam(defaultValue = "10", required = false) int tamanho) {
        List<UsuarioResponseDTO> usuarios = this.servicoUsuario.listarUsuarios(pagina, tamanho);
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> criarViaJSON(@RequestBody UsuarioRequestDTO body) {
        Usuario novoUsuario = this.servicoUsuario.criarUsuario(body);
        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO(novoUsuario.getId(),
                novoUsuario.getNome(), novoUsuario.getEmail(),
                novoUsuario.getDataHoraCriacao(), novoUsuario.getDataHoraEdicao());
        return ResponseEntity.status(201).body(usuarioResponseDTO);
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<UsuarioResponseDTO> criarViaForm(@RequestParam("nome") String nome,
                                                           @RequestParam("email") String email,
                                                           @RequestParam("senha") String senha) {
        UsuarioRequestDTO usuarioRequestDTO = new UsuarioRequestDTO(nome, email, senha);
        Usuario novoUsuario = this.servicoUsuario.criarUsuario(usuarioRequestDTO);

        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO(novoUsuario.getId(),
                novoUsuario.getNome(), novoUsuario.getEmail(),
                novoUsuario.getDataHoraCriacao(), novoUsuario.getDataHoraEdicao());
        return ResponseEntity.status(201).body(usuarioResponseDTO);
    }

    @PutMapping("/{usuarioId}")
    public ResponseEntity<UsuarioResponseDTO> atualizarViaJSON(@RequestBody UsuarioRequestDTO body,
                                                               @PathVariable("usuarioId") UUID usuarioId) {
        Usuario usuarioAtualizado = this.servicoUsuario.atualizarUsuario(usuarioId, body);

        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO(usuarioAtualizado.getId(),
                usuarioAtualizado.getNome(), usuarioAtualizado.getEmail(),
                usuarioAtualizado.getDataHoraCriacao(), usuarioAtualizado.getDataHoraEdicao());
        return ResponseEntity.status(200).body(usuarioResponseDTO);
    }

    @PutMapping(value = "/{usuarioId}", consumes = "multipart/form-data")
    public ResponseEntity<UsuarioResponseDTO> atualizarViaForm(@RequestParam("nome") String nome,
                                                               @RequestParam("email") String email,
                                                               @RequestParam("senha") String senha,
                                                               @PathVariable("usuarioId") UUID usuarioId) {
        UsuarioRequestDTO usuarioRequestDTO = new UsuarioRequestDTO(nome, email, senha);
        Usuario usuarioAtualizado = this.servicoUsuario.atualizarUsuario(usuarioId, usuarioRequestDTO);

        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO(usuarioAtualizado.getId(),
                usuarioAtualizado.getNome(), usuarioAtualizado.getEmail(),
                usuarioAtualizado.getDataHoraCriacao(), usuarioAtualizado.getDataHoraEdicao());
        return ResponseEntity.status(200).body(usuarioResponseDTO);
    }

    @DeleteMapping("/{usuarioId}")
    public ResponseEntity<UsuarioResponseDTO> excluir(@PathVariable("usuarioId") UUID usuarioId) {
        Usuario usuarioExcluido = this.servicoUsuario.apagarUsuarioPorId(usuarioId);

        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO(usuarioExcluido.getId(),
                usuarioExcluido.getNome(), usuarioExcluido.getEmail(),
                usuarioExcluido.getDataHoraCriacao(), usuarioExcluido.getDataHoraEdicao());
        return ResponseEntity.status(200).body(usuarioResponseDTO);
    }
}
