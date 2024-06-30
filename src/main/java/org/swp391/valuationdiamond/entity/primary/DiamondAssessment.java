//package org.swp391.valuationdiamond.entity;
//
//import jakarta.persistence.*;
//
//import java.util.List;
//import lombok.AccessLevel;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.experimental.FieldDefaults;
//
//@Entity
//@Table(name = "tbl_Diamond_Assessment")
//@NoArgsConstructor
//@AllArgsConstructor
//@Data
//@Builder
//@FieldDefaults(level = AccessLevel.PRIVATE)
//public class DiamondAssessment {
//
//  @Id
//  @GeneratedValue(strategy = GenerationType.UUID)
//  @Column(name = "assess_id", nullable = false, length = 255)
//  String assessId;
//
//  @Column(name = "assess_origin", nullable = true, length = 10)
//  String assessOrigin;
//
//  @Column(name = "assess_measurement", nullable = true)
//  Float assessMeasurement;
//
//  @Column(name = "assess_cut", nullable = true, length = 10)
//  String assessCut;
//
//  @Column(name = "assess_shape_cut", nullable = true, length = 100)
//  String assessShapeCut;
//
//  @Column(name = "assess_color", nullable = true, length = 10)
//  String assessColor;
//
//  @Column(name = "assess_clarity", nullable = true, length = 10)
//  String assessClarity;
//
//  @Column(name = "proportions", nullable = true, length = 10)
//  String proportions;
//
//  @Column(name = "symmetry", nullable = true, length = 20)
//  String symmetry;
//
//  @Column(name = "fluorescence", nullable = true, length = 10)
//  String fluorescence;

//  @OneToMany(mappedBy = "assessId")
//  List<EvaluationResult> evaluationResults;
package org.swp391.valuationdiamond.entity.primary;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

  @Entity
  @Table(name = "tbl_Diamond_Assessment")
  @NoArgsConstructor
  @AllArgsConstructor
  @Data
  @Builder
  @FieldDefaults(level = AccessLevel.PRIVATE)
  public class DiamondAssessment {

    @Id
    @Column(name = "assess_id", nullable = false, length = 255)
    String assessId;

    @Column(name = "assess_origin", nullable = true, length = 10)
    String assessOrigin;

    @Column(name = "assess_measurement", nullable = true)
    Float assessMeasurement;

    @Column(name = "assess_cut", nullable = true, length = 10)
    String assessCut;

    @Column(name = "assess_shape_cut", nullable = true, length = 100)
    String assessShapeCut;

    @Column(name = "assess_color", nullable = true, length = 10)
    String assessColor;

    @Column(name = "assess_clarity", nullable = true, length = 10)
    String assessClarity;

    @Column(name = "proportions", nullable = true, length = 10)
    String proportions;

    @Column(name = "symmetry", nullable = true, length = 20)
    String symmetry;

    @Column(name = "fluorescence", nullable = true, length = 10)
    String fluorescence;

//  @OneToMany(mappedBy = "assessId")
//  List<EvaluationResult> evaluationResults;

  }

