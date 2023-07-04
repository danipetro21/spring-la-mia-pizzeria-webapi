package org.lessons.springpizzeria.security;

import org.lessons.springpizzeria.model.User;
import org.lessons.springpizzeria.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {


    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> result = userRepository.findByEmail(username);
        if (result.isPresent()) {

            return new DatabaseUserDetails(result.get());
        } else {

            throw new UsernameNotFoundException("User with email " + username + " not found");
        }
    }
}