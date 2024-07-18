package org.swp391.valuationdiamond.entity.primary;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tbl_Order_Details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetail {

  @Id
  @Column(name = "order_de_id", nullable = false, length = 255)
  String orderDetailId;

  @Column(name = "evaluation_staff_id", nullable = true)
  String evaluationStaffId;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "received_date", nullable = true)
  Date receivedDate;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "expired_received_date", nullable = true)
  Date expiredReceivedDate;

  @Column(name = "unit_price", nullable = true)
  Float unitPrice;
  @Column(name = "img", nullable = true, columnDefinition = "NVARCHAR(MAX)")
  String img;
  @Column(name = "size", nullable = true)
  Float size;
  @Column(name = "is_diamond", nullable = true)
  Boolean isDiamond;
  @Column(name = "status", nullable = true, length = 255)
  @Enumerated(EnumType.STRING)
  Status status;


  @JsonManagedReference
  @ManyToOne
  @JoinColumn(name = "order_id", nullable = true)
  Order orderId;

  @JsonIgnore
  @OneToMany(mappedBy = "orderDetailId", cascade = CascadeType.ALL)
  List<EvaluationResult> evaluationResults;

  @JsonManagedReference
  @ManyToOne
  @JoinColumn(name = "service_id", nullable = true)
  EvaluationService serviceId;

}

