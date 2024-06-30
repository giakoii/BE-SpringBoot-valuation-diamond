package org.swp391.valuationdiamond.entity.secondary;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Table(name = "diamond_assessment")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class DiamondAssessmentDB2 {

  @Id
  @Column(name = "assess_id", nullable = false)
  private String assessId;

  @Column(name = "assess_origin", nullable = false)
  private String assessOrigin;

  @Column(name = "assess_measurement", nullable = false)
  private BigDecimal assessMeasurement;

  @Column(name = "assess_cut", nullable = false)
  private String assessCut;

  @Column(name = "assess_shape_cut", nullable = false)
  private String assessShapeCut;

  @Column(name = "assess_color", nullable = false)
  private String assessColor;

  @Column(name = "assess_clarity", nullable = false)
  private String assessClarity;

  @Column(name = "proportions", nullable = false)
  private String proportions;

  @Column(name = "symmetry", nullable = false)
  private String symmetry;

  @Column(name = "fluorescence", nullable = false)
  private String fluorescence;

  @Column(name = "polish", nullable = false)
  private String polish;

  @Column(name = "price", nullable = false)
  private BigDecimal price;
}
