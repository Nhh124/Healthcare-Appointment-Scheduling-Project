package com.fx21314.asm3.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "roles")
public class Role {

    @Id
    private int id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;


}