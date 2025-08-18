package io.github.antonio.backnotfront.ratelimiter.service.impl;

import io.github.antonio.backnotfront.ratelimiter.model.User;
import io.github.antonio.backnotfront.ratelimiter.model.UserPrincipal;
import io.github.antonio.backnotfront.ratelimiter.repository.impl.UserRepository;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final CacheManager cacheManager;

    private final UserRepository repository;

    public UserDetailsServiceImpl(CacheManager cacheManager, UserRepository repository) {
        this.cacheManager = cacheManager;
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Cache userCache = cacheManager.getCache("USERS");
        Cache.ValueWrapper cachedUser = userCache.get(username);
        if (cachedUser != null) {
            return new UserPrincipal((User)(cachedUser.get()));
        }
        Optional<User> userOptional = repository.findByEmail(username);
        if (!userOptional.isPresent())
            throw new UsernameNotFoundException(String.format("User with username '%s' not found.", username));
        userCache.put(username, userOptional.get());
        return new UserPrincipal(userOptional.get());
    }

}
