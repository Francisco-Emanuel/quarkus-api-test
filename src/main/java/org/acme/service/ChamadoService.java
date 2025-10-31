package org.acme.service;

import java.time.LocalDateTime;

import org.acme.dto.ChamadoRequestDTO;
import org.acme.entity.Chamado;
import org.acme.entity.Departamento;
import org.acme.entity.Usuario;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ChamadoService {

    @Transactional
    public Chamado abrirChamado(ChamadoRequestDTO chamado, String username) {
        Usuario usuario = Usuario.find("name", username).firstResult();
        Chamado novoChamado = new Chamado();
        Departamento depto = Departamento.findById(chamado.departamentoId);
        novoChamado.title = chamado.title;
        novoChamado.descr = chamado.descr;
        novoChamado.local = chamado.local;
        novoChamado.departamento = depto;
        novoChamado.usuario = usuario;
        novoChamado.status = chamado.status;
        novoChamado.dataAbertura = LocalDateTime.now();

        novoChamado.persist();

        return novoChamado;
    }
}
