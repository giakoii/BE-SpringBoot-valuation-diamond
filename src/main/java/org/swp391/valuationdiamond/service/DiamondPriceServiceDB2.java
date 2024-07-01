package org.swp391.valuationdiamond.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.swp391.valuationdiamond.entity.secondary.DiamondPricing;
import org.swp391.valuationdiamond.repository.secondary.DiamondPricingRepositoryDB2;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class DiamondPriceServiceDB2 {

    private static final Logger logger = LoggerFactory.getLogger(DiamondPriceServiceDB2.class);

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
        logger.info("Price Map: {}", priceMap);

        BigDecimal lowerKey = priceMap.floorKey(caratWeight);
        BigDecimal upperKey = priceMap.ceilingKey(caratWeight);
        if (lowerKey == null) return priceMap.firstEntry().getValue();
        if (upperKey == null) return priceMap.lastEntry().getValue();
        if (lowerKey.equals(upperKey)) return priceMap.get(lowerKey);

        BigDecimal lowerPrice = priceMap.get(lowerKey);
        BigDecimal upperPrice = priceMap.get(upperKey);

        BigDecimal interpolatedPrice = lowerPrice.add((upperPrice.subtract(lowerPrice))
                .multiply(caratWeight.subtract(lowerKey))
                .divide(upperKey.subtract(lowerKey), RoundingMode.HALF_UP));

        logger.info("Interpolated Price for carat weight {}: {}", caratWeight, interpolatedPrice);
        return interpolatedPrice;
    }

//    @Override
    public BigDecimal getPricePerCarat(String shape, BigDecimal caratWeight, String diamondOrigin) {
        logger.info("Fetching price for shape: {}, carat weight: {}, diamond origin: {}", shape, caratWeight, diamondOrigin);
        List<DiamondPricing> priceList = diamondPricingRepositoryDB2.findByShapeAndDiamondOrigin(shape, diamondOrigin);
        logger.info("Price List for shape {} and origin {}: {}", shape, diamondOrigin, priceList);
        if (!priceList.isEmpty()) {
            return interpolatePrice(priceList, caratWeight);
        }
        logger.warn("No pricing data found for shape {} and origin {}", shape, diamondOrigin);
        return BigDecimal.ZERO;
    }

//    @Override
    public PriceDetails calculateFinalPrice(BigDecimal caratWeight, String shape, String cut,
                                            String fluorescence, String symmetry, String polish,
                                            String color, String clarity, String diamondOrigin) {
        BigDecimal pricePerCarat = getPricePerCarat(shape, caratWeight, diamondOrigin);
        logger.info("Price Per Carat: {}", pricePerCarat);

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

        logger.info("Adjustments - Cut: {}, Fluorescence: {}, Symmetry: {}, Polish: {}, Color: {}, Clarity: {}",
                cutAdjustment, fluorescenceAdjustment, symmetryAdjustment, polishAdjustment, colorAdjustment, clarityAdjustment);

        BigDecimal baseFinalPrice = caratWeight
                .multiply(pricePerCarat)
                .multiply(BigDecimal.ONE.add(cutAdjustment))
                .multiply(BigDecimal.ONE.add(fluorescenceAdjustment))
                .multiply(BigDecimal.ONE.add(symmetryAdjustment))
                .multiply(BigDecimal.ONE.add(polishAdjustment))
                .multiply(BigDecimal.ONE.add(colorAdjustment))
                .multiply(BigDecimal.ONE.add(clarityAdjustment));

        logger.info("Base Final Price before range adjustment: {}", baseFinalPrice);

        BigDecimal minPrice = baseFinalPrice.multiply(new BigDecimal("0.85"));
        BigDecimal maxPrice = baseFinalPrice.multiply(new BigDecimal("1.15"));

        logger.info("Price Range - Min: {}, Max: {}", minPrice, maxPrice);

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
