package org.swp391.valuationdiamond.service;

import jakarta.mail.MessagingException;
import org.swp391.valuationdiamond.dto.UserDTO;
import org.swp391.valuationdiamond.entity.primary.User;

import java.util.List;

public interface IUserService {
    void createUser(UserDTO userDTO) throws MessagingException;
    User confirmEmail(String userId, String otp);
    User login(String userId, String password);
    boolean forgotPassword(String userId) throws MessagingException;
    User resetPassword(String userId, String otp, String newPassword);
    List<User> getStaffByRoleEvaluationStaff();
    List<User> getStaff();
    User getStaffById(String id);
    User getAUser(String id);
    List<User> getCustomers();
    User updateUser(String userId, UserDTO userDTO);
    boolean deleteUser(String userId);
//    String generateJwtToken(String userId);
//    void sendOtpEmail(String email);
//     String generateOtp();
//    void sendOtpEmail(String email, String otp) throws MessagingException;
    boolean deletePendingUser(String userId);
    long countUsers();
}
