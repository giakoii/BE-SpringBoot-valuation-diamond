package org.swp391.valuationdiamond.controller;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.swp391.valuationdiamond.dto.UserDTO;
import org.swp391.valuationdiamond.entity.primary.User;
import org.swp391.valuationdiamond.service.UserServiceImp;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user_request")
public class UserController {
    @Autowired
    private UserServiceImp userServiceImp;

    // =================================== API CREATE ======================================

    //hàm đăng ký thông thường
    @PostMapping("/create")
    User createCustomer(@RequestBody UserDTO userDTO){

        return userServiceImp.createUser(userDTO);
    }

    @PostMapping("/sendotp/{email}")
    public String sendOtpEmail(@PathVariable("email") String email) {
        userServiceImp.sendOtpEmail(email);

        return "OTP sent to email successfully";
    }

    // =================================== API LOGIN ======================================

    @PostMapping("/login")
    public User login(@RequestBody Map<String, String> loginRequest) {
        String userId = loginRequest.get("userId");
        String password = loginRequest.get("password");
        return userServiceImp.login(userId, password);
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
    public void deleteUser(@PathVariable("userId") String userId,  @RequestBody UserDTO userDTO){
        if (userId == null || userId.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid userId");
        }
        if (userDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid userDTO");
        }
        try {
            userServiceImp.deleteUser(userId);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while deleting the user");

        }

    }


}

