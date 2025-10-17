package org.acme.entity;

import java.util.UUID;

import org.hibernate.validator.constraints.UniqueElements;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;

@Entity
public class Usuario extends PanacheEntity {
    
    @NotNull
    @Column(unique = true)
    public String name;

    @NotNull
    public String password;

    @Column(unique = true)
    public String authToken;

    public void generateToken() {
        this.authToken = UUID.randomUUID().toString();
    }
}
