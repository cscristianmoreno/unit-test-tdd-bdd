package com.app.test.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Users extends BaseEntity {
    private String name;
    private String lastname;
    private String username;
    private String password;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
        joinColumns = {
            @JoinColumn(referencedColumnName = "id")
        },
        inverseJoinColumns = {
            @JoinColumn(referencedColumnName = "id")
        }
    )
    @Column(nullable = false)
    private List<Authority> authority;
}
