package org.swp391.valuationdiamond.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import org.swp391.valuationdiamond.config.CustomDateDeserializer;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EvaluationRequestDTO {
  String requestDescription;

  @JsonDeserialize(using = CustomDateDeserializer.class)
  @DateTimeFormat(pattern = "MM/dd/yyyy, HH:mm")
  Date requestDate;

  @NotBlank
  String requestEmail;

  @NotBlank
  String guestName;

  String status;

  @NotBlank
  String service;

  @NotBlank
  String phoneNumber;

  String userId;

  @JsonDeserialize(using = CustomDateDeserializer.class)
  @DateTimeFormat(pattern = "MM/dd/yyyy, HH:mm")
  Date meetingDate;
}
