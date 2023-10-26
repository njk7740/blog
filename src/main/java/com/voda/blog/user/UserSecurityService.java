package com.voda.blog.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserSecurityService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<SiteUser> op_user = userRepository.findByUsername(username);
        if (op_user.isEmpty()) throw new UsernameNotFoundException("유저를 찾을 수 없습니다");
        SiteUser user = op_user.get();
        List<GrantedAuthority> userRoles = new ArrayList<>();
        if (user.getUsername().equals("njk7740")) userRoles.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        else userRoles.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        return new User(user.getUsername(), user.getPassword(), userRoles);
    }
}
