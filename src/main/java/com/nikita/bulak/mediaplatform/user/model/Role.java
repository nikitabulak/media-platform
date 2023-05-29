package com.nikita.bulak.mediaplatform.user.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    private Long id;
    @Enumerated(EnumType.STRING)
    private ERole name;
//    @Transient
//    @ManyToMany(mappedBy = "roles")
//    private Set<User> users;

    public Role(Long id) {
        this.id = id;
    }

    public Role(ERole name) {
        this.name = name;
    }

    public Role(Long id, ERole name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return getName().name();
    }
}

