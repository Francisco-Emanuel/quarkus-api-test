package org.acme.service;

import java.time.LocalDateTime;

import org.acme.entity.Chamado;
import org.acme.entity.Usuario;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ChamadoService {

    @Transactional
    public Chamado abrirChamado(Chamado chamado, String username) {
        Usuario usuario = Usuario.find("name", username).firstResult();
        Chamado novoChamado = new Chamado();
        novoChamado.title = chamado.title;
        novoChamado.descr = chamado.descr;
        novoChamado.local = chamado.local;
        novoChamado.departamento = chamado.departamento;
        novoChamado.usuario = usuario;
        novoChamado.status = chamado.status;
        novoChamado.dataAbertura = LocalDateTime.now();

        novoChamado.persist();

        return novoChamado;
    }
}
