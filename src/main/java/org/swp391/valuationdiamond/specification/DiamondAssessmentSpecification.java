package org.swp391.valuationdiamond.specification;

import org.springframework.data.jpa.domain.Specification;
import org.swp391.valuationdiamond.entity.secondary.DiamondAssessmentDB2;

import java.math.BigDecimal;

public class DiamondAssessmentSpecification {

    public static Specification<DiamondAssessmentDB2> HasAssessCarat(BigDecimal assessCarat) {
        return (root, query, cb) -> assessCarat == null ? null : cb.equal(root.get("assessCarat"), assessCarat);
    }

    public static Specification<DiamondAssessmentDB2> hasAssessShapeCut(String assessShapeCut) {
        return (root, query, cb) -> assessShapeCut == null || assessShapeCut.isEmpty() ? null : cb.equal(root.get("assessShapeCut"), assessShapeCut);
    }

    public static Specification<DiamondAssessmentDB2> hasAssessCut(String assessCut) {
        return (root, query, cb) -> assessCut == null || assessCut.isEmpty() ? null : cb.equal(root.get("assessCut"), assessCut);
    }

    public static Specification<DiamondAssessmentDB2> hasFluorescence(String fluorescence) {
        return (root, query, cb) -> fluorescence == null || fluorescence.isEmpty() ? null : cb.equal(root.get("fluorescence"), fluorescence);
    }

    public static Specification<DiamondAssessmentDB2> hasSymmetry(String symmetry) {
        return (root, query, cb) -> symmetry == null || symmetry.isEmpty() ? null : cb.equal(root.get("symmetry"), symmetry);
    }

    public static Specification<DiamondAssessmentDB2> hasAssessColor(String assessColor) {
        return (root, query, cb) -> assessColor == null || assessColor.isEmpty() ? null : cb.equal(root.get("assessColor"), assessColor);
    }

    public static Specification<DiamondAssessmentDB2> hasAssessClarity(String assessClarity) {
        return (root, query, cb) -> assessClarity == null || assessClarity.isEmpty() ? null : cb.equal(root.get("assessClarity"), assessClarity);
    }

    public static Specification<DiamondAssessmentDB2> hasAssessOrigin(String assessOrigin) {
        return (root, query, cb) -> assessOrigin == null || assessOrigin.isEmpty() ? null : cb.equal(root.get("assessOrigin"), assessOrigin);
    }

    public static Specification<DiamondAssessmentDB2> hasProportions(String proportions) {
        return (root, query, cb) -> proportions == null || proportions.isEmpty() ? null : cb.equal(root.get("proportions"), proportions);
    }

    public static Specification<DiamondAssessmentDB2> hasPriceBetween(BigDecimal priceMin, BigDecimal priceMax) {
        return (root, query, cb) -> {
            if (priceMin == null && priceMax == null) {
                return null;
            } else if (priceMin != null && priceMax != null) {
                return cb.between(root.get("price"), priceMin, priceMax);
            } else if (priceMin != null) {
                return cb.greaterThanOrEqualTo(root.get("price"), priceMin);
            } else {
                return cb.lessThanOrEqualTo(root.get("price"), priceMax);
            }
        };
    }

    public static Specification<DiamondAssessmentDB2> hasCaratBetween(BigDecimal caratMin, BigDecimal caratMax) {
        return (root, query, cb) -> {
            if (caratMin == null && caratMax == null) {
                return null;
            } else if (caratMin != null && caratMax != null) {
                return cb.between(root.get("assessCarat"), caratMin, caratMax);
            } else if (caratMin != null) {
                return cb.greaterThanOrEqualTo(root.get("assessCarat"), caratMin);
            } else {
                return cb.lessThanOrEqualTo(root.get("assessCarat"), caratMax);
            }
        };
    }
}
