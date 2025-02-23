package br.com.noxtec.challenge_api.controladores;

import br.com.noxtec.challenge_api.config.JwtUtil;
import br.com.noxtec.challenge_api.dominio.auth.AutenticacaoRequestDTO;
import br.com.noxtec.challenge_api.dominio.auth.AutenticacaoResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class ControladorAuth {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<AutenticacaoResponseDTO> login(
            @RequestBody AutenticacaoRequestDTO autentitcacao) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(autentitcacao.email(), autentitcacao.senha())
        );

        var userDetails = userDetailsService.loadUserByUsername(autentitcacao.email());
        String jwt = jwtUtil.generateToken(userDetails.getUsername());

        AutenticacaoResponseDTO responseBody = new AutenticacaoResponseDTO(jwt);
        return ResponseEntity.ok(responseBody);
    }
}
