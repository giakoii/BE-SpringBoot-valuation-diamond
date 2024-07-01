package org.swp391.valuationdiamond.repository.primary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.swp391.valuationdiamond.entity.primary.EvaluationRequest;
import org.swp391.valuationdiamond.entity.primary.Order;
import org.swp391.valuationdiamond.entity.primary.User;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
  List<Order> findOrderByStatus(String status);
  Order findOrderByOrderId(String orderId);
  List<Order> findOrderByRequestId(EvaluationRequest requestId);
  List<Order> findOrderByUserId(User userId);
}
