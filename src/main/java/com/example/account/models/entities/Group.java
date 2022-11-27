package com.example.account.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "principle_groups")
@Data
@NoArgsConstructor
public class Group {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String role;

    @ManyToMany(mappedBy = "userGroups" )
    private Set<User> users;

    public Group(String role){
        this.role = role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getName() {
        return "ROLE_"+role;
    }
}
