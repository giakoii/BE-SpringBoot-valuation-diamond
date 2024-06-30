package org.swp391.valuationdiamond.entity.primary;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "tbl_Diamond_Price")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DiamondPrice {

  @Id
  @Column(name = "diamond_id", nullable = false, length = 255)
  String diamondId;

  @Column(name = "brand", nullable = true, length = 100)
  String brand;

  @Column(name = "diamond_origin", nullable = true, length = 100)
  String diamondOrigin;

  @Column(name = "shape", nullable = true, length = 50)
  String shape;

  @Column(name = "carat_weight", nullable = true, precision = 18, scale = 2)
  BigDecimal caratWeight;

  @Column(name = "color", nullable = true, length = 50)
  String color;

  @Column(name = "clarity", nullable = true, length = 50)
  String clarity;

  @Column(name = "cut", nullable = true, length = 50)
  String cut;

  @Column(name = "symmetry", nullable = true, length = 50)
  String symmetry;

  @Column(name = "polish", nullable = true, length = 50)
  String polish;

  @Column(name = "fluorescence", nullable = true, length = 50)
  String fluorescence;

  @Column(name = "price", nullable = true, precision = 18, scale = 2)
  BigDecimal price;

  @Column(name = "Image", nullable = true)
  String image;

  @Column(name = "valid_date", nullable = true)
  Date validDate;

}
