package br.com.noxtec.challenge_api.dominio.usuario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue
    @Column(name = "usuario_id")
    private UUID id;

    @Column(name = "usuario_nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "usuario_email", unique = true, nullable = false)
    private String email;

    @Column(name = "usuario_senha")
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UsuarioRole role;

    @CreationTimestamp
    @Column(name = "usuario_dh_cad")
    private LocalDateTime dataHoraCriacao;

    @UpdateTimestamp
    @Column(name = "usuario_dh_ead")
    private LocalDateTime dataHoraEdicao;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> roleAuthorities =
                List.of(new SimpleGrantedAuthority(this.role.name()));

        Set<SimpleGrantedAuthority> authorities =
                this.role.getAuthorities().stream()
                        .map(authority -> new SimpleGrantedAuthority(authority.name()))
                        .collect(Collectors.toSet());

        authorities.addAll(roleAuthorities);

        return authorities;
    }
}
