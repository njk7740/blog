package com.voda.blog.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<SiteUser, Long> {
    public Optional<SiteUser> findByUsername(String username);
}
