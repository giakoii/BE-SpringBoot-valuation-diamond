package org.swp391.valuationdiamond.repository.primary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.swp391.valuationdiamond.entity.primary.EvaluationService;

@Repository
public interface EvaluationServiceRepository extends JpaRepository<EvaluationService, String> {

}
