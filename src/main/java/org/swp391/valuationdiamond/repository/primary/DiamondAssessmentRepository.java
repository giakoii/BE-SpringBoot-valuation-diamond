package org.swp391.valuationdiamond.repository.primary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.swp391.valuationdiamond.entity.primary.DiamondAssessment;

@Repository
public interface DiamondAssessmentRepository extends JpaRepository<DiamondAssessment, String> {

}
