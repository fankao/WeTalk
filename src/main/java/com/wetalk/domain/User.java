package com.wetalk.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "profile_picture")
    private String profilePicture;

    @Column(name = "last_login_time")
    private Date lastLoginTime;

    @OneToMany(mappedBy = "sender", fetch = FetchType.EAGER)
    private Set<Message> sentMessages;

    @OneToMany(mappedBy = "recipient", fetch = FetchType.EAGER)
    private Set<Message> receivedMessages;

    @ManyToMany(mappedBy = "participants")
    private Set<ChatRoom> chatRooms;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private RoleEnum role = RoleEnum.USER;

    public User(String username, String email, String password, String profilePicture) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.profilePicture = profilePicture;
    }
}
