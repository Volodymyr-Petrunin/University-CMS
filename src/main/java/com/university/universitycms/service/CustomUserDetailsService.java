package com.university.universitycms.service;

import com.university.universitycms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        com.university.universitycms.domain.User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User with email " + email + " was not found.");
        }

        return new UserDetailsImpl(user);
    }
}
