package org.swp391.valuationdiamond.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.swp391.valuationdiamond.entity.secondary.DiamondPricing;
import org.swp391.valuationdiamond.repository.secondary.DiamondPricingRepositoryDB2;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;

@Service
public class DiamondPriceServiceDB2 {

    @Autowired
    private DiamondPricingRepositoryDB2 diamondPricingRepositoryDB2;

    private Map<String, BigDecimal> getAdjustments(String category) {
        Map<String, BigDecimal> adjustments = new HashMap<>();
        List<DiamondPricing> pricingList = diamondPricingRepositoryDB2.findByCategory(category);
        for (DiamondPricing pricing : pricingList) {
            adjustments.put(pricing.getType(), pricing.getAdjustment());
        }
        return adjustments;
    }

    private BigDecimal interpolatePrice(List<DiamondPricing> priceList, BigDecimal caratWeight) {
        NavigableMap<BigDecimal, BigDecimal> priceMap = new TreeMap<>();
        for (DiamondPricing pricing : priceList) {
            priceMap.put(pricing.getCaratWeight(), pricing.getPrice());
        }
        BigDecimal lowerKey = priceMap.floorKey(caratWeight);
        BigDecimal upperKey = priceMap.ceilingKey(caratWeight);
        if (lowerKey == null) return priceMap.firstEntry().getValue();
        if (upperKey == null) return priceMap.lastEntry().getValue();
        if (lowerKey.equals(upperKey)) return priceMap.get(lowerKey);

        BigDecimal lowerPrice = priceMap.get(lowerKey);
        BigDecimal upperPrice = priceMap.get(upperKey);

        return lowerPrice.add((upperPrice.subtract(lowerPrice))
                .multiply(caratWeight.subtract(lowerKey))
                .divide(upperKey.subtract(lowerKey), RoundingMode.HALF_UP));
    }

    public BigDecimal getPricePerCarat(String shape, BigDecimal caratWeight, boolean isLabGrown) {
        List<DiamondPricing> priceList = diamondPricingRepositoryDB2.findByShapeAndIsLabGrown(shape, isLabGrown);
        if (!priceList.isEmpty()) {
            return interpolatePrice(priceList, caratWeight);
        }
        return BigDecimal.ZERO;
    }

    public PriceDetails calculateFinalPrice(BigDecimal caratWeight, String shape, String cut,
                                            String fluorescence, String symmetry, String polish,
                                            String color, String clarity, boolean isLabGrown) {
        BigDecimal pricePerCarat = getPricePerCarat(shape, caratWeight, isLabGrown);

        Map<String, BigDecimal> cutAdjustments = getAdjustments("CUT");
        Map<String, BigDecimal> fluorescenceAdjustments = getAdjustments("FLUORESCENCE");
        Map<String, BigDecimal> symmetryAdjustments = getAdjustments("SYMMETRY");
        Map<String, BigDecimal> polishAdjustments = getAdjustments("POLISH");
        Map<String, BigDecimal> colorAdjustments = getAdjustments("COLOR");
        Map<String, BigDecimal> clarityAdjustments = getAdjustments("CLARITY");

        BigDecimal cutAdjustment = cutAdjustments.getOrDefault(cut, BigDecimal.ZERO);
        BigDecimal fluorescenceAdjustment = fluorescenceAdjustments.getOrDefault(fluorescence, BigDecimal.ZERO);
        BigDecimal symmetryAdjustment = symmetryAdjustments.getOrDefault(symmetry, BigDecimal.ZERO);
        BigDecimal polishAdjustment = polishAdjustments.getOrDefault(polish, BigDecimal.ZERO);
        BigDecimal colorAdjustment = colorAdjustments.getOrDefault(color, BigDecimal.ZERO);
        BigDecimal clarityAdjustment = clarityAdjustments.getOrDefault(clarity, BigDecimal.ZERO);

        BigDecimal baseFinalPrice = caratWeight
                .multiply(pricePerCarat)
                .multiply(BigDecimal.ONE.add(cutAdjustment))
                .multiply(BigDecimal.ONE.add(fluorescenceAdjustment))
                .multiply(BigDecimal.ONE.add(symmetryAdjustment))
                .multiply(BigDecimal.ONE.add(polishAdjustment))
                .multiply(BigDecimal.ONE.add(colorAdjustment))
                .multiply(BigDecimal.ONE.add(clarityAdjustment));

        BigDecimal minPrice = baseFinalPrice.multiply(new BigDecimal("0.85"));
        BigDecimal maxPrice = baseFinalPrice.multiply(new BigDecimal("1.15"));

        LocalDate currentDate = LocalDate.now();
        return new PriceDetails(baseFinalPrice, minPrice, maxPrice, currentDate);
    }

    public static class PriceDetails {
        private BigDecimal baseFinalPrice;
        private BigDecimal minPrice;
        private BigDecimal maxPrice;
        private LocalDate currentDate;

        public PriceDetails(BigDecimal baseFinalPrice, BigDecimal minPrice, BigDecimal maxPrice, LocalDate currentDate) {
            this.baseFinalPrice = baseFinalPrice;
            this.minPrice = minPrice;
            this.maxPrice = maxPrice;
            this.currentDate = currentDate;
        }

        public BigDecimal getBaseFinalPrice() {
            return baseFinalPrice;
        }

        public BigDecimal getMinPrice() {
            return minPrice;
        }

        public BigDecimal getMaxPrice() {
            return maxPrice;
        }

        public LocalDate getCurrentDate() {
            return currentDate;
        }
    }
}
