package org.swp391.valuationdiamond.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.swp391.valuationdiamond.repository.primary.DiamondPriceRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class DiamondPriceServiceImp {
    @Autowired
    private DiamondPriceRepository diamondPriceRepository;


    // Price adjustment factors
    private static final Map<String, BigDecimal> CUT_ADJUSTMENTS = new HashMap<>();
    private static final Map<String, BigDecimal> FLUORESCENCE_ADJUSTMENTS = new HashMap<>();
    private static final Map<String, BigDecimal> SYMMETRY_ADJUSTMENTS = new HashMap<>();
    private static final Map<String, BigDecimal> POLISH_ADJUSTMENTS = new HashMap<>();
    private static final Map<String, BigDecimal> COLOR_ADJUSTMENTS = new HashMap<>();
    private static final Map<String, BigDecimal> CLARITY_ADJUSTMENTS = new HashMap<>();

    // Price indices for lab-grown and natural diamonds
    private static final Map<String, NavigableMap<BigDecimal, BigDecimal>> LAB_GROWN_PRICE_INDEX = new HashMap<>();
    private static final Map<String, NavigableMap<BigDecimal, BigDecimal>> NATURAL_PRICE_INDEX = new HashMap<>();

    static {
        CUT_ADJUSTMENTS.put("Excellent", new BigDecimal("0.10"));
        CUT_ADJUSTMENTS.put("Very Good", new BigDecimal("0.07"));
        CUT_ADJUSTMENTS.put("Good", new BigDecimal("0.05"));
        CUT_ADJUSTMENTS.put("Fair", new BigDecimal("-0.05"));
        CUT_ADJUSTMENTS.put("Poor", new BigDecimal("-0.10"));

        FLUORESCENCE_ADJUSTMENTS.put("None", BigDecimal.ZERO);
        FLUORESCENCE_ADJUSTMENTS.put("Faint", new BigDecimal("-0.01"));
        FLUORESCENCE_ADJUSTMENTS.put("Medium", new BigDecimal("-0.03"));
        FLUORESCENCE_ADJUSTMENTS.put("Strong", new BigDecimal("-0.05"));
        FLUORESCENCE_ADJUSTMENTS.put("Very Strong", new BigDecimal("-0.10"));

        SYMMETRY_ADJUSTMENTS.put("Excellent", new BigDecimal("0.05"));
        SYMMETRY_ADJUSTMENTS.put("Very Good", new BigDecimal("0.03"));
        SYMMETRY_ADJUSTMENTS.put("Good", new BigDecimal("0.02"));
        SYMMETRY_ADJUSTMENTS.put("Fair", new BigDecimal("-0.02"));
        SYMMETRY_ADJUSTMENTS.put("Poor", new BigDecimal("-0.05"));

        POLISH_ADJUSTMENTS.put("Excellent", new BigDecimal("0.05"));
        POLISH_ADJUSTMENTS.put("Very Good", new BigDecimal("0.03"));
        POLISH_ADJUSTMENTS.put("Good", new BigDecimal("0.02"));
        POLISH_ADJUSTMENTS.put("Fair", new BigDecimal("-0.02"));
        POLISH_ADJUSTMENTS.put("Poor", new BigDecimal("-0.05"));

        COLOR_ADJUSTMENTS.put("D", new BigDecimal("0.20"));
        COLOR_ADJUSTMENTS.put("E", new BigDecimal("0.15"));
        COLOR_ADJUSTMENTS.put("F", new BigDecimal("0.10"));
        COLOR_ADJUSTMENTS.put("G", BigDecimal.ZERO);
        COLOR_ADJUSTMENTS.put("H", new BigDecimal("-0.05"));
        COLOR_ADJUSTMENTS.put("I", new BigDecimal("-0.10"));
        COLOR_ADJUSTMENTS.put("J", new BigDecimal("-0.15"));
        COLOR_ADJUSTMENTS.put("K", new BigDecimal("-0.20"));

        CLARITY_ADJUSTMENTS.put("FL", new BigDecimal("0.20"));
        CLARITY_ADJUSTMENTS.put("IF", new BigDecimal("0.15"));
        CLARITY_ADJUSTMENTS.put("VVS1", new BigDecimal("0.10"));
        CLARITY_ADJUSTMENTS.put("VVS2", new BigDecimal("0.05"));
        CLARITY_ADJUSTMENTS.put("VS1", BigDecimal.ZERO);
        CLARITY_ADJUSTMENTS.put("VS2", new BigDecimal("-0.05"));
        CLARITY_ADJUSTMENTS.put("SI1", new BigDecimal("-0.10"));
        CLARITY_ADJUSTMENTS.put("SI2", new BigDecimal("-0.15"));

        // Lab-grown diamond prices
        NavigableMap<BigDecimal, BigDecimal> labRoundPrices = new TreeMap<>();
        labRoundPrices.put(new BigDecimal("1.0"), new BigDecimal("699"));
        labRoundPrices.put(new BigDecimal("2.0"), new BigDecimal("1335"));
        labRoundPrices.put(new BigDecimal("3.0"), new BigDecimal("1662"));

        NavigableMap<BigDecimal, BigDecimal> labOvalPrices = new TreeMap<>();
        labOvalPrices.put(new BigDecimal("1.0"), new BigDecimal("643"));
        labOvalPrices.put(new BigDecimal("2.0"), new BigDecimal("1353"));
        labOvalPrices.put(new BigDecimal("3.0"), new BigDecimal("2133"));

        NavigableMap<BigDecimal, BigDecimal> labEmeraldPrices = new TreeMap<>();
        labEmeraldPrices.put(new BigDecimal("1.0"), new BigDecimal("624"));
        labEmeraldPrices.put(new BigDecimal("2.0"), new BigDecimal("1179"));
        labEmeraldPrices.put(new BigDecimal("3.0"), new BigDecimal("1965"));

        NavigableMap<BigDecimal, BigDecimal> labPrincessPrices = new TreeMap<>();
        labPrincessPrices.put(new BigDecimal("1.0"), new BigDecimal("739"));
        labPrincessPrices.put(new BigDecimal("2.0"), new BigDecimal("1228"));
        labPrincessPrices.put(new BigDecimal("3.0"), new BigDecimal("1857"));

        NavigableMap<BigDecimal, BigDecimal> labCushionPrices = new TreeMap<>();
        labCushionPrices.put(new BigDecimal("1.0"), new BigDecimal("607"));
        labCushionPrices.put(new BigDecimal("2.0"), new BigDecimal("1433"));
        labCushionPrices.put(new BigDecimal("3.0"), new BigDecimal("2147"));

        NavigableMap<BigDecimal, BigDecimal> labPearPrices = new TreeMap<>();
        labPearPrices.put(new BigDecimal("1.0"), new BigDecimal("624"));
        labPearPrices.put(new BigDecimal("2.0"), new BigDecimal("1447"));
        labPearPrices.put(new BigDecimal("3.0"), new BigDecimal("2072"));

        LAB_GROWN_PRICE_INDEX.put("Round", labRoundPrices);
        LAB_GROWN_PRICE_INDEX.put("Oval", labOvalPrices);
        LAB_GROWN_PRICE_INDEX.put("Emerald", labEmeraldPrices);
        LAB_GROWN_PRICE_INDEX.put("Princess", labPrincessPrices);
        LAB_GROWN_PRICE_INDEX.put("Cushion", labCushionPrices);
        LAB_GROWN_PRICE_INDEX.put("Pear", labPearPrices);

        // Natural diamond prices
        NavigableMap<BigDecimal, BigDecimal> naturalRoundPrices = new TreeMap<>();
        naturalRoundPrices.put(new BigDecimal("0.5"), new BigDecimal("1258"));
        naturalRoundPrices.put(new BigDecimal("1.0"), new BigDecimal("4892"));
        naturalRoundPrices.put(new BigDecimal("2.0"), new BigDecimal("19786"));

        NavigableMap<BigDecimal, BigDecimal> naturalOvalPrices = new TreeMap<>();
        naturalOvalPrices.put(new BigDecimal("0.5"), new BigDecimal("1065"));
        naturalOvalPrices.put(new BigDecimal("1.0"), new BigDecimal("3837"));
        naturalOvalPrices.put(new BigDecimal("2.0"), new BigDecimal("18134"));

        NavigableMap<BigDecimal, BigDecimal> naturalEmeraldPrices = new TreeMap<>();
        naturalEmeraldPrices.put(new BigDecimal("0.5"), new BigDecimal("980"));
        naturalEmeraldPrices.put(new BigDecimal("1.0"), new BigDecimal("3768"));
        naturalEmeraldPrices.put(new BigDecimal("2.0"), new BigDecimal("17249"));

        NavigableMap<BigDecimal, BigDecimal> naturalPrincessPrices = new TreeMap<>();
        naturalPrincessPrices.put(new BigDecimal("0.5"), new BigDecimal("978"));
        naturalPrincessPrices.put(new BigDecimal("1.0"), new BigDecimal("3270"));
        naturalPrincessPrices.put(new BigDecimal("2.0"), new BigDecimal("14578"));

        NavigableMap<BigDecimal, BigDecimal> naturalCushionPrices = new TreeMap<>();
        naturalCushionPrices.put(new BigDecimal("0.5"), new BigDecimal("998"));
        naturalCushionPrices.put(new BigDecimal("1.0"), new BigDecimal("3301"));
        naturalCushionPrices.put(new BigDecimal("2.0"), new BigDecimal("15313"));

        NavigableMap<BigDecimal, BigDecimal> naturalPearPrices = new TreeMap<>();
        naturalPearPrices.put(new BigDecimal("0.5"), new BigDecimal("1071"));
        naturalPearPrices.put(new BigDecimal("1.0"), new BigDecimal("4112"));
        naturalPearPrices.put(new BigDecimal("2.0"), new BigDecimal("18812"));

        NATURAL_PRICE_INDEX.put("Round", naturalRoundPrices);
        NATURAL_PRICE_INDEX.put("Oval", naturalOvalPrices);
        NATURAL_PRICE_INDEX.put("Emerald", naturalEmeraldPrices);
        NATURAL_PRICE_INDEX.put("Princess", naturalPrincessPrices);
        NATURAL_PRICE_INDEX.put("Cushion", naturalCushionPrices);
        NATURAL_PRICE_INDEX.put("Pear", naturalPearPrices);
    }

    private BigDecimal interpolatePrice(NavigableMap<BigDecimal, BigDecimal> priceMap, BigDecimal caratWeight) {
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
        NavigableMap<BigDecimal, BigDecimal> shapePrices = isLabGrown ? LAB_GROWN_PRICE_INDEX.get(shape) : NATURAL_PRICE_INDEX.get(shape);
        if (shapePrices != null) {
            return interpolatePrice(shapePrices, caratWeight);
        }
        return BigDecimal.ZERO;
    }

    public PriceDetails calculateFinalPrice(BigDecimal caratWeight, String shape, String cut,
                                            String fluorescence, String symmetry, String polish,
                                            String color, String clarity, boolean isLabGrown) {
        BigDecimal pricePerCarat = getPricePerCarat(shape, caratWeight, isLabGrown);

        BigDecimal cutAdjustment = CUT_ADJUSTMENTS.getOrDefault(cut, BigDecimal.ZERO);
        BigDecimal fluorescenceAdjustment = FLUORESCENCE_ADJUSTMENTS.getOrDefault(fluorescence, BigDecimal.ZERO);
        BigDecimal symmetryAdjustment = SYMMETRY_ADJUSTMENTS.getOrDefault(symmetry, BigDecimal.ZERO);
        BigDecimal polishAdjustment = POLISH_ADJUSTMENTS.getOrDefault(polish, BigDecimal.ZERO);
        BigDecimal colorAdjustment = COLOR_ADJUSTMENTS.getOrDefault(color, BigDecimal.ZERO);
        BigDecimal clarityAdjustment = CLARITY_ADJUSTMENTS.getOrDefault(clarity, BigDecimal.ZERO);

        // Calculate the base final price
        BigDecimal baseFinalPrice = caratWeight
                .multiply(pricePerCarat)
                .multiply(BigDecimal.ONE.add(cutAdjustment))
                .multiply(BigDecimal.ONE.add(fluorescenceAdjustment))
                .multiply(BigDecimal.ONE.add(symmetryAdjustment))
                .multiply(BigDecimal.ONE.add(polishAdjustment))
                .multiply(BigDecimal.ONE.add(colorAdjustment))
                .multiply(BigDecimal.ONE.add(clarityAdjustment));

        // Calculate min and max price
        BigDecimal minPrice = baseFinalPrice.multiply(new BigDecimal("0.85"));
        BigDecimal maxPrice = baseFinalPrice.multiply(new BigDecimal("1.15"));

        LocalDateTime currentDate = LocalDateTime.now();
        return new PriceDetails(baseFinalPrice, minPrice, maxPrice,currentDate);
    }

    public static class PriceDetails {
        private BigDecimal baseFinalPrice;
        private BigDecimal minPrice;
        private BigDecimal maxPrice;
        private LocalDateTime currentDate;

        public PriceDetails(BigDecimal baseFinalPrice, BigDecimal minPrice, BigDecimal maxPrice, LocalDateTime currentDate) {
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
        public LocalDateTime getCurrentDate(){
            return currentDate;
        }
    }





//    public List<DiamondPrice> findByDiamondOriginAndShapeAndCaratWeightBetweenAndColorAndClarityAndCutAndFluorescenceAndPolishAndSymmetryAndPriceBetween(
//            String diamondOrigin, String shape, BigDecimal caratWeightMin, BigDecimal caratWeightMax,
//            String color, String clarity, String cut, String fluorescence, String polish, String symmetry,
//            BigDecimal priceMin, BigDecimal priceMax) {
//
//        // Set default values if not provided
//        if (diamondOrigin == null) diamondOrigin = "Lab Grown";
//        if (shape == null) shape = "Round";
//        if (caratWeightMin == null) caratWeightMin = BigDecimal.ZERO;
//        if (caratWeightMax == null) caratWeightMax = BigDecimal.ZERO;
//        if (color == null) color = "G";
//        if (clarity == null) clarity = "VS1";
//        if (cut == null) cut = "Good";
//        if (fluorescence == null) fluorescence = "None";
//        if (polish == null) polish = "Good";
//        if (symmetry == null) symmetry = "Good";
//        if (priceMin == null) priceMin = BigDecimal.ZERO;
//        if (priceMax == null) priceMax = BigDecimal.ZERO;
//
//        return diamondPriceRepository.findByDiamondOriginAndShapeAndCaratWeightBetweenAndColorAndClarityAndCutAndFluorescenceAndPolishAndSymmetryAndPriceBetween(
//                diamondOrigin, shape, caratWeightMin, caratWeightMax, color, clarity, cut, fluorescence, polish, symmetry, priceMin, priceMax);
//    }
//    public List<DiamondPrice> findSimilarDiamonds(BigDecimal caratWeight, String shape, String cut,
//                                                  String fluorescence, String symmetry, String polish,
//                                                  String color, String clarity, boolean isLabGrown, BigDecimal priceMin, BigDecimal priceMax) {
//
//        BigDecimal caratWeightMin = caratWeight.subtract(new BigDecimal("0.1"));
//        BigDecimal caratWeightMax = caratWeight.add(new BigDecimal("0.1"));
//
//        return diamondPriceRepository.findByDiamondOriginLikeAndShapeLikeAndCaratWeightBetweenAndColorLikeAndClarityLikeAndCutLikeAndFluorescenceLikeAndPolishLikeAndSymmetryLikeAndPriceBetween(
//                "%", shape, caratWeightMin, caratWeightMax, color, clarity, cut, fluorescence, polish, symmetry, priceMin, priceMax);
//    }
//    public List<DiamondPrice> findDiamondsByCaratWeightOrPriceRange(BigDecimal caratWeightMax,BigDecimal caratWeightMin, BigDecimal priceMin, BigDecimal priceMax) {
//        // Implementation to find diamonds by carat weight and price range
//        return diamondPriceRepository.findDiamondsByCaratWeightOrPriceRange(caratWeightMax,caratWeightMin, priceMin, priceMax);
//    }
    }

