package org.swp391.valuationdiamond.repository.secondary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.swp391.valuationdiamond.entity.secondary.DiamondAssessmentDB2;

import java.math.BigDecimal;
import java.util.List;

public interface DiamondAssessmentDB2Repository extends JpaRepository<DiamondAssessmentDB2, String>, JpaSpecificationExecutor<DiamondAssessmentDB2> {

    List<DiamondAssessmentDB2> findByAssessMeasurementAndAssessShapeCutAndAssessCutAndFluorescenceAndSymmetryAndAssessColorAndAssessClarityAndAssessOriginAndProportionsAndPriceBetween(
            BigDecimal assessMeasurement,
            String assessShapeCut,
            String assessCut,
            String fluorescence,
            String symmetry,
            String assessColor,
            String assessClarity,
            String assessOrigin,
            String proportions,
            BigDecimal priceMin,
            BigDecimal priceMax);
}
