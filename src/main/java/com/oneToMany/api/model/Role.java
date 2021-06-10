package com.oneToMany.api.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "role_table")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Role extends AuditModel{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String roleName;

    @Column(nullable = false)
    @Size(min = 25, max = 255)
    private String description;

    @OneToMany(targetEntity = User.class, cascade = CascadeType.ALL)
    private List<User> users;

    public Role(String roleName, String description, List<User> users) {
        this.roleName = roleName;
        this.description = description;
        this.users = users;
    }
}
