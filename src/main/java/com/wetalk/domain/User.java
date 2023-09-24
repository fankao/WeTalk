package com.wetalk.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
}
