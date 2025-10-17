package org.acme.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.smallrye.common.constraint.NotNull;
import jakarta.persistence.Entity;

@Entity
public class Departamento extends PanacheEntity {
    @NotNull
    public String name;
}
