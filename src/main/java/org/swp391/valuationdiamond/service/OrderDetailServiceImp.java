package org.swp391.valuationdiamond.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.swp391.valuationdiamond.dto.OrderDetailDTO;
import org.swp391.valuationdiamond.entity.primary.EvaluationService;
import org.swp391.valuationdiamond.entity.primary.Order;
import org.swp391.valuationdiamond.entity.primary.OrderDetail;
import org.swp391.valuationdiamond.repository.primary.EvaluationServiceRepository;
import org.swp391.valuationdiamond.repository.primary.OrderDetailRepository;
import org.swp391.valuationdiamond.repository.primary.OrderRepository;
import org.swp391.valuationdiamond.repository.primary.UserRepository;

import java.util.List;

@Service
public class OrderDetailServiceImp {
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EvaluationServiceRepository evaluationServiceRepository;

    public OrderDetail getOrderDetailId(String id){
        return orderDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not Found"));
    }

    public OrderDetail updateOrderDeStatus(String orderDetailId, OrderDetailDTO orderDetailDTO){
        OrderDetail orderDetail = getOrderDetailId(orderDetailId);

        orderDetail.setStatus(orderDetailDTO.getStatus());

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
    }


    //update thằng staff
    public OrderDetail updateOrderDeEvaluationStaff(String orderDetailId, OrderDetailDTO orderDetailDTO){
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
    }

    public List<OrderDetail> getOrderDetailsByOrderId(String orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        return orderDetailRepository.findByOrderId(order);
    }

    public List<OrderDetail> getOrderDetailsByOrderStatusInProgress() {
        return orderDetailRepository.findByStatus("In-Progress");
    }

    //ham getall

    public List<OrderDetail> getAllOrderDetail() {
        return orderDetailRepository.findAll();
    }

    //hàm get staff == null
    public List<OrderDetail> getOrderDetailByEvaluationStaffIsNull(){
        return orderDetailRepository.findByEvaluationStaffIdIsNull();
    }
    public OrderDetail updateOrderDeIsDiamond(String orderDetailId, OrderDetailDTO orderDetailDTO){
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
    }

    //
    public OrderDetail updateOrderDetail(String orderDetailId, OrderDetailDTO orderDetailDTO) {
        OrderDetail orderDetail = orderDetailRepository.findById(orderDetailId).orElseThrow(() -> new RuntimeException("Order detail not found"));

        // Update properties from DTO only if they are not null
        if (orderDetailDTO.getOrderDetailId() != null) {
            orderDetail.setOrderDetailId(orderDetailDTO.getOrderDetailId());
        }
        if (orderDetailDTO.getEvaluationStaffId() != null) {
            orderDetail.setEvaluationStaffId(orderDetailDTO.getEvaluationStaffId());
        }
        if (orderDetailDTO.getStatus() != null) {
            orderDetail.setStatus(orderDetailDTO.getStatus());
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
    }
    public List<OrderDetail> getOrderDetailByEvaluationStaffId(String evaluationStaffId){
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
    public long countByEvaluationStaffIdIsNull(){
        return orderDetailRepository.countByEvaluationStaffIdIsNull();
    }
}

