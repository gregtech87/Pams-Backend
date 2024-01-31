package com.example.pamsbackend.service;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.pamsbackend.dao.RoleRepository;
import com.example.pamsbackend.dao.UserRepository;
import com.example.pamsbackend.entity.Role;
import com.example.pamsbackend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author didin
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        User user = userRepository.findByUsername(username);
        System.out.println(user);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        // Load user roles from the database
        Set<Role> roles = user.getRoles();
        List<GrantedAuthority> authorities = new ArrayList<>();
        System.out.println(user);
        System.out.println(roles.toString());
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
        }

        // Use BCryptPasswordEncoder to handle password encoding and matching
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        System.out.println();
        System.out.println(username);
        System.out.println(roles.toString());
        System.out.println(encodedPassword);
        System.out.println(authorities);
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                encodedPassword,
                authorities
        );
    }
}
