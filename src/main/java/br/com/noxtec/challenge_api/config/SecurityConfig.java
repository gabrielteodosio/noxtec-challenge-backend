package br.com.noxtec.challenge_api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static br.com.noxtec.challenge_api.dominio.usuario.UsuarioAuthority.*;
import static br.com.noxtec.challenge_api.dominio.usuario.UsuarioRole.ADMIN;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public AuthenticationManager authenticationManager() {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Rotas públicas
                        .requestMatchers("/api/auth/login", "/api/users/**").permitAll()
                        // Rotas de admin
                        .requestMatchers("/admin/**").hasRole(ADMIN.name())
                        // Rotas de contato
                        .requestMatchers("/api/contacts").hasAuthority(LISTAR_CONTATOS.name())
                        .requestMatchers("/api/contacts").hasAuthority(CRIAR_CONTATO.name())
                        .requestMatchers("/api/contacts/*").hasAuthority(EDITAR_CONTATO.name())
                        .requestMatchers("/api/contacts/*").hasAuthority(REMOVER_CONTATO.name())
                        // Outras Rotas
                        .anyRequest().authenticated()
                )
                .cors(cors -> {});

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
