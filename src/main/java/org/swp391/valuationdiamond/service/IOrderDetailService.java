package org.swp391.valuationdiamond.service;

import org.swp391.valuationdiamond.dto.OrderDetailDTO;
import org.swp391.valuationdiamond.entity.primary.OrderDetail;

import java.util.List;

public interface IOrderDetailService {
    OrderDetail getOrderDetailId(String id);
    OrderDetail updateOrderDeStatus(String orderDetailId, OrderDetailDTO orderDetailDTO);
    OrderDetail updateOrderDeEvaluationStaff(String orderDetailId, OrderDetailDTO orderDetailDTO);
    List<OrderDetail> getOrderDetailsByOrderId(String orderId);
    List<OrderDetail> getOrderDetailsByOrderStatusInProgress();
    List<OrderDetail> getAllOrderDetail();
    List<OrderDetail> getOrderDetailByEvaluationStaffIsNull();
    OrderDetail updateOrderDeIsDiamond(String orderDetailId, OrderDetailDTO orderDetailDTO);
    OrderDetail updateOrderDetail(String orderDetailId, OrderDetailDTO orderDetailDTO);
    List<OrderDetail> getOrderDetailByEvaluationStaffId(String evaluationStaffId);
    long countByEvaluationStaffIdIsNull();
    List<OrderDetail> getOrderDetailsByEvaluationStaffIsNullAndStatusInProgress();

}
