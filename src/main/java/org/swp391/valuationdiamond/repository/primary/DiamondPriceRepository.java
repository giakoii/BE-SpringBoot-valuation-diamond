package org.swp391.valuationdiamond.repository.primary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.swp391.valuationdiamond.entity.primary.DiamondPrice;

@Repository
public interface DiamondPriceRepository extends JpaRepository<DiamondPrice, String> {
//    List<DiamondPrice> findByDiamondOriginAndShapeAndCaratWeightBetweenAndColorAndClarityAndCutAndFluorescenceAndPolishAndSymmetryAndPriceBetween(
//            String diamondOrigin, String shape, BigDecimal caratWeightMin, BigDecimal caratWeightMax,
//            String color, String clarity, String cut, String fluorescence, String polish, String symmetry,
//            BigDecimal priceMin, BigDecimal priceMax
//    );
//    List<DiamondPrice> findByDiamondOriginLikeAndShapeLikeAndCaratWeightBetweenAndColorLikeAndClarityLikeAndCutLikeAndFluorescenceLikeAndPolishLikeAndSymmetryLikeAndPriceBetween(
//            String diamondOrigin, String shape, BigDecimal caratWeightMin, BigDecimal caratWeightMax,
//            String color, String clarity, String cut, String fluorescence, String polish, String symmetry,
//            BigDecimal priceMin, BigDecimal priceMax);
//    List<DiamondPrice> findDiamondsByCaratWeightOrPriceRange(BigDecimal caratMin, BigDecimal caratMax, BigDecimal priceMin, BigDecimal priceMax);

}
