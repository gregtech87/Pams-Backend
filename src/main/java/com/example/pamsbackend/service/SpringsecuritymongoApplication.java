//package com.example.pamsbackend.service;
//import com.example.pamsbackend.dao.RoleRepository;
//import com.example.pamsbackend.entity.Role;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
//@SpringBootApplication
//public class SpringsecuritymongoApplication {
//
//    public static void main(String[] args) {
//        SpringApplication.run(SpringsecuritymongoApplication.class, args);
//    }
//
//    @Bean
//    CommandLineRunner init(RoleRepository roleRepository) {
//
//        return args -> {
//
//            Role adminRole = roleRepository.findByRole("ADMIN");
//            if (adminRole == null) {
//                Role newAdminRole = new Role();
//                newAdminRole.setRole("ADMIN");
//                roleRepository.save(newAdminRole);
//            }
//
//            Role userRole = roleRepository.findByRole("USER");
//            if (userRole == null) {
//                Role newUserRole = new Role();
//                newUserRole.setRole("USER");
//                roleRepository.save(newUserRole);
//            }
//        };
//
//    }
//
//}