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
public class DiamondAssessmentServiceDB2 {

    private static final Logger logger = LoggerFactory.getLogger(DiamondAssessmentServiceDB2.class);

    @Autowired
    private DiamondAssessmentDB2Repository diamondAssessmentDB2Repository;

    public List<DiamondAssessmentDB2> findSimilarDiamonds(BigDecimal assessCarat, String assessShapeCut, String assessCut,
                                                          String fluorescence, String symmetry, String assessColor,
                                                          String assessClarity, String assessOrigin, String proportions,
                                                          BigDecimal priceMin, BigDecimal priceMax) {
        try {

            BigDecimal caratMin = (assessCarat != null) ? assessCarat.subtract(BigDecimal.valueOf(0.5)) : null;
            BigDecimal caratMax = (assessCarat != null) ? assessCarat.add(BigDecimal.valueOf(0.5)) : null;
            Specification<DiamondAssessmentDB2> spec = Specification.where(
                    (assessCarat != null) ? DiamondAssessmentSpecification.hasCaratBetween(caratMin, caratMax) : null
            );


            if (assessOrigin != null && !assessOrigin.isEmpty()) {
                spec = spec.and(DiamondAssessmentSpecification.hasAssessOrigin(assessOrigin));
            }

            // Add other criteria only if carat and origin are specified
            if (assessCarat != null || (assessOrigin != null && !assessOrigin.isEmpty())) {
                spec = spec.and(DiamondAssessmentSpecification.hasAssessShapeCut(assessShapeCut))
                        .and(DiamondAssessmentSpecification.hasAssessCut(assessCut))
                        .and(DiamondAssessmentSpecification.hasFluorescence(fluorescence))
                        .and(DiamondAssessmentSpecification.hasSymmetry(symmetry))
                        .and(DiamondAssessmentSpecification.hasAssessColor(assessColor))
                        .and(DiamondAssessmentSpecification.hasAssessClarity(assessClarity))
                        .and(DiamondAssessmentSpecification.hasProportions(proportions))
                        .and(DiamondAssessmentSpecification.hasPriceBetween(priceMin, priceMax));
            } else {
                // If neither carat nor origin is specified, add other criteria directly
                spec = DiamondAssessmentSpecification.hasAssessShapeCut(assessShapeCut)
                        .and(DiamondAssessmentSpecification.hasAssessCut(assessCut))
                        .and(DiamondAssessmentSpecification.hasFluorescence(fluorescence))
                        .and(DiamondAssessmentSpecification.hasSymmetry(symmetry))
                        .and(DiamondAssessmentSpecification.hasAssessColor(assessColor))
                        .and(DiamondAssessmentSpecification.hasAssessClarity(assessClarity))
                        .and(DiamondAssessmentSpecification.hasProportions(proportions))
                        .and(DiamondAssessmentSpecification.hasPriceBetween(priceMin, priceMax));
            }

            return diamondAssessmentDB2Repository.findAll(spec);
        } catch (Exception e) {
            logger.error("Error finding similar diamonds: {}", e.getMessage());
            return Collections.emptyList();
        }
    }
}
