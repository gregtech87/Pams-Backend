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
        System.out.println(username);

        List<String> entityList = SystemData.getEntityList();

        boolean systemEntity = false;
        User user = new User();
        for (String s: entityList){
            if (s.equals(username)) {
                systemEntity = true;
                break;
            }
        }

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

        System.out.println(user);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        // Load user roles from the database
        System.out.println("CustomUserDetailsService line 65: " + user);
        System.out.println("CustomUserDetailsService line 66: " + user.getRole());
        System.out.println("incoming: " + user.getPassword());

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
