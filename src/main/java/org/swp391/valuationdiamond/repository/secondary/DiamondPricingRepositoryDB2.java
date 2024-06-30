package org.swp391.valuationdiamond.repository.secondary;


import org.springframework.data.jpa.repository.JpaRepository;
import org.swp391.valuationdiamond.entity.secondary.DiamondPricing;

import java.util.List;

public interface DiamondPricingRepositoryDB2 extends JpaRepository<DiamondPricing, Long> {
    List<DiamondPricing> findByShapeAndIsLabGrown(String shape, Boolean isLabGrown);
    List<DiamondPricing> findByCategory(String category);
}
