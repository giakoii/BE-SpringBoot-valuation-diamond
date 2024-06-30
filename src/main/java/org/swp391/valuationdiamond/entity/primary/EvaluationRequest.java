package org.swp391.valuationdiamond.entity.primary;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tbl_Evaluation_Request")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EvaluationRequest {

  @Id
  @Column(name = "request_id", nullable = false, length = 255)
  String requestId;

  @Column(name = "request_description", nullable = true, length = 255)
  String requestDescription;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "request_date", nullable = true)
  Date requestDate;

  @Column(name = "request_email", nullable = false, length = 255)
  String requestEmail;

  @Column(name = "guest_name", nullable = false, length = 255)
  String guestName;

  @Column(name = "status", nullable = true, length = 255)
  String status;

  @Column(name = "service", nullable = true, length = 255)
  String service;

  @Column(name = "phone_number", nullable = false, length = 255)
  String phoneNumber;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "meeting_date", nullable = true)
  Date meetingDate;

  @JsonManagedReference
  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "user_id")
  User userId;

  @JsonBackReference
  @OneToMany(mappedBy = "requestId")
  List<Order> orders;



}
