package com.McDiffyStore.store.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Getter@Setter
@Table(name = "user")
public class McUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String username;
    private String password;

   @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
   @JoinTable(name = "user_role",
           joinColumns = {@JoinColumn(name = "user_id")},
           inverseJoinColumns = {@JoinColumn(name = "role_id")}
   )
    private Set<Role> roles;

}
