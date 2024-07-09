package org.swp391.valuationdiamond.repository.primary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.swp391.valuationdiamond.entity.primary.EvaluationService;
import org.swp391.valuationdiamond.entity.primary.Status;

import java.util.List;

@Repository
public interface EvaluationServiceRepository extends JpaRepository<EvaluationService, String> {
    List<EvaluationService> findByStatus(Status status);

}
