package org.swp391.valuationdiamond.service;

import com.nimbusds.jose.jwk.Curve;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.swp391.valuationdiamond.dto.OrderDetailDTO;
import org.swp391.valuationdiamond.entity.primary.*;
import org.swp391.valuationdiamond.repository.primary.EvaluationServiceRepository;
import org.swp391.valuationdiamond.repository.primary.OrderDetailRepository;
import org.swp391.valuationdiamond.repository.primary.OrderRepository;
import org.swp391.valuationdiamond.repository.primary.UserRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderDetailServiceImp implements IOrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private EvaluationServiceRepository evaluationServiceRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public OrderDetail getOrderDetailId(String id) {
        return orderDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not Found"));
    }

    @Override
    public OrderDetail updateOrderDeStatus(String orderDetailId, OrderDetailDTO orderDetailDTO) {
        try {
            OrderDetail orderDetail = getOrderDetailId(orderDetailId);

            orderDetail.setStatus(Status.valueOf(orderDetailDTO.getStatus()));

            // Set Order
            if (orderDetailDTO.getOrderId() != null) {
                Order order = orderRepository.findById(orderDetailDTO.getOrderId()).orElse(null);
                orderDetail.setOrderId(order);
            }

            // Set Service
            if (orderDetailDTO.getServiceId() != null) {
                EvaluationService service = evaluationServiceRepository.findById(orderDetailDTO.getServiceId()).orElse(null);
                orderDetail.setServiceId(service);
            }
            return orderDetailRepository.save(orderDetail);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while updating the order detail", e);
        }
    }


    //update thằng staff
    @Override
    public OrderDetail updateOrderDeEvaluationStaff(String orderDetailId, OrderDetailDTO orderDetailDTO) {
        try {
            OrderDetail orderDetail = getOrderDetailId(orderDetailId);

            orderDetail.setEvaluationStaffId(orderDetailDTO.getEvaluationStaffId());

            // Set Order
            if (orderDetailDTO.getOrderId() != null) {
                Order order = orderRepository.findById(orderDetailDTO.getOrderId()).orElse(null);
                orderDetail.setOrderId(order);
            }

            // Set Service
            if (orderDetailDTO.getServiceId() != null) {
                EvaluationService service = evaluationServiceRepository.findById(orderDetailDTO.getServiceId()).orElse(null);
                orderDetail.setServiceId(service);
            }
            return orderDetailRepository.save(orderDetail);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while updating the order detail", e);
        }
    }

    @Override
    public List<OrderDetail> getOrderDetailsByOrderId(String orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        return orderDetailRepository.findByOrderId(order);
    }

    @Override
    public List<OrderDetail> getOrderDetailsByOrderStatusInProgress() {
        return orderDetailRepository.findByStatus(Status.In_Progress);
    }

    //ham getall
    @Override
    public List<OrderDetail> getAllOrderDetail() {
        return orderDetailRepository.findAll();
    }

    //hàm get staff == null
    @Override
    public List<OrderDetail> getOrderDetailByEvaluationStaffIsNull() {
        return orderDetailRepository.findByEvaluationStaffIdIsNull();
    }
    // get function
    @Override
    public List<OrderDetail> getOrderDetailsByEvaluationStaffIsNullAndStatusInProgress() {
        return orderDetailRepository.findByEvaluationStaffIdIsNullAndStatus(Status.In_Progress);
    }

    @Override
    public OrderDetail updateOrderDeIsDiamond(String orderDetailId, OrderDetailDTO orderDetailDTO) {
        try {
            OrderDetail orderDetail = getOrderDetailId(orderDetailId);
            orderDetail.setIsDiamond(orderDetailDTO.getIsDiamond());
            // Set Order
            if (orderDetailDTO.getOrderId() != null) {
                Order order = orderRepository.findById(orderDetailDTO.getOrderId()).orElse(null);
                orderDetail.setOrderId(order);
            }

            // Set Service
            if (orderDetailDTO.getServiceId() != null) {
                EvaluationService service = evaluationServiceRepository.findById(orderDetailDTO.getServiceId()).orElse(null);
                orderDetail.setServiceId(service);
            }
            return orderDetailRepository.save(orderDetail);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while updating the order detail", e);
        }
    }

    //
    @Override
    public OrderDetail updateOrderDetail(String orderDetailId, OrderDetailDTO orderDetailDTO) {

            OrderDetail orderDetail = orderDetailRepository.findById(orderDetailId).orElseThrow(() -> new RuntimeException("Order detail not found"));
        try {
            // Update properties from DTO only if they are not null
            if (orderDetailDTO.getOrderDetailId() != null) {
                orderDetail.setOrderDetailId(orderDetailDTO.getOrderDetailId());
            }
            if (orderDetailDTO.getEvaluationStaffId() != null) {
                orderDetail.setEvaluationStaffId(orderDetailDTO.getEvaluationStaffId());
            }
            if (orderDetailDTO.getStatus() != null) {
                orderDetail.setStatus(Status.valueOf(orderDetailDTO.getStatus()));
            }
            if (orderDetailDTO.getUnitPrice() != null) {
                orderDetail.setUnitPrice(orderDetailDTO.getUnitPrice());
            }
            if (orderDetailDTO.getReceivedDate() != null) {
                orderDetail.setReceivedDate(orderDetailDTO.getReceivedDate());
            }
            if (orderDetailDTO.getExpiredReceivedDate() != null) {
                orderDetail.setExpiredReceivedDate(orderDetailDTO.getExpiredReceivedDate());
            }
            if (orderDetailDTO.getImg() != null) {
                orderDetail.setImg(orderDetailDTO.getImg());
            }
            if (orderDetailDTO.getSize() != null) {
                orderDetail.setSize(orderDetailDTO.getSize());
            }
            if (orderDetailDTO.getIsDiamond() != null) {
                orderDetail.setIsDiamond(orderDetailDTO.getIsDiamond());
            }
            // Add more properties as needed

            return orderDetailRepository.save(orderDetail);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while updating the order detail", e);
        }
    }


    @Override
    public List<OrderDetail> getOrderDetailByEvaluationStaffId(String evaluationStaffId) {
        return orderDetailRepository.findByEvaluationStaffId(evaluationStaffId);
    }


    public static class OrderDetailCountResponse {
        private long Count;

        public OrderDetailCountResponse(long Count) {
            this.Count = Count;
        }

        public long getCount() {
            return Count;
        }

        public void setCount(long orderDetailCount) {
            this.Count = Count;
        }

        @Override
        public String toString() {
            return "UserCountResponse{" +
                    "Total Order Detail=" + Count +
                    '}';
        }
    }

    @Override
    public long countByEvaluationStaffIdIsNull() {
        return orderDetailRepository.countByEvaluationStaffIdIsNull();
    }

    //=============================== Hàm để count các valuation staff đang làm order ==========================================
    //để admin chia việc
    public List<Map<String, Object>> countOrderDetailByEvaluationStaffId() {
        List<User> valuationStaffs = userRepository.findByRole(Role.valuation_staff);
        List<Map<String, Object>> result = new ArrayList<>();

        for (User valuationStaff : valuationStaffs) {
            long count = orderDetailRepository.countByEvaluationStaffIdAndStatus(valuationStaff.getUserId(), Status.Assigned);
            Map<String, Object> staffCount = new HashMap<>();
            staffCount.put("userId", valuationStaff.getUserId());
            staffCount.put("count", count);
            result.add(staffCount);
        }

        return result;
    }
}

