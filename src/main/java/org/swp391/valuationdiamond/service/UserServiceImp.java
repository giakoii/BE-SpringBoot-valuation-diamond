package org.swp391.valuationdiamond.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.swp391.valuationdiamond.dto.UserDTO;
import org.swp391.valuationdiamond.entity.primary.PendingUser;
import org.swp391.valuationdiamond.entity.primary.Role;
import org.swp391.valuationdiamond.entity.primary.User;
import org.swp391.valuationdiamond.repository.primary.PendingUserRepository;
import org.swp391.valuationdiamond.repository.primary.UserRepository;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImp {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private PendingUserRepository pendingUserRepository;

    @NonFinal
    public static final String  SIGNER_KEY = "loJB7k9HBo3Fm3spN+I7TV5Dkx8OyznG2cnitNEX2rvKGi82q4OnhDzhv3EZkXSA";


    public void createUser(UserDTO userDTO) throws MessagingException {
        if (userRepository.findByUserId(userDTO.getUserId()) != null) {
            throw new IllegalArgumentException("User with ID " + userDTO.getUserId() + " already exists");
        }

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        PendingUser pendingUser = PendingUser.builder()
                .userId(userDTO.getUserId())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .email(userDTO.getEmail())
                .birthday(userDTO.getBirthday())
                .phoneNumber(userDTO.getPhoneNumber())
                .address(userDTO.getAddress())
                .role(Role.valueOf(userDTO.getRole()))
                .build();

        String otp = generateOtp();
        pendingUser.setOtp(otp);
        pendingUser.setOtpCreationTime(LocalDateTime.now());

        pendingUserRepository.save(pendingUser);

        sendOtpEmail(userDTO.getEmail(), otp);
    }

    //hàm confirm email
    @Transactional
    public User confirmEmail(String userId, String otp) {
        PendingUser pendingUser = pendingUserRepository.findByUserId(userId);
        if (pendingUser == null) {
            throw new RuntimeException("OTP not found");
        }
        if (!pendingUser.getOtp().equals(otp)) {
            throw new RuntimeException("OTP is incorrect");
        }

        LocalDateTime otpCreationTime = pendingUser.getOtpCreationTime();
        if (otpCreationTime.isBefore(LocalDateTime.now().minusMinutes(10))) {
            throw new RuntimeException("OTP has expired");
        }

        User user = User.builder()
                .userId(pendingUser.getUserId())
                .password(pendingUser.getPassword())
                .firstName(pendingUser.getFirstName())
                .lastName(pendingUser.getLastName())
                .email(pendingUser.getEmail())
                .birthday(pendingUser.getBirthday())
                .phoneNumber(pendingUser.getPhoneNumber())
                .address(pendingUser.getAddress())
                .role(pendingUser.getRole())
                .build();
        pendingUserRepository.deleteByUserId(userId);

        return userRepository.save(user);
    }

    //hàm đăng nhập
    public User login(String userId, String password) {
        User user = userRepository.findByUserId(userId);
        if (user != null) {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
            if (passwordEncoder.matches(password, user.getPassword())) {
                user.setPassword(null);
                return user;

            } else {
                throw new RuntimeException("Password is incorrect");
            }
        } else {
            throw new RuntimeException("User not found");
        }

    }

    public List<User> getStaffByRoleEvaluationStaff(){

        return userRepository.getUsersByRole(Role.valuation_staff);
    }

    public List<User> getStaff() {
        List<User> staff = new ArrayList<>();

        staff.addAll(userRepository.getUsersByRole(Role.valuation_staff));
        staff.addAll(userRepository.getUsersByRole(Role.consultant_staff));

        return staff;
    }


    public User getStaffById(String id){
        return userRepository.findById(id).orElseThrow(()-> new RuntimeException("Staff not found"));
    }
    public User getAUser(String id){
        return userRepository.findById(id).orElseThrow(()-> new RuntimeException("UserId Not Found"));
    }

    public User updateUser(String userId, UserDTO userDTO){
        User user= userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        if (userDTO.getPassword() != null) {
            user.setPassword(userDTO.getPassword());
        }
        if (userDTO.getFirstName() != null) {
            user.setFirstName(userDTO.getFirstName());
        }
        if (userDTO.getLastName() != null) {
            user.setLastName(userDTO.getLastName());
        }
        if (userDTO.getBirthday() != null) {
            user.setBirthday(userDTO.getBirthday());
        }
        if (userDTO.getPhoneNumber() != null) {
            user.setPhoneNumber(userDTO.getPhoneNumber());
        }
        if (userDTO.getEmail() != null) {
            user.setEmail(userDTO.getEmail());
        }
        if (userDTO.getAddress() != null) {
            user.setAddress(userDTO.getAddress());
        }
        if (userDTO.getRole() != null) {
            user.setRole(Role.valueOf(userDTO.getRole()));
        }
        return userRepository.save(user);
    }

    public boolean deleteUser(String userId) {
        User user= userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        userRepository.delete(user);
        return true;
    }

    public List<User> getCustomers(){
        return userRepository.getUsersByRole(Role.customer);
    }



    //jwt
    private String jenerateJwtToken(String userId){
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(userId)
                .issuer("valuation-diamond")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes(StandardCharsets.UTF_8)));
        } catch (KeyLengthException e) {
            throw new RuntimeException(e);
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }


        return userId;
    }





    //hàm send otp
    public void sendOtpEmail(String email){
        String opt = generateOtp();
        try {
            sendOtpEmail(email, opt);
        } catch (MessagingException e) {
            throw new RuntimeException("Error sending email");
        }

    }

    private String generateOtp(){
        SecureRandom random = new SecureRandom();
        int otp = 1000001 + random.nextInt(9000000);
        return String.valueOf(otp);
    }

    private void sendOtpEmail(String email, String otp) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setTo(email);
        helper.setSubject("OTP");

        String text = "Dear User,\n\n"
                + "Your OTP is: " + otp + "\n\n"
                + "This OTP is valid for 10 minutes. Do not share this OTP with anyone for security reasons.\n\n"
                + "Best regards,\n"
                + "Valuation Diamond";
        helper.setText(text);

        javaMailSender.send(message);
    }


//    public List<User> getStaffs(){
//        Role role = Role.valueOf("valuation_staff".toUpperCase());
//        return userRepository.getUserByRole(role);
//    }




}
