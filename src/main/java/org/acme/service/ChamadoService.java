package org.acme.service;

import java.time.LocalDateTime;

import org.acme.entity.Chamado;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ChamadoService {

    @Transactional
    public Chamado abrirChamado(Chamado chamado) {
        Chamado novoChamado = new Chamado();
        novoChamado.title = chamado.title;
        novoChamado.descr = chamado.descr;
        novoChamado.local = chamado.local;
        novoChamado.usuario = chamado.usuario;
        novoChamado.status = chamado.status;
        novoChamado.dataAbertura = LocalDateTime.now();

        novoChamado.persist();

        return novoChamado;
    }
}
