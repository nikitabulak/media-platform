package com.nikita.bulak.mediaplatform.user.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    @Email
    private String email;
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "subscriptions",
            joinColumns = {@JoinColumn(name = "subscriber_id")},
            inverseJoinColumns = {@JoinColumn(name = "author_id")}
    )
    private Set<User> authors = new HashSet<>();
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "friends",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "friend_id")}
    )
    private Set<User> friends = new HashSet<>();
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "friendship_requests",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "requesting_friend_id")}
    )
    private Set<User> requestedFriends = new HashSet<>();
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "friendship_requests",
            joinColumns = {@JoinColumn(name = "requesting_friend_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private Set<User> requestingFriends = new HashSet<>();
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private Set<Role> roles;

    public User(String username, String password, String email) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
