package com.voda.blog.user;

import com.voda.blog.post.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

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
    private String email;
    private LocalDateTime createDate;
    @OneToMany(mappedBy = "author")
    private List<Post> postList;
}
