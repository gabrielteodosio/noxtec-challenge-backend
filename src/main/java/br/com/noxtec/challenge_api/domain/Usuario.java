package br.com.noxtec.challenge_api.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue
    @Column(name = "usuario_id")
    private UUID id;

    @Column(name = "usuario_nome", length = 100)
    private String nome;

    @Column(name = "usuario_email")
    private String email;

    @Column(name = "usuario_senha")
    private String senha;

    @CreationTimestamp
    @Column(name = "usuario_dh_cad")
    private LocalDateTime dataHoraCriacao;

    @UpdateTimestamp
    @Column(name = "usuario_dh_ead")
    private LocalDateTime dataHoraEdicao;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Contato[] contatos;
}
