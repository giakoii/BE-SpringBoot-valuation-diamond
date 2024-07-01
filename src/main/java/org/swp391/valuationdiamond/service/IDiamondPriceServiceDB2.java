package org.swp391.valuationdiamond.service;

import java.math.BigDecimal;

public interface IDiamondPriceServiceDB2 {



    BigDecimal getPricePerCarat(String shape, BigDecimal caratWeight, String diamondOrigin);

    DiamondPriceServiceDB2.PriceDetails calculateFinalPrice(BigDecimal caratWeight, String shape, String cut,
                                                            String fluorescence, String symmetry, String polish,
                                                            String color, String clarity, String diamondOrigin);
}
