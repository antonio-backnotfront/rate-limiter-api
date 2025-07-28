package io.github.antonio.backnotfront.ratelimiter.service.impl;

import io.github.antonio.backnotfront.ratelimiter.model.User;
import io.github.antonio.backnotfront.ratelimiter.model.UserPrincipal;
import io.github.antonio.backnotfront.ratelimiter.repository.impl.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    public MyUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> user = repository.findByUsername(username);
        if (!user.isPresent())
            throw new UsernameNotFoundException(String.format("User with username '%s' not found.", username));
        return new UserPrincipal(user.get());
    }

}
