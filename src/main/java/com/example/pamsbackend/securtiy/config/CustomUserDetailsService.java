package com.example.pamsbackend.securtiy.config;


import com.example.pamsbackend.SystemData;
import com.example.pamsbackend.entity.SystemEntity;
import com.example.pamsbackend.entity.User;
import com.example.pamsbackend.repositorys.SystemEntitiesRepository;
import com.example.pamsbackend.repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final SystemEntitiesRepository systemEntitiesRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository, SystemEntitiesRepository systemEntitiesRepository) {
        this.userRepository = userRepository;
        this.systemEntitiesRepository = systemEntitiesRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<String> entityList = SystemData.getEntityList();
        boolean systemEntity = false;
        // Check if request comes from the system or a user.
        User user = new User();
        for (String s: entityList){
            if (s.equals(username)) {
                systemEntity = true;
                break;
            }
        }

        // Completes system or user check.
        if (systemEntity){
           SystemEntity entity = systemEntitiesRepository.findByUsername(username);
           user.setId(entity.getId());
           user.setUsername(entity.getUsername());
           user.setPassword(entity.getPassword());
           user.setFirstName(entity.getFirstName());
           user.setEnabled(true);
           user.setLocked(false);
           user.setRole(entity.getRole());
        } else {
            user = userRepository.findByUsername(username);
        }

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getEnabled(),
                user.isAccountNonExpired(),
                user.isCredentialsNonExpired(),
                user.isAccountNonLocked(),
                user.getAuthorities()
        );
    }
}
