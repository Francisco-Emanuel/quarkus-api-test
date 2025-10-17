package org.acme.entity;

import java.util.UUID;

import org.hibernate.validator.constraints.UniqueElements;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;

@Entity
public class Usuario extends PanacheEntity {
    
    @NotNull
    @UniqueElements
    public String name;

    @NotNull
    public String password;

    @UniqueElements
    public String authToken;

    public void generateToken() {
        this.authToken = UUID.randomUUID().toString();
    }
}
