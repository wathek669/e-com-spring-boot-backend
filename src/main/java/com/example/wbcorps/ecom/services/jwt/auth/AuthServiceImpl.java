package com.example.wbcorps.ecom.services.jwt.auth;

import com.example.wbcorps.ecom.dto.SignupRequest;
import com.example.wbcorps.ecom.dto.UserDto;
import com.example.wbcorps.ecom.entity.User;
import com.example.wbcorps.ecom.enums.UserRole;
import com.example.wbcorps.ecom.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static java.rmi.server.LogStream.log;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService{
    @Autowired
    private UserRepository userRepository;
    /*
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }*/
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(SignupRequest signupRequest){
        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setRole(UserRole.CUSTOMER);
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        ;
        return userRepository.save(user);


    }

    public Boolean hasUserWithEmail(String email){
        return userRepository.findFirstByEmail(email).isPresent();
    }

    @PostConstruct
    public void createAdminAccount(){
        User adminAccount=userRepository.findByRole(UserRole.ADMIN);
        if (adminAccount==null){
            User user = new User();
            user.setEmail("admin@test.com");
            user.setName("admin");
            user.setRole(UserRole.ADMIN);
            user.setPassword(passwordEncoder.encode("admin"));
            userRepository.save(user);
        }

    }


}
