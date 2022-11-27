package com.example.account.models.entities;

import com.example.account.models.infrastracture.BreachedPasswordValidation;
import com.example.account.models.infrastracture.MinPasswordSizeValidation;
import com.example.account.models.infrastracture.PasswordNotNullValidation;
import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;



import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements Comparable<User>{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String lastname;

    @NotBlank
    @Column(unique = true)
    @Pattern(regexp = "\\w+@acme.com")
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @PasswordNotNullValidation
    @MinPasswordSizeValidation
    @BreachedPasswordValidation
    private String password;

    @Column(name = "account_non_locked")
    private boolean accountNonLocked = true;

    @Column(name = "failed_attempt")
    private int failedAttempt = 0;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Payroll> payroll;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "user_groups", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<Group> userGroups = new HashSet<>();

    public void addUserGroup(Group group){
        userGroups.add(group);
        group.getUsers().add(this);
    }

    public void removeUserGroup(Group group){
        userGroups.remove(group);
    }

    public void removeUserGroups(){
        userGroups.removeAll(userGroups);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public int compareTo(User o) {
        return id.compareTo(o.id);
    }
}