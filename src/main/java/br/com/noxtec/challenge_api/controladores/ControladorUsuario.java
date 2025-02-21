package br.com.noxtec.challenge_api.controladores;

import br.com.noxtec.challenge_api.dominio.usuario.Usuario;
import br.com.noxtec.challenge_api.dominio.usuario.UsuarioRequestDTO;
import br.com.noxtec.challenge_api.dominio.usuario.UsuarioResponseDTO;
import br.com.noxtec.challenge_api.servicos.ServicoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
