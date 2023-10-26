package com.voda.blog.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class SiteUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20, unique = true)
    private String username;
    private String password;
    @Column(length = 10, unique = true)
    private String nickname;
    @Column(unique = true)
    private String email;
    private LocalDateTime createDate;
}
