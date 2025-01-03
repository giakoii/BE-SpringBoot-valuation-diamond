package org.swp391.valuationdiamond.repository.primary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.swp391.valuationdiamond.entity.primary.EvaluationService;
import org.swp391.valuationdiamond.entity.primary.Order;
import org.swp391.valuationdiamond.entity.primary.OrderDetail;
import org.swp391.valuationdiamond.entity.primary.Status;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {
    //    List<OrderDetail> findByOrderDetailId(String orderDetailId);
    List<OrderDetail> findByOrderId(Order orderId);

    List<OrderDetail> findByStatus(Status status);

    List<OrderDetail> findByEvaluationStaffIdIsNull();

    List<OrderDetail> findByEvaluationStaffId(String evaluationStaffId);

    List<OrderDetail> findByServiceId(EvaluationService evaluationService);

    Long countByEvaluationStaffIdIsNull();

    Long findByEvaluationStaffIdAndStatus(String evaluationStaffId, Status status);
    List<OrderDetail> findByEvaluationStaffIdIsNullAndStatus(Status status);


    Long countByEvaluationStaffIdAndStatus(String evaluationStaffId, Status status);
}
