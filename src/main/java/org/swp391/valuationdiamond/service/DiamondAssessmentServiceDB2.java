package org.swp391.valuationdiamond.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.swp391.valuationdiamond.entity.secondary.DiamondAssessmentDB2;
import org.swp391.valuationdiamond.repository.secondary.DiamondAssessmentDB2Repository;
import org.swp391.valuationdiamond.specification.DiamondAssessmentSpecification;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DiamondAssessmentServiceDB2 {

    @Autowired
    private DiamondAssessmentDB2Repository diamondAssessmentDB2Repository;

    public List<DiamondAssessmentDB2> findSimilarDiamonds(BigDecimal assessMeasurement, String assessShapeCut, String assessCut,
                                                          String fluorescence, String symmetry, String assessColor,
                                                          String assessClarity, String assessOrigin, String proportions,
                                                          BigDecimal priceMin, BigDecimal priceMax) {

        BigDecimal caratMin = (assessMeasurement != null) ? assessMeasurement.subtract(BigDecimal.valueOf(0.5)) : null;
        BigDecimal caratMax = (assessMeasurement != null) ? assessMeasurement.add(BigDecimal.valueOf(0.5)) : null;

        Specification<DiamondAssessmentDB2> spec = Specification.where(
                        (assessMeasurement != null) ?
                                DiamondAssessmentSpecification.hasCaratBetween(caratMin, caratMax) :
                                null)
                .and(DiamondAssessmentSpecification.hasAssessShapeCut(assessShapeCut))
                .and(DiamondAssessmentSpecification.hasAssessCut(assessCut))
                .and(DiamondAssessmentSpecification.hasFluorescence(fluorescence))
                .and(DiamondAssessmentSpecification.hasSymmetry(symmetry))
                .and(DiamondAssessmentSpecification.hasAssessColor(assessColor))
                .and(DiamondAssessmentSpecification.hasAssessClarity(assessClarity))
                .and(DiamondAssessmentSpecification.hasAssessOrigin(assessOrigin))
                .and(DiamondAssessmentSpecification.hasProportions(proportions))
                .and(DiamondAssessmentSpecification.hasPriceBetween(priceMin, priceMax));

        return diamondAssessmentDB2Repository.findAll(spec);
    }
}
