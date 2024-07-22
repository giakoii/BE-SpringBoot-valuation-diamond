package org.swp391.valuationdiamond.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
    public class EvaluationServicePriceListDTO {
        String priceList;

        @NotNull
        Integer sizeFrom;

        @NotNull
        Integer sizeTo;

        @NotNull
        Double initPrice;

        @NotNull
        Double priceUnit;

        @NotBlank
        String serviceId;
    }
