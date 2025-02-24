package br.com.noxtec.challenge_api.controladores;

import br.com.noxtec.challenge_api.dominio.contato.Contato;
import br.com.noxtec.challenge_api.dominio.contato.ContatoRequestDTO;
import br.com.noxtec.challenge_api.dominio.contato.ContatoResponseDTO;
import br.com.noxtec.challenge_api.dominio.usuario.Usuario;
import br.com.noxtec.challenge_api.servicos.ServicoContato;
import br.com.noxtec.challenge_api.servicos.ServicoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/contacts")
public class ControladorContato {

    @Autowired
    private ServicoContato servicoContato;

    @Autowired
    private ServicoUsuario servicoUsuario;

    @GetMapping
    public ResponseEntity<List<ContatoResponseDTO>> listar(@RequestParam(defaultValue = "0", required = false) int pagina,
                                                           @RequestParam(defaultValue = "10", required = false) int tamanho) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            String userEmail = userDetails.getUsername();
            List<ContatoResponseDTO> contatos = this.servicoContato.listarContatosPorUsusario(pagina, tamanho, userEmail);

            return ResponseEntity.ok(contatos);
        }

        return ResponseEntity.badRequest().body(new ArrayList<>());
    }

    @GetMapping("/{contatoId}")
    public ResponseEntity<ContatoResponseDTO> buscarUmContato(@PathVariable("contatoId") UUID contatoId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            String userEmail = userDetails.getUsername();
            Contato contato =  this.servicoContato.buscarContatoPorId(contatoId, userEmail);

            ContatoResponseDTO contatoResponseDTO = new ContatoResponseDTO(contato.getId(),
                    contato.getNome(), contato.getEmail(),
                    contato.getCelular(), contato.getTelefone(),
                    contato.getFavorito(), contato.getAtivo(),
                    contato.getDataHoraCriacao(), contato.getDataHoraEdicao());

            return ResponseEntity.ok(contatoResponseDTO);
        }

        return ResponseEntity.badRequest().body(null);
    }


    @PostMapping
    public ResponseEntity<ContatoResponseDTO> criarContatoViaJSON(@RequestBody ContatoRequestDTO body) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!(principal instanceof UserDetails userDetails)) {
            return (ResponseEntity<ContatoResponseDTO>) ResponseEntity.badRequest();
        }

        Usuario usuarioParente = this.servicoUsuario.buscarUsuarioPorEmail(userDetails.getUsername());

        Character favorito = body.favorito() != null ? body.favorito() : 'n';
        Character ativo = body.ativo() != null ? body.ativo() : 's';

        ContatoRequestDTO contatoRequestDTO = new ContatoRequestDTO(body.nome(), body.email(),
                body.celular(), body.telefone(), body.favorito(), body.ativo(), usuarioParente);

        Contato novoContato = this.servicoContato.criarContato(contatoRequestDTO);
        ContatoResponseDTO contatoResponseDTO = new ContatoResponseDTO(novoContato.getId(),
                novoContato.getNome(), novoContato.getEmail(), novoContato.getCelular(), novoContato.getTelefone(),
                favorito, ativo, novoContato.getDataHoraCriacao(), novoContato.getDataHoraEdicao());
        return ResponseEntity.status(201).body(contatoResponseDTO);
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<ContatoResponseDTO> criarViaForm(@RequestParam("nome") String nome,
                                                           @RequestParam(value = "email", required = false) String email,
                                                           @RequestParam(value = "celular", required = false) String celular,
                                                           @RequestParam(value = "telefone", required = false) String telefone,
                                                           @RequestParam(value = "favorito", required = false, defaultValue = "n") Character favorito,
                                                           @RequestParam(value = "ativo", required = false, defaultValue = "s") Character ativo) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!(principal instanceof UserDetails userDetails)) {
            return (ResponseEntity<ContatoResponseDTO>) ResponseEntity.badRequest();
        }

        Usuario usuarioParente = this.servicoUsuario.buscarUsuarioPorEmail(userDetails.getUsername());

        ContatoRequestDTO contatoRequestDTO = new ContatoRequestDTO(nome, email, celular, telefone, favorito, ativo, usuarioParente);
        Contato novoContato = this.servicoContato.criarContato(contatoRequestDTO);

        ContatoResponseDTO contatoResponseDTO = new ContatoResponseDTO(novoContato.getId(),
                novoContato.getNome(), novoContato.getEmail(), novoContato.getCelular(), novoContato.getTelefone(),
                novoContato.getFavorito(), novoContato.getAtivo(),
                novoContato.getDataHoraCriacao(), novoContato.getDataHoraEdicao());
        return ResponseEntity.status(201).body(contatoResponseDTO);
    }

    @PutMapping("/{contatoId}")
    public ResponseEntity<ContatoResponseDTO> atualizarViaJSON(@RequestBody ContatoRequestDTO body,
                                                               @PathVariable("contatoId") UUID contatoId) {
        Contato contatoAtualizado = this.servicoContato.atualizarContato(contatoId, body);

        ContatoResponseDTO contatoResponseDTO = new ContatoResponseDTO(contatoAtualizado.getId(),
                contatoAtualizado.getNome(), contatoAtualizado.getEmail(),
                contatoAtualizado.getCelular(), contatoAtualizado.getTelefone(),
                contatoAtualizado.getFavorito(), contatoAtualizado.getAtivo(),
                contatoAtualizado.getDataHoraCriacao(), contatoAtualizado.getDataHoraEdicao());
        return ResponseEntity.status(200).body(contatoResponseDTO);
    }

    @PutMapping(value = "/{contatoId}", consumes = "multipart/form-data")
    public ResponseEntity<ContatoResponseDTO> atualizarViaJSON(@RequestParam(value = "nome", required = false) String nome,
                                                               @RequestParam(value = "email", required = false) String email,
                                                               @RequestParam(value = "celular", required = false) String celular,
                                                               @RequestParam(value = "telefone", required = false) String telefone,
                                                               @RequestParam(value = "favorito", required = false) Character favorito,
                                                               @RequestParam(value = "ativo", required = false) Character ativo,
                                                               @PathVariable(value = "contatoId") UUID contatoId) {
        ContatoRequestDTO contatoRequestDTO = new ContatoRequestDTO(nome, email, celular, telefone, favorito, ativo, null);
        Contato contatoAtualizado = this.servicoContato.atualizarContato(contatoId, contatoRequestDTO);

        ContatoResponseDTO contatoResponseDTO = new ContatoResponseDTO(contatoAtualizado.getId(),
                contatoAtualizado.getNome(), contatoAtualizado.getEmail(),
                contatoAtualizado.getCelular(), contatoAtualizado.getTelefone(),
                contatoAtualizado.getFavorito(), contatoAtualizado.getAtivo(),
                contatoAtualizado.getDataHoraCriacao(), contatoAtualizado.getDataHoraEdicao());
        return ResponseEntity.status(200).body(contatoResponseDTO);
    }

    @DeleteMapping("/{contatoId}")
    public ResponseEntity<ContatoResponseDTO> excluir(@PathVariable("contatoId") UUID contatoId) {
        Contato contatoExcluido = this.servicoContato.apagarContatoPorId(contatoId);

        ContatoResponseDTO contatoResponseDTO = new ContatoResponseDTO(contatoExcluido.getId(),
                contatoExcluido.getNome(), contatoExcluido.getEmail(),
                contatoExcluido.getCelular(), contatoExcluido.getTelefone(),
                contatoExcluido.getFavorito(), contatoExcluido.getAtivo(),
                contatoExcluido.getDataHoraCriacao(), contatoExcluido.getDataHoraEdicao());
        return ResponseEntity.status(200).body(contatoResponseDTO);
    }
}
