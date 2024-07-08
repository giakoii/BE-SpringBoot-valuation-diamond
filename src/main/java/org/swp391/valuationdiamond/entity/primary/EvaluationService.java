package org.swp391.valuationdiamond.entity.primary;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "tbl_Evaluation_Service")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EvaluationService {

  @Id
  @Column(name = "service_id", nullable = false, length = 255)
  String serviceId;

  @Column(name = "service_type", nullable = true, length = 100)
  String serviceType;

  @Column(name = "service_description", nullable = true, columnDefinition = "NVARCHAR(MAX)")
  String serviceDescription;

  @Column(name = "status", nullable = true, length = 50)
  String status;

  @JsonIgnore
  @OneToMany(mappedBy = "serviceId")
  List<EvaluationServicePriceList> servicePriceList;

//  @JsonIgnore
@JsonBackReference
  @OneToMany(mappedBy = "serviceId")
  List<OrderDetail> orderDetails;

}
