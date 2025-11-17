package org.acme.service;

import java.time.LocalDateTime;
import java.util.Optional;

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

    @Transactional
    public Optional<Chamado> atualizarChamado(Long id, ChamadoRequestDTO dadosNovos) {
        Optional<Chamado> chamadoExistenteOpt = Chamado.findByIdOptional(id);

        if(chamadoExistenteOpt.isEmpty()) {
            return Optional.empty();
        }

        Chamado chamadoExistente = chamadoExistenteOpt.get();

        Departamento novoDepartamento = Departamento.findById(dadosNovos.departamentoId);

        chamadoExistente.title = dadosNovos.title;
        chamadoExistente.descr = dadosNovos.descr;
        chamadoExistente.local = dadosNovos.local;
        chamadoExistente.status = dadosNovos.status;
        chamadoExistente.departamento = novoDepartamento;

        return Optional.of(chamadoExistente);
    }

    @Transactional
    public Boolean delCha(Long id) {
        return Chamado.deleteById(id);
    }
}
