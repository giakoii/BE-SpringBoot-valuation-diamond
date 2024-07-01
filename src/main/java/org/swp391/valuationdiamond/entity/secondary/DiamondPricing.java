package org.swp391.valuationdiamond.entity.secondary;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "diamond_pricing")
public class DiamondPricing {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String shape;

  @Column(name = "carat_weight")
  private BigDecimal caratWeight;

  private BigDecimal price;

  @Column(name = "diamond_origin")
  private String diamondOrigin;

  private String category;
  private String type;
  private BigDecimal adjustment;
}
