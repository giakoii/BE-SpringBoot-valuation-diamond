//package org.swp391.valuationdiamond.jwt.auth;
//
//import lombok.Data;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.swp391.valuationdiamond.jwt.*;
//import org.swp391.valuationdiamond.entity.Role;
//import org.swp391.valuationdiamond.entity.User;
//import org.swp391.valuationdiamond.repository.UserRepository;
//import org.swp391.valuationdiamond.jwt.auth.RegisterRequest;
//import org.swp391.valuationdiamond.jwt.auth.AuthenticationRespone;
//import org.swp391.valuationdiamond.jwt.auth.AuthenticationRequest;
//
//
//@Service
//@RequiredArgsConstructor
//@Data
//public class AuthenticationService {
//
//    private final UserRepository userRepository;
//
//    private final PasswordEncoder passwordEncoder;
//    private final JwtService  jwtService;
//    private final AuthenticationManager authenticationManager;
//
//    public AuthenticationRespone register(RegisterRequest request) {
//        var user = User.builder()
//                .email(request.getEmail())
//                .password(passwordEncoder.encode(request.getPassword()))
//                .role(Role.USER)
//                .build();
//        userRepository.save(user);
//
//        var jwtToken = jwtService.generateToken(user);
//
//        return AuthenticationRespone.builder()
//                .token(jwtToken)
//                .build();
//    }
//
//
//    public AuthenticationRespone authenticate(AuthenticationRequest request) {
//        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
//        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
//        return null;
//    }
//}
