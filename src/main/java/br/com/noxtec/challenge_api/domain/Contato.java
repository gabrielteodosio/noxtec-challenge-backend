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
@Table(name = "contatos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contato {

    @Id
    @GeneratedValue
    @Column(name = "contato_id")
    private UUID id;

    @Column(name = "contato_nome", length = 100)
    private String name;

    @Column(name = "contato_email")
    private String email;

    @Column(name = "contato_celular", length = 11)
    private String celular;

    @Column(name = "contato_telefone", length = 10)
    private String telefone;

    @Column(name = "contato_sn_favorito", length = 1)
    private Character favorito;

    @Column(name = "contato_sn_ativo", length = 1)
    private Character ativo;

    @CreationTimestamp
    @Column(name = "contato_dh_cad")
    private LocalDateTime dataHoraCriacao;

    @UpdateTimestamp
    @Column(name = "contato_dh_ead")
    private LocalDateTime dataHoraEdicao;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
