package com.app.test.entity;

import com.app.test.roles.Roles;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Authority extends BaseEntity {
    
    @Enumerated(EnumType.STRING)
    private Roles roles;
}
