package org.swp391.valuationdiamond.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.swp391.valuationdiamond.entity.secondary.DiamondAssessmentDB2;
import org.swp391.valuationdiamond.repository.secondary.DiamondAssessmentDB2Repository;
import org.swp391.valuationdiamond.specification.DiamondAssessmentSpecification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Service
public class DiamondAssessmentServiceDB2 implements IDiamondAssessmentDB2{

    private static final Logger logger = LoggerFactory.getLogger(DiamondAssessmentServiceDB2.class);

    @Autowired
    private DiamondAssessmentDB2Repository diamondAssessmentDB2Repository;

    public List<DiamondAssessmentDB2> findSimilarDiamonds(BigDecimal assessCarat, String assessShapeCut, String assessCut,
                                                          String fluorescence, String symmetry, String assessColor,
                                                          String assessClarity, String assessOrigin, String proportions,
                                                          BigDecimal priceMin, BigDecimal priceMax) {
        try {
            BigDecimal caratRange = BigDecimal.valueOf(0.5); // Điều chỉnh phạm vi carat ở đây
            BigDecimal caratMin = (assessCarat != null) ? assessCarat.subtract(caratRange) : null;
            BigDecimal caratMax = (assessCarat != null) ? assessCarat.add(caratRange) : null;

            Specification<DiamondAssessmentDB2> spec = Specification.where(
                    (assessCarat != null) ? DiamondAssessmentSpecification.hasCaratBetween(caratMin, caratMax) : null
            );

            if (assessShapeCut != null && !assessShapeCut.isEmpty()) {
                spec = spec.and(DiamondAssessmentSpecification.hasAssessShapeCut(assessShapeCut));
            }

            if (assessCut != null && !assessCut.isEmpty()) {
                spec = spec.and(DiamondAssessmentSpecification.hasAssessCut(assessCut));
            }

            if (fluorescence != null && !fluorescence.isEmpty()) {
                spec = spec.and(DiamondAssessmentSpecification.hasFluorescence(fluorescence));
            }

            if (symmetry != null && !symmetry.isEmpty()) {
                spec = spec.and(DiamondAssessmentSpecification.hasSymmetry(symmetry));
            }

            if (assessColor != null && !assessColor.isEmpty()) {
                spec = spec.and(DiamondAssessmentSpecification.hasAssessColor(assessColor));
            }

            if (assessClarity != null && !assessClarity.isEmpty()) {
                spec = spec.and(DiamondAssessmentSpecification.hasAssessClarity(assessClarity));
            }

            if (assessOrigin != null && !assessOrigin.isEmpty()) {
                spec = spec.and(DiamondAssessmentSpecification.hasAssessOrigin(assessOrigin));
            }

            if (proportions != null && !proportions.isEmpty()) {
                spec = spec.and(DiamondAssessmentSpecification.hasProportions(proportions));
            }

            if (priceMin != null && priceMax != null) {
                spec = spec.and(DiamondAssessmentSpecification.hasPriceBetween(priceMin, priceMax));
            }

            return diamondAssessmentDB2Repository.findAll(spec);
        } catch (Exception e) {
            logger.error("Error finding similar diamonds: {}", e.getMessage());
            return Collections.emptyList();
        }
    }
    public DiamondAssessmentDB2 getDiamondAssessmentById(String assessId) {
        logger.info("Fetching diamond assessment with ID {}", assessId);
        DiamondAssessmentDB2 assessment = diamondAssessmentDB2Repository.findByAssessId(assessId);
        if (assessment != null) {
            logger.info("Found diamond assessment: {}", assessment);
            return assessment;
        } else {
            logger.info("Diamond assessment with ID {} not found", assessId);
            return null;
        }
    }
}
