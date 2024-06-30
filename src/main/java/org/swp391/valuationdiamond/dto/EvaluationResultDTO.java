package org.swp391.valuationdiamond.dto;


import lombok.*;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EvaluationResultDTO {
    String evaluationResultId;
    String diamondOrigin;
    String measurements;
    String proportions;
    String shapeCut;
    BigDecimal caratWeight;
    String color;
    String clarity;
    String cut;
    String symmetry;
    String polish;
    String fluorescence;
    String description;
    BigDecimal price;
    String userId;
    String orderDetailId;
    String img;



}