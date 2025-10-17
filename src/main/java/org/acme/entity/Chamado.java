package org.acme.entity;

import java.time.LocalDateTime;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.smallrye.common.constraint.NotNull;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class Chamado extends PanacheEntity {

    @NotNull
    public String title;

    @NotNull
    public String descr;

    @NotNull
    public String local;

    @NotNull
    @ManyToOne
    public Departamento departamento;

    @ManyToOne
    public Usuario usuario;

    public String tecnico;

    @NotNull
    public String status;

    public LocalDateTime dataAbertura;

    public LocalDateTime dataFechamento;
}
