package org.swp391.valuationdiamond.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EvaluationServiceDTO {
    String serviceId;
    @NotBlank
    String serviceType;

    @NotBlank
    String serviceDescription;

    @NotBlank
    String status;
}
