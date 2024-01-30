package com.example.pamsbackend.service;

import com.example.pamsbackend.dao.UserRepository;
import com.example.pamsbackend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class MongoAuthUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public MongoAuthUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findUserByUsername(userName);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

//        return new User(user.get().getUsername(), user.get().getPassword(), grantedAuthorities);
//
        user.get().getAuthorities()
                .forEach(role -> grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole().getName())));
//
        return new User(user.get().getUsername(), user.get().getPassword(), grantedAuthorities);
    }
}