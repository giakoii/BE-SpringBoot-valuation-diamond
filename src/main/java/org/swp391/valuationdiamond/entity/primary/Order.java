package org.swp391.valuationdiamond.entity.primary;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tbl_Order")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {

  @Id
  @Column(name = "order_id", nullable = false, length = 255)
  String orderId;

  @Column(name = "customer_name", nullable = true, length = 50)
  String customerName;

  @Column(name = "phone", nullable = true, length = 20)
  String phone;

  @Column(name = "diamond_quantity", nullable = true)
  int diamondQuantity;

  @Column(name = "order_date", nullable = true)
  Date orderDate;

  @Column(name = "status", nullable = true, length = 10)
  String status;

  @Column(name = "total_price", nullable = true, precision = 18, scale = 2)
  BigDecimal totalPrice;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "user_id")
  User userId;

  @JsonManagedReference
  @ManyToOne
  @JoinColumn(name = "request_id")
  EvaluationRequest requestId;

  @JsonBackReference
  @OneToMany(mappedBy = "orderId")
  List<OrderDetail> orderDetailId;

  @JsonIgnore
  @OneToMany(mappedBy = "orderId")
  List<CommittedPaper> committedPapers;

}

