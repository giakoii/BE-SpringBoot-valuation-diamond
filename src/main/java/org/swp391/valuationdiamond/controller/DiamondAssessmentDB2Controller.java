package org.swp391.valuationdiamond.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.swp391.valuationdiamond.entity.secondary.DiamondAssessmentDB2;

import org.swp391.valuationdiamond.service.Implement.DiamondAssessmentServiceDB2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Collections;


@RestController
@RequestMapping("/api/diamond-assessments")
public class DiamondAssessmentDB2Controller {

    private static final Logger logger = LoggerFactory.getLogger(DiamondAssessmentDB2Controller.class);

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

        try {
            return diamondAssessmentServiceDB2.findSimilarDiamonds(
                    assessCarat, assessShapeCut, assessCut, fluorescence, symmetry,
                    assessColor, assessClarity, assessOrigin, proportions, priceMin, priceMax);
        } catch (Exception e) {
            logger.error("Error searching diamonds: {}", e.getMessage());
            return Collections.emptyList();
        }
    }
    @GetMapping("/DiamondAssessmentByIDDB2/{assessId}")
    public ResponseEntity<?> getDiamondAssessment(@PathVariable("assessId") String assessId) {
        DiamondAssessmentDB2 assessment = diamondAssessmentServiceDB2.getDiamondAssessmentById(assessId);
        if (assessment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Diamond not found");
        }
        return ResponseEntity.ok(assessment);
    }
}
