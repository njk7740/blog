package com.voda.blog.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUser getByUsername(String username) {
        Optional<SiteUser> user = userRepository.findByUsername(username);
        if (user.isPresent()) return user.get();
        throw new RuntimeException("유저 찾기 실패");
    }

    public void create(String username, String password, String email, String nickname) {
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setNickname(nickname);
        user.setCreateDate(LocalDateTime.now());
        userRepository.save(user);
    }
}
