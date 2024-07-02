package org.swp391.valuationdiamond.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.swp391.valuationdiamond.dto.UserDTO;
import org.swp391.valuationdiamond.entity.primary.User;
import org.swp391.valuationdiamond.service.UserServiceImp;


import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@RestController
@RequestMapping("/user_request")
public class UserController {
    @Autowired
    private UserServiceImp userServiceImp;

    // =================================== API CREATE ======================================

    //hàm đăng ký thông thường
    @PostMapping("/create")
     void createCustomer(@Valid @RequestBody UserDTO userDTO) throws MessagingException {
        userServiceImp.createUser(userDTO);
    }

    //
    @PostMapping("/confirm-email")
    public ResponseEntity<?> confirmEmail(@RequestBody Map<String, String> request) {
        String userId = request.get("userId");
        String otp = request.get("otp");
        return ResponseEntity.ok(userServiceImp.confirmEmail(userId, otp));


    }

//    @PostMapping("/sendotp/{email}")
//    public String sendOtpEmail(@PathVariable("email") String email) {
//        userServiceImp.sendOtpEmail(email);
//
//        return "OTP sent to email successfully";
//    }

    // =================================== API LOGIN ======================================

    @PostMapping("/login")
    public User login(@RequestBody Map<String, String> loginRequest) {
        String userId = loginRequest.get("userId");
        String password = loginRequest.get("password");
        return userServiceImp.login(userId, password);
    }

    // =================================== API FORGOT PASSWORD ======================================
    //Api gửi OTP tới email để reset password
    @PostMapping("/forgot-password")
    public boolean forgotPassword(@RequestBody Map<String, String> request) throws MessagingException {
        String userId = request.get("userId");
        return userServiceImp.forgotPassword(userId);
    }

    //Api reset password
    @PostMapping("/reset-password")
    public User resetPassword(@RequestBody Map<String, String> request) {
        String userId = request.get("userId");
        String otp = request.get("otp");
        String newPassword = request.get("newPassword");
        return userServiceImp.resetPassword(userId, otp, newPassword);
    }
    // =================================== API GET ======================================

    // API get user by userId
    @GetMapping("/getUser/{userId}")
    User getStaff(@PathVariable("userId") String userId){

        return userServiceImp.getStaffById(userId);
    }
    @GetMapping("/getStaff")
    List<User> getStaffs(){

        return userServiceImp.getStaffByRoleEvaluationStaff();
    }
    // API get all staffs
    @GetMapping("/getAllStaff")
    List<User> getAllStaffs(){

        return userServiceImp.getStaff();
    }

    // API get all customers
    @GetMapping("/getCustomer")
    List<User> getCustomer(){

        return userServiceImp.getCustomers();
    }

    // API get a user by userId
    @GetMapping("/getAUser/{userId}")
        User getAUser(@PathVariable("userId") String userId ){

        return userServiceImp.getAUser(userId);
        }

    // =================================== API UPDATE ======================================

    @PutMapping("/updateUser/{userId}")
    public User updateUser(@PathVariable("userId") String userId, @RequestBody UserDTO userDTO) {
        return userServiceImp.updateUser(userId, userDTO);
    }


    // =================================== API DELETE ======================================
    @DeleteMapping("/deleteUser/{userId}")
    public boolean deleteUser(@PathVariable("userId") String userId){
        return userServiceImp.deleteUser(userId);
    }

    @DeleteMapping("/deletePendingUser/{userId}")
    public boolean deletePendingUser(@PathVariable("userId") String userId){
        return userServiceImp.deletePendingUser(userId);
    }
    // Number only
    @GetMapping("/countUsers")
    public long countUsers() {
        return userServiceImp.countUsers();
    }

    //"totalUser": count
    @GetMapping("/totalUserCount")
    public UserServiceImp.UserCountResponse getTotalUserCount() {
        long totalUserCount = userServiceImp.countUsers();
        return new UserServiceImp.UserCountResponse(totalUserCount);
    }
}

