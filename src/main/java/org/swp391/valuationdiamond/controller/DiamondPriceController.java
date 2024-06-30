package org.swp391.valuationdiamond.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.swp391.valuationdiamond.repository.primary.DiamondPriceRepository;
import org.swp391.valuationdiamond.service.DiamondPriceServiceImp;
import org.swp391.valuationdiamond.service.DiamondPriceServiceImp.PriceDetails;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/diamond")
public class DiamondPriceController {

    @Autowired
    private DiamondPriceServiceImp diamondPriceServiceImp;
    @Autowired
    private DiamondPriceRepository diamondPriceRepository;

    @GetMapping("/calculateFinalPrice")
    public PriceDetails calculateFinalPrice(@RequestParam BigDecimal caratWeight, @RequestParam String shape,
                                            @RequestParam String cut, @RequestParam String fluorescence,
                                            @RequestParam String symmetry, @RequestParam String polish,
                                            @RequestParam String color, @RequestParam String clarity,
                                            @RequestParam boolean isLabGrown) {
        return diamondPriceServiceImp.calculateFinalPrice(caratWeight, shape, cut, fluorescence, symmetry, polish, color, clarity, isLabGrown);
    }

//    @GetMapping("/search")
//    public ResponseEntity<List<DiamondPrice>> searchDiamonds(
//            @RequestParam(required = false) String diamondOrigin,
//            @RequestParam(required = false) String shape,
//            @RequestParam(required = false) BigDecimal caratWeightMin,
//            @RequestParam(required = false) BigDecimal caratWeightMax,
//            @RequestParam(required = false) String color,
//            @RequestParam(required = false) String clarity,
//            @RequestParam(required = false) String cut,
//            @RequestParam(required = false) String fluorescence,
//            @RequestParam(required = false) String polish,
//            @RequestParam(required = false) String symmetry,
//            @RequestParam(required = false) BigDecimal priceMin,
//            @RequestParam(required = false) BigDecimal priceMax) {
//
//        List<DiamondPrice> diamonds = diamondPriceServiceImp.findByDiamondOriginAndShapeAndCaratWeightBetweenAndColorAndClarityAndCutAndFluorescenceAndPolishAndSymmetryAndPriceBetween(
//                diamondOrigin, shape, caratWeightMin, caratWeightMax, color, clarity, cut, fluorescence, polish, symmetry, priceMin, priceMax);
//
//        return ResponseEntity.ok(diamonds);
//    }

//    @GetMapping("/findSimilarDiamonds")
//    public List<DiamondPrice> findSimilarDiamonds(@RequestParam BigDecimal caratWeight, @RequestParam String shape,
//                                                  @RequestParam String cut, @RequestParam String fluorescence,
//                                                  @RequestParam String symmetry, @RequestParam String polish,
//                                                  @RequestParam String color, @RequestParam String clarity,
//                                                  @RequestParam boolean isLabGrown, @RequestParam BigDecimal priceTolerance) {
//        // Calculate the base final price using the provided details
//        PriceDetails priceDetails = diamondPriceServiceImp.calculateFinalPrice(caratWeight, shape, cut, fluorescence, symmetry, polish, color, clarity, isLabGrown);
//        BigDecimal priceMin = priceDetails.getBaseFinalPrice().subtract(priceTolerance);
//        BigDecimal priceMax = priceDetails.getBaseFinalPrice().add(priceTolerance);
//
//        // Search for similar diamonds using the full criteria
//        List<DiamondPrice> similarDiamonds = diamondPriceServiceImp.findSimilarDiamonds(caratWeight, shape, cut, fluorescence, symmetry, polish, color, clarity, isLabGrown, priceMin, priceMax);
//
//        // If no similar diamonds are found, search by carat and price range with a wider tolerance
//        if (similarDiamonds.isEmpty()) {
//            BigDecimal caratTolerance = new BigDecimal("0.1"); // Example tolerance for carat weight
//            BigDecimal caratMin = caratWeight.subtract(caratTolerance);
//            BigDecimal caratMax = caratWeight.add(caratTolerance);
//
//            similarDiamonds = diamondPriceServiceImp.findDiamondsByCaratWeightOrPriceRange(caratMin, caratMax, priceMin, priceMax);
//        }
//
//        return similarDiamonds;
//    }
}
