package org.acme.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Departamento extends PanacheEntity {

    @Column(unique = true, nullable = false)
    public String name;
}
