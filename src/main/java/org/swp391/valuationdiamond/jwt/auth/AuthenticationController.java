//package org.swp391.valuationdiamond.jwt.auth;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.swp391.valuationdiamond.jwt.auth.RegisterRequest;
//import org.swp391.valuationdiamond.jwt.auth.AuthenticationService;
//
//@RestController
//@RequestMapping("/api/v1/auth")
//@RequiredArgsConstructor
//public class AuthenticationController {
//
//    private final AuthenticationService service;
//
//    @PostMapping("/register")
//    public ResponseEntity<org.swp391.demojwt.auth.AuthenticationRespone> register(@RequestBody RegisterRequest request){
//        return ResponseEntity.ok(service.register(request));
//    }
//
//    @PostMapping("/authenticate")
//    public ResponseEntity<org.swp391.demojwt.auth.AuthenticationRespone> authenticate(@RequestBody org.swp391.demojwt.auth.AuthenticationRequest request){
//        return ResponseEntity.ok(service.authenticate(request));
//    }
//}
