package com.oneToMany.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user_table")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User extends AuditModel{
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    @Size(min = 8, max = 32)
    private String userName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    @Size(min = 8, max = 32)
    private String password;

    @JsonIgnore
    @ManyToOne
    private Role role;
}
