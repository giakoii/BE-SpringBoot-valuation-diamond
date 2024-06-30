package org.swp391.valuationdiamond.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.swp391.valuationdiamond.entity.secondary.DiamondAssessmentDB2;
import org.swp391.valuationdiamond.service.DiamondAssessmentServiceDB2;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/diamond-assessments")
public class DiamondAssessmentDB2Controller {

    @Autowired
    private DiamondAssessmentServiceDB2 diamondAssessmentServiceDB2;

    @GetMapping("/search")
    public List<DiamondAssessmentDB2> searchDiamonds(
            @RequestParam(required = false) BigDecimal assessCarat,
            @RequestParam(required = false) String assessShapeCut,
            @RequestParam(required = false) String assessCut,
            @RequestParam(required = false) String fluorescence,
            @RequestParam(required = false) String symmetry,
            @RequestParam(required = false) String assessColor,
            @RequestParam(required = false) String assessClarity,
            @RequestParam(required = false) String assessOrigin,
            @RequestParam(required = false) String proportions,
            @RequestParam(required = false) BigDecimal priceMin,
            @RequestParam(required = false) BigDecimal priceMax) {

        return diamondAssessmentServiceDB2.findSimilarDiamonds(
                assessCarat, assessShapeCut, assessCut, fluorescence, symmetry,
                assessColor, assessClarity, assessOrigin, proportions, priceMin, priceMax);
    }
}
