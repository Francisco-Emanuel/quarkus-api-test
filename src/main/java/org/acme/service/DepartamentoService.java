package org.acme.service;

import java.util.Optional;

import org.acme.entity.Departamento;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@ApplicationScoped
public class DepartamentoService {

    @Transactional
    public Departamento criarDP(Departamento departamento) {
        Departamento novoDepartamento = new Departamento();
        novoDepartamento.name = departamento.name;

        novoDepartamento.persist();

        return novoDepartamento;
    }

    @Transactional
    public Optional<Departamento> atualizarDepartamento(Long id, @Valid Departamento dadosNovos) {
        Optional<Departamento> departamentoExistenteOpt = Departamento.findByIdOptional(id);
        
        if(departamentoExistenteOpt.isEmpty()) {
            return Optional.empty();
        }

        Departamento departamentoExistente = departamentoExistenteOpt.get();

        departamentoExistente.name = dadosNovos.name;

        return Optional.of(departamentoExistente);
    }

    @Transactional
    public Boolean deletarDP(Long id) {
        return Departamento.deleteById(id);
    }
}
