package org.swp391.valuationdiamond.repository.primary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.swp391.valuationdiamond.entity.primary.EvaluationRequest;
import org.swp391.valuationdiamond.entity.primary.Order;
import org.swp391.valuationdiamond.entity.primary.Status;
import org.swp391.valuationdiamond.entity.primary.User;

import java.util.List;

@Repository
public interface EvaluationRequestRepository extends JpaRepository<EvaluationRequest, String> {
    EvaluationRequest findByRequestId(String requestId);
    List<EvaluationRequest> findByStatus(Status status);
    List<EvaluationRequest> findByUserId(User userId);
    boolean deleteByRequestId(String requestId);
    EvaluationRequest findEvaluationRequestByOrders(Order order);
}
