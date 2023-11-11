package com.sidof.task.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sidof.task.model.Task;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.SEQUENCE;

/**
 * @Author sidof
 * @Since 10/2/23
 * @Version v1.0
 * @YouTube @sidof8065
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Appuser implements UserDetails {
    @SequenceGenerator(name = "sequence_id_appuser", allocationSize = 1, sequenceName = "sequence_id_appuser")
    @GeneratedValue(strategy = SEQUENCE, generator = "sequence_id_appuser")
    @Id
    private Long id;
    private String name;
    private String email;
    private String password;
    @Enumerated(STRING)
    private Role role;
    @JsonIgnore
    @OneToMany(mappedBy = "appuser", fetch = EAGER)
    private List<Token> tokens = new ArrayList<>();
    @OneToMany(mappedBy = "assignee")
    private List<Task> tasks;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.name()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
