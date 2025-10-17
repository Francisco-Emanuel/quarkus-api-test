package org.acme.service;

import java.util.Optional;

import org.acme.entity.Usuario;
import org.mindrot.jbcrypt.BCrypt;

import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UsuarioService {
    
    @Transactional
    public Usuario createUser(String name, String password) {
        Usuario usuario = new Usuario();
        usuario.name = name;
        usuario.password = BcryptUtil.bcryptHash(password);
        usuario.persist();
        return usuario;
    }

    public Optional<String> login(String name, String password) {
        Optional<Usuario> optionalUser = Usuario.find("name", name).firstResultOptional();

        if (optionalUser.isPresent()) {
            Usuario usuario = optionalUser.get();
            if (BCrypt.checkpw(password, usuario.password)) {
                return Optional.of(usuario.authToken);
            }
        }
        return Optional.empty();
    }
}
