package org.swp391.valuationdiamond.repository.primary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.swp391.valuationdiamond.entity.primary.EvaluationResult;
import org.swp391.valuationdiamond.entity.primary.OrderDetail;
import org.swp391.valuationdiamond.entity.primary.User;

import java.util.List;

@Repository
public interface EvaluationResultRepository extends JpaRepository<EvaluationResult, String> {
    EvaluationResult findFirstByOrderDetailId(OrderDetail orderDetail);
    List<EvaluationResult> findByUserId(User userId);
    }

