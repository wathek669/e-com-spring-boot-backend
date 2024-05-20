package com.example.wbcorps.ecom.controllers;

import com.example.wbcorps.ecom.dto.AuthenticationRequest;
import com.example.wbcorps.ecom.dto.SignupRequest;
import com.example.wbcorps.ecom.dto.UserDto;
import com.example.wbcorps.ecom.entity.User;
import com.example.wbcorps.ecom.repository.UserRepository;
import com.example.wbcorps.ecom.services.jwt.auth.AuthService;
import com.example.wbcorps.ecom.services.jwt.auth.AuthServiceImpl;
import com.example.wbcorps.ecom.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final AuthServiceImpl authService;
    public static final String HEADER_STRING ="Authorization";
    public static final String TOKEN_PREFIX="BEARER ";


    @RequestMapping("authenticate")
    public void createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest,
                                          HttpServletResponse response) throws IOException, JSONException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(),authenticationRequest.getPassword()));

        } catch (BadCredentialsException e){
            throw new BadCredentialsException("Incorrect username or Password !");
        }
        final UserDetails userDetails= userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername()) ;
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        if (optionalUser.isPresent()){
            response.getWriter().write(new JSONObject()
                    .put("userId",optionalUser.get().getId())
                    .put("role",optionalUser.get().getRole())
                    .toString()
            );
            response.addHeader("Access-Control-Expose-Headers","Authorization");
            response.addHeader("Access-Control-Allow-Headers","Authorization, X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept, X-Custom-header ");
            response.addHeader(HEADER_STRING,TOKEN_PREFIX+jwt);
        }
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signupRequest(@RequestBody SignupRequest signupRequest){
        log.info("sdfgdfgdfg");
        if ( authService.hasUserWithEmail(signupRequest.getEmail()) ){
            return new ResponseEntity<>("User Already exist ", HttpStatus.NOT_ACCEPTABLE);
        }
        UserDto user = authService.createUser(signupRequest);
        return new ResponseEntity<>(user,HttpStatus.CREATED);
    }

    }

