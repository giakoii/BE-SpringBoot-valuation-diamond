package org.swp391.valuationdiamond.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.swp391.valuationdiamond.service.DiamondPriceServiceDB2;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/getDB2/calculate")
public class DiamondPriceDB2Controller {

    private static final Logger logger = LoggerFactory.getLogger(DiamondPriceDB2Controller.class);

    @Autowired
    private DiamondPriceServiceDB2 diamondPriceServiceDB2;

    @GetMapping("/price")
    public DiamondPriceServiceDB2.PriceDetails getPrice(
            @RequestParam BigDecimal caratWeight,
            @RequestParam String shape,
            @RequestParam String cut,
            @RequestParam String fluorescence,
            @RequestParam String symmetry,
            @RequestParam String polish,
            @RequestParam String color,
            @RequestParam String clarity,
            @RequestParam String diamondOrigin) {

        try {
            return diamondPriceServiceDB2.calculateFinalPrice(caratWeight, shape, cut, fluorescence, symmetry, polish, color, clarity, diamondOrigin);
        } catch (Exception e) {
            logger.error("Error fetching price details: {}", e.getMessage());
            return new DiamondPriceServiceDB2.PriceDetails(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, LocalDate.now());
        }
    }
}
