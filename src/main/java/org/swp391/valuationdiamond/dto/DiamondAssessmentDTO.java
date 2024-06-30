package org.swp391.valuationdiamond.dto;
import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DiamondAssessmentDTO {
    String assessID;
    String assessOrigin;
    Float assessMeasurement;
    String assessCut;
    String assessShapeCut;
    String assessColor;
    String assessClarity;
    String proportions;
    String symmetry;
    String fluorescence;
    String evaluationResultId;
}
