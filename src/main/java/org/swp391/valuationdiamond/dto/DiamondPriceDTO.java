package org.swp391.valuationdiamond.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DiamondPriceDTO {

    String diamondId;
    String brand;
    String diamondOrigin;
    String shape;
    BigDecimal caratWeight;
    String color;
    String clarity;
    String cut;
    String symmetry;
    String polish;
    String fluorescence;
    BigDecimal price;
    String image;
    Date validDate;
}
