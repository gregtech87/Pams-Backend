//package com.example.pamsbackend.service;
//
//import com.example.pamsbackend.dao.UserRepository;
//import com.example.pamsbackend.entity.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Component;
//
//@Component
//public class SecUserDetailsService implements UserDetailsService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        /*Here add user data layer fetching from the MongoDB.
//          I have used userRepository*/
//        User user = userRepository.findByUsername(username);
//        if(user == null){
//            throw new UsernameNotFoundException(username);
//        }else{
//            UserDetails details = new SecUserDetails(user);
//            System.out.println(details);
//            return details;
//        }
//    }
//}