package org.swp391.valuationdiamond.entity.primary;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tbl_User")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

  @Id
  @Column(name = "user_id", nullable = false, length = 255)
  String userId;

  @Column(name = "password", nullable = true, length = 20)
  String password;

  @Column(name = "first_name", nullable = true, length = 255)
  String firstName;

  @Column(name = "last_name", nullable = true, length = 255)
  String lastName;

  @Column(name = "birthday", nullable = true)
  Date birthday;

  @Column(name = "phone_number", nullable = true, length = 20)
  String phoneNumber;

  @Column(name = "email", nullable = true, length = 100)
  String email;

  @Column(name = "address", nullable = true, length = 255)
  String address;

  @Column(name = "role", nullable = true, length = 50)
  @Enumerated(EnumType.STRING)
  Role role;

  @Column(name = "otp", nullable = true)
  String otp;

  @Column(name = "otp_creation_time", nullable = true)
  LocalDateTime otpCreationTime;

  @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
  List<Rating> ratings;

  @JsonBackReference
  @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
  List<EvaluationRequest> evaluationRequests;

  @JsonIgnore
  @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
  List<Order> orderId;

  @JsonIgnore
  @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
  List<EvaluationResult> evaluationResults;
  @JsonIgnore
  @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
  List<CommittedPaper> committedPapers;


}
