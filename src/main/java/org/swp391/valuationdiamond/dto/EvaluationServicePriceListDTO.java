package org.swp391.valuationdiamond.dto;
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
        Integer sizeFrom;
        Integer sizeTo;
        Double initPrice;
        Double priceUnit;
        String serviceId;
    }
