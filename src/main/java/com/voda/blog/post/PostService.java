package com.voda.blog.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public void create(String subject, String content) {
        Post post = new Post();
        post.setSubject(subject);
        post.setContent(content);
        post.setCreateDate(LocalDateTime.now());
        postRepository.save(post);
    }

    public List<Post> getList() {
        return postRepository.findAll();
    }

    public Post getById(Integer id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) return post.get();
        throw new RuntimeException("게시물이 없습니다.");
    }
}
