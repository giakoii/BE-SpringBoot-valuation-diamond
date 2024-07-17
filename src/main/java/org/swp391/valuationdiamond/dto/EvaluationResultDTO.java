package org.swp391.valuationdiamond.dto;


import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EvaluationResultDTO {
    @NotBlank
    String diamondOrigin;
    @NotBlank
    String measurements;
    @NotBlank
    String proportions;
    @NotBlank
    String shapeCut;
    @NotNull
    @DecimalMax(value = "49.99", message = "Carat weight must be less than 50")
    BigDecimal caratWeight;
    @NotBlank
    String color;
    @NotBlank
    String clarity;
    @NotBlank
    String cut;
    @NotBlank
    String symmetry;
    @NotBlank
    String polish;
    @NotBlank
    String fluorescence;
    @NotBlank
    String description;
    @NotNull
    BigDecimal price;
    @NotBlank
    String userId;
    @NotBlank
    String orderDetailId;
    @NotBlank
    String img;
    Date createDate;


}