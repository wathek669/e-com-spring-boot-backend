package com.example.wbcorps.ecom.services.jwt.auth;

import com.example.wbcorps.ecom.dto.SignupRequest;
import com.example.wbcorps.ecom.dto.UserDto;
import com.example.wbcorps.ecom.entity.User;
import com.example.wbcorps.ecom.enums.UserRole;
import com.example.wbcorps.ecom.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service

public class AuthServiceImpl implements AuthService{
    @Autowired
    private UserRepository userRepository;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public UserDto createUser(SignupRequest signupRequest){
        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        user.setName(signupRequest.getName());
        user.setRole(UserRole.CUSTOMER);
        User createdUser=userRepository.save(user);
        UserDto userDto=new UserDto();
        userDto.setId(createdUser.getId());
        return userDto;
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
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userRepository.save(user);
        }

    }


}
