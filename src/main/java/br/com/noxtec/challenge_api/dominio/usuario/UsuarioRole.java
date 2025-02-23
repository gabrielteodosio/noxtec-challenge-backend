package br.com.noxtec.challenge_api.dominio.usuario;

import lombok.Getter;

import java.util.Set;

@Getter
public enum UsuarioRole {
    ADMIN(Set.of(UsuarioAuthority.CRIAR_CONTATO,
            UsuarioAuthority.EDITAR_CONTATO, UsuarioAuthority.LISTAR_CONTATOS,
            UsuarioAuthority.REMOVER_CONTATO)),
    GUEST(Set.of());

    private final Set<UsuarioAuthority> authorities;

    UsuarioRole(Set<UsuarioAuthority> authorities) {
        this.authorities = authorities;
    }
}
