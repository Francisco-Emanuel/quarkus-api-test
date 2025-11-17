package org.acme.service;

import java.util.List;
import java.util.Optional;

import org.acme.dto.LoginRequestDTO;
import org.acme.entity.Usuario;
import org.mindrot.jbcrypt.BCrypt;

import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class UsuarioService {
    
    public List<Usuario> listarUsuarios() {
        return Usuario.listAll();
    }

    public Optional<Usuario> buscarUsuarioPorId(Long id) {
        return Usuario.findByIdOptional(id);
    }

    @Transactional
    public Usuario createUser(String name, String password) {
        if (Usuario.find("name", name).firstResult() != null) {
            throw new NotFoundException("Usuário com este nome já existe.");
        }

        Usuario usuario = new Usuario();
        usuario.name = name;
        usuario.password = BcryptUtil.bcryptHash(password); 
        
        usuario.persist();
        return usuario;
    }

    @Transactional
    public Optional<Usuario> atualizarUsuario(Long id, LoginRequestDTO dadosNovos) {
        Optional<Usuario> usuarioOpt = Usuario.findByIdOptional(id);

        if (usuarioOpt.isEmpty()) {
            return Optional.empty(); 
        }

        Usuario usuario = usuarioOpt.get();
        
        usuario.name = dadosNovos.name;
        

        return Optional.of(usuario);
    }

    @Transactional
    public boolean deletarUsuario(Long id) {
        return Usuario.deleteById(id);
    }
}
