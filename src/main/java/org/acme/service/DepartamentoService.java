package org.acme.service;

import org.acme.entity.Departamento;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class DepartamentoService {

     @Transactional
    public Departamento criarDP(Departamento departamento) {
        Departamento novoDepartamento = new Departamento();
        novoDepartamento.name = departamento.name;

        novoDepartamento.persist();

        return novoDepartamento;
    }
}


