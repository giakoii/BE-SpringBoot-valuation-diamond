package org.swp391.valuationdiamond.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.swp391.valuationdiamond.controller.OrderDetailController;
import org.swp391.valuationdiamond.dto.OrderDTO;
import org.swp391.valuationdiamond.entity.primary.*;

import org.swp391.valuationdiamond.repository.primary.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImp {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EvaluationRequestRepository evaluationRequestRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private EvaluationServiceRepository evaluationServiceRepository;

    @Autowired
    private OrderDetailServiceImp orderDetailServiceImp;
    @Autowired
    private OrderDetailController orderDetailController;
    //=============================================== Create Order ===============================================

    public Order saveOrder(OrderDTO orderDTO) {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy"));

        User user = userRepository.findById(orderDTO.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        EvaluationRequest request = evaluationRequestRepository.findById(orderDTO.getRequestId()).orElseThrow(() -> new RuntimeException("Request not found"));

        if (orderDTO.getUserId() == null || orderDTO.getRequestId() == null) {
            throw new IllegalArgumentException("User ID and Request ID must not be null");
        }

        long count = orderRepository.count();
        String formattedCount = String.valueOf(count + 1);
        String orderId = "Or" + date + formattedCount;

        Order order = Order.builder()
                .orderId(orderId)
                .customerName(orderDTO.getCustomerName())
                .phone(orderDTO.getPhone())
                .diamondQuantity(orderDTO.getDiamondQuantity())
                .orderDate(orderDTO.getOrderDate())
                .status("In-Progress")
                .totalPrice(orderDTO.getTotalPrice())
                .userId(user)
                .requestId(request)
                .build();
        Order savedOrder = orderRepository.save(order);

        List<OrderDetail> orderDetails = orderDTO.getOrderDetails().stream()
                .map(od -> {
                    EvaluationService service = evaluationServiceRepository.findById(od.getServiceId())
                            .orElseThrow(() -> new RuntimeException("Service not found"));

                    long countDetail = orderDetailRepository.count();
                    String formattedCountDetail = String.valueOf(countDetail + 1);
                    String orderDetailId = "OD" + date + formattedCountDetail;

                    OrderDetail orderDetail = OrderDetail.builder()
                            .orderDetailId(orderDetailId)
                            .receivedDate(od.getReceivedDate())
                            .expiredReceivedDate(od.getExpiredReceivedDate())
                            .unitPrice(od.getUnitPrice())
                            .size(od.getSize())
                            .isDiamond(od.getIsDiamond())
                            .img(od.getImg())
                            .status("In-Progress")
                            .serviceId(service)
                            .evaluationStaffId(od.getEvaluationStaffId())
                            .orderId(savedOrder)
                            .build();

                    return orderDetailRepository.save(orderDetail);
                })
                .collect(Collectors.toList());

        savedOrder.setOrderDetailId(orderDetails);
        return savedOrder;
    }

    //===============================================Methods Get Order ===============================================

    public List<Order> getOrders() {

        return orderRepository.findOrderByStatus("In-Progress");
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrder(String id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order with id " + id + " not found"));
    }

    //API get order by request id
    public List<Order> getOrderByRequest(String requestId) {
        EvaluationRequest request = evaluationRequestRepository.findById(requestId).orElseThrow(() -> new RuntimeException("Request not found"));
        return orderRepository.findOrderByRequestId(request);
    }

    //get order by user id
    public List<Order> getOrderByUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return orderRepository.findOrderByUserId(user);
    }
    //===============================================Methods Update Order ===============================================

    public Order updateOrderStatus(String orderId, OrderDTO orderDTO) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));

        if (orderDTO.getStatus() != null) {
            order.setStatus(orderDTO.getStatus());
        }
        return orderRepository.save(order);
    }

    //===============================================Methods Delete Order ===============================================
    public boolean deleteOrder(String orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        orderRepository.delete(order);
        return true;
    }

    //    public long countOrdersRegisteredWithinMonth(int year, int month) {
//        YearMonth yearMonth = YearMonth.of(year, month);
//        LocalDate startDate = yearMonth.atDay(1);
//        LocalDate endDate = yearMonth.atEndOfMonth();
//
//        List<OrderDetail> allOrderDetails = orderDetailRepository.findAll();
//        List<OrderDetail> ordersWithinMonth = allOrderDetails.stream()
//                .filter(orderDetail -> {
//                    Date receivedDate = orderDetail.getReceivedDate();
//                    if (receivedDate == null) {
//                        return false;
//                    }
//                    LocalDate receivedLocalDate = receivedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//                    return (receivedLocalDate.isEqual(startDate) || receivedLocalDate.isAfter(startDate)) &&
//                            (receivedLocalDate.isEqual(endDate) || receivedLocalDate.isBefore(endDate));
//                })
//                .collect(Collectors.toList());
//
//        return ordersWithinMonth.size();
//    }
    public long countOrdersRegisteredWithinMonth(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        List<Order> allOrders = orderRepository.findAll();
        List<Order> ordersWithinMonth = allOrders.stream()
                .filter(order -> {
                    Date orderDate = order.getOrderDate();
                    if (orderDate == null) {
                        return false;
                    }
                    LocalDate LocalDate = orderDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    return (LocalDate.isEqual(startDate) || LocalDate.isAfter(startDate)) &&
                            (LocalDate.isEqual(endDate) || LocalDate.isBefore(endDate));
                })
                .collect(Collectors.toList());

        return ordersWithinMonth.size();
    }

    //
//
//    public BigDecimal sumTotalPriceWithinMonth(int year, int month) {
//        YearMonth yearMonth = YearMonth.of(year, month);
//        LocalDate startDate = yearMonth.atDay(1);
//        LocalDate endDate = yearMonth.atEndOfMonth();
//
//        List<Order> allOrders = orderRepository.findAll();
//        List<OrderDetail> allOrderDetails = orderDetailRepository.findAll();
//
//        BigDecimal totalPriceSum = allOrderDetails.stream()
//                .filter(orderDetail -> {
//                    Date receivedDate = orderDetail.getReceivedDate();
//                    if (receivedDate == null) {
//                        return false;
//                    }
//                    LocalDate receivedLocalDate = receivedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//                    return (receivedLocalDate.isEqual(startDate) || receivedLocalDate.isAfter(startDate)) &&
//                            (receivedLocalDate.isEqual(endDate) || receivedLocalDate.isBefore(endDate));
//                })
//                .map(orderDetail -> orderDetail.getOrderId().getTotalPrice())
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//
//        return totalPriceSum;
//    }
    public BigDecimal sumTotalPriceWithinMonth(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        List<Order> allOrders = orderRepository.findAll();


        BigDecimal totalPriceSum = allOrders.stream()
                .filter(order -> {
                    Date orderDate = order.getOrderDate();
                    if (orderDate == null) {
                        return false;
                    }
                    LocalDate receivedLocalDate = orderDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    return (receivedLocalDate.isEqual(startDate) || receivedLocalDate.isAfter(startDate)) &&
                            (receivedLocalDate.isEqual(endDate) || receivedLocalDate.isBefore(endDate));
                })
                .map(Order::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalPriceSum;
    }

    public int sumQuantityWithinMonth(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        List<Order> allOrders = orderRepository.findAll();

        int totalQuantitySum = allOrders.stream()
                .filter(order -> {
                    Date orderDate = order.getOrderDate();
                    if (orderDate == null) {
                        return false;
                    }
                    LocalDate receivedLocalDate = orderDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    return (receivedLocalDate.isEqual(startDate) || receivedLocalDate.isAfter(startDate)) &&
                            (receivedLocalDate.isEqual(endDate) || receivedLocalDate.isBefore(endDate));
                })
                .mapToInt(Order::getDiamondQuantity)
                .sum();

        return totalQuantitySum;
    }

}
