package org.swp391.valuationdiamond.entity.primary;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Table(name = "tbl_Committed_Paper")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommittedPaper {

  @Id
  @Column(name = "committed_id", nullable = false, length = 255)
  String committedId;

  @Column(name = "committed_name", nullable = true, length = 255)
  String committedName;

  @Column(name = "committed_date", nullable = true)
  Date committedDate;

  @Column(name = "civil_id", nullable = true, length = 255)
  String civilId;

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "user_id")
  User userId;

  @ManyToOne
  @JoinColumn(name = "order_id", referencedColumnName = "order_id")
  Order orderId;
}
