package com.example.pamsbackend.securtiy.config;


import com.example.pamsbackend.repositorys.UserRepository;
import com.example.pamsbackend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        // ********** EV koll om det är användarnamn eller mail *************
        User user = userRepository.findByUsername(username);
        System.out.println(user);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        // Load user roles from the database
        System.out.println("CustomUserDetailsService line 47: " + user);
        System.out.println("CustomUserDetailsService line 48: " + user.getRole());
        System.out.println("incoming: " + user.getPassword());
        // Use BCryptPasswordEncoder to handle password encoding and matching
//        String bcryptIdentifier = "$2";
//        String passwordIdentifier = user.getPassword().substring(0,2);
//        System.out.println(bcryptIdentifier);
//        String encodedPassword;
//        if (passwordIdentifier.equals(bcryptIdentifier)) {
//            encodedPassword = user.getPassword();
//        } else {
//            encodedPassword = passwordEncoder.encode(user.getPassword());
//        }
//
//        System.out.println("encoded: "+encodedPassword);
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
//                encodedPassword,
                user.getPassword(),
                user.getEnabled(),
                user.isAccountNonExpired(),
                user.isCredentialsNonExpired(),
                user.isAccountNonLocked(),
                user.getAuthorities()
        );
    }
}
