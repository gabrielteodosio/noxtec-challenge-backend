package br.com.noxtec.challenge_api.controladores;

import br.com.noxtec.challenge_api.dominio.contato.Contato;
import br.com.noxtec.challenge_api.dominio.contato.ContatoRequestDTO;
import br.com.noxtec.challenge_api.dominio.contato.ContatoResponseDTO;
import br.com.noxtec.challenge_api.dominio.usuario.Usuario;
import br.com.noxtec.challenge_api.servicos.ServicoContato;
import br.com.noxtec.challenge_api.servicos.ServicoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/{usuarioId}/contacts")
public class ControladorContato {

    @Autowired
    private ServicoContato servicoContato;

    @Autowired
    private ServicoUsuario servicoUsuario;

    @GetMapping
    public ResponseEntity<List<ContatoResponseDTO>> listarContatos(@RequestParam(defaultValue = "0", required = false) int pagina,
                                                                   @RequestParam(defaultValue = "10", required = false) int tamanho,
                                                                   @PathVariable("usuarioId") UUID usuarioId) {
        List<ContatoResponseDTO> contatos = this.servicoContato.listarContatosPorUsusario(pagina, tamanho, usuarioId);
        return ResponseEntity.ok(contatos);
    }

    @PostMapping
    public ResponseEntity<ContatoResponseDTO> criarContatoViaJSON(@RequestBody ContatoRequestDTO body,
                                                                  @PathVariable("usuarioId") UUID usuarioId) {
        Usuario usuarioParente = this.servicoUsuario.buscarUsuarioPorId(usuarioId);

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
                                                           @RequestParam(value = "ativo", required = false, defaultValue = "s") Character ativo,
                                                           @PathVariable("usuarioId") UUID usuarioId) {
        Usuario usuarioParente = this.servicoUsuario.buscarUsuarioPorId(usuarioId);

        ContatoRequestDTO contatoRequestDTO = new ContatoRequestDTO(nome, email, celular, telefone, favorito, ativo, usuarioParente);
        Contato novoContato = this.servicoContato.criarContato(contatoRequestDTO);

        ContatoResponseDTO contatoResponseDTO = new ContatoResponseDTO(novoContato.getId(),
                novoContato.getNome(), novoContato.getEmail(), novoContato.getCelular(), novoContato.getTelefone(),
                novoContato.getFavorito(), novoContato.getAtivo(),
                novoContato.getDataHoraCriacao(), novoContato.getDataHoraEdicao());
        return ResponseEntity.status(201).body(contatoResponseDTO);
    }
}
