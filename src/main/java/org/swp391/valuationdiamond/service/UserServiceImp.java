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
public class UserServiceImp implements IUserService {
    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;
    private final PendingUserRepository pendingUserRepository;

    @Autowired
    public UserServiceImp(UserRepository userRepository, JavaMailSender javaMailSender, PendingUserRepository pendingUserRepository) {
        this.userRepository = userRepository;
        this.javaMailSender = javaMailSender;
        this.pendingUserRepository = pendingUserRepository;
    }

    @NonFinal
    public static final String  SIGNER_KEY = "loJB7k9HBo3Fm3spN+I7TV5Dkx8OyznG2cnitNEX2rvKGi82q4OnhDzhv3EZkXSA";


    //=============================== Các hàm liên quan tới tạo, login ==========================================
    @Override
    public void createUser(UserDTO userDTO) throws MessagingException {
        if (userRepository.findByUserId(userDTO.getUserId()) != null || pendingUserRepository.findByUserId(userDTO.getUserId()) != null){
            throw new IllegalArgumentException("User with ID " + userDTO.getUserId() + " already exists");
        }
        if (userRepository.findByEmail(userDTO.getEmail()) != null || pendingUserRepository.findByEmail(userDTO.getEmail()) != null){
            throw new IllegalArgumentException("User with email " + userDTO.getEmail() + " already exists");
        }
        if(userDTO.getBirthday().toInstant().isAfter(Instant.now())){
            throw new IllegalArgumentException("Birthday is invalid");
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

    //hàm tạo account cho staff
    @Override
    public User createStaff(UserDTO userDTO){
        if (userRepository.findByUserId(userDTO.getUserId()) != null){
            throw new IllegalArgumentException("User with ID " + userDTO.getUserId() + " already exists");
        }
        if(userDTO.getBirthday().toInstant().isAfter(Instant.now())){
            throw new IllegalArgumentException("Birthday is invalid");
        }

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        User user = User.builder()
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

        return userRepository.save(user);
    }
    //change password
    @Override
    public User changePassword(String userId, String oldPassword, String newPassword){
        User user = userRepository.findByUserId(userId);
        if(user == null){
            throw new RuntimeException("User not found");
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        if(passwordEncoder.matches(oldPassword, user.getPassword())){
            user.setPassword(passwordEncoder.encode(newPassword));
            return userRepository.save(user);
        }
        else {
            throw new RuntimeException("Password is incorrect");
        }
    }

    //hàm confirm email
    @Transactional
    @Override
    public User confirmEmail(String userId, String otp) {
        PendingUser pendingUser = pendingUserRepository.findByUserId(userId);
        if (pendingUser == null) {
            throw new RuntimeException("OTP not found");
        }
        if (!pendingUser.getOtp().equals(otp)) {
            throw new RuntimeException("OTP is incorrect");
        }

        LocalDateTime otpCreationTime = pendingUser.getOtpCreationTime();
        if (otpCreationTime.isBefore(LocalDateTime.now().minusMinutes(5))) {
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
    @Override
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

    //hàm forgot password --> Gui email chứa OTP
    @Override
    public boolean forgotPassword(String userId) throws MessagingException {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        if (pendingUserRepository.findByUserId(userId) != null) {
            throw new RuntimeException("OTP has already been sent");
        }

        String otp = generateOtp();

        sendOtpEmail(user.getEmail(), otp);
        PendingUser pendingUser = PendingUser.builder()
                .userId(user.getUserId())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .birthday(user.getBirthday())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .role(user.getRole())
                .otp(otp)
                .otpCreationTime(LocalDateTime.now())
                .build();
        pendingUserRepository.save(pendingUser);
        return true;
    }

    //hàm reset password
    @Override
    @Transactional
    public User resetPassword(String userId, String otp, String newPassword) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        if (!pendingUserRepository.findByUserId(userId).getOtp().equals(otp)) {
            throw new RuntimeException("OTP is incorrect");
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        pendingUserRepository.deleteByUserId(userId);

        return user;
    }

    //=============================== Các hàm GET ==========================================
    @Override
    public List<User> getStaffByRoleEvaluationStaff(){

        return userRepository.getUsersByRole(Role.valuation_staff);
    }
    @Override
    public List<User> getStaff() {
        List<User> staff = new ArrayList<>();

        staff.addAll(userRepository.getUsersByRole(Role.valuation_staff));
        staff.addAll(userRepository.getUsersByRole(Role.consultant_staff));

        return staff;
    }
    @Override
    public User getStaffById(String id){
        return userRepository.findById(id).orElseThrow(()-> new RuntimeException("Staff not found"));
    }
    @Override
    public User getAUser(String id){
        return userRepository.findById(id).orElseThrow(()-> new RuntimeException("UserId Not Found"));
    }
    @Override
    public List<User> getCustomers(){
        return userRepository.getUsersByRole(Role.customer);
    }


    //=============================== Các hàm UPDATE và DELETE ==========================================
    @Override
    public User updateUser(String userId, UserDTO userDTO){
        User user= userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        if (userDTO.getBirthday().toInstant().isAfter(Instant.now())){
            throw new IllegalArgumentException("Birthday is invalid");
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        if (userDTO.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
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
    @Override
    public boolean deleteUser(String userId) {
        User user= userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        userRepository.delete(user);
        return true;
    }


    //=============================== Các hàm liên quan tới OTP và JWT ==========================================
    //jwt
    private String generateJwtToken(String userId){
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
        int otp = 100001 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    private void sendOtpEmail(String email, String otp) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setTo(email);
        helper.setSubject("OTP");

        String text = "Dear User,\n\n"
                + "Your OTP is: " + otp + "\n\n"
                + "This OTP is valid for 5 minutes. Do not share this OTP with anyone for security reasons.\n\n"
                + "Best regards,\n"
                + "Valuation Diamond";
        helper.setText(text);

        javaMailSender.send(message);
    }

    //delete PendingUser
    @Override
    public boolean deletePendingUser(String userId){
        PendingUser pendingUser = pendingUserRepository.findByUserId(userId);
        if (pendingUser == null) {
            throw new RuntimeException("User not found");
        }
        pendingUserRepository.delete(pendingUser);
        return true;
    }
    public static class UserCountResponse {
        private long totalUser;

        public UserCountResponse(long totalUser) {
            this.totalUser = totalUser;
        }

        public long getTotalUser() {
            return totalUser;
        }

        public void setTotalUser(long totalUser) {
            this.totalUser = totalUser;
        }

        @Override
        public String toString() {
            return "UserCountResponse{" +
                    "Total User=" + totalUser +
                    '}';
        }
    }
    @Override
    public long countUsers() {
        return userRepository.count();
    }
}
