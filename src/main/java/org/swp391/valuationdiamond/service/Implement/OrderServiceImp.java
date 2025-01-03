package org.swp391.valuationdiamond.service.Implement;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.swp391.valuationdiamond.controller.OrderDetailController;
import org.swp391.valuationdiamond.dto.OrderDTO;
import org.swp391.valuationdiamond.entity.primary.*;

import org.swp391.valuationdiamond.repository.primary.*;
import org.swp391.valuationdiamond.service.Interface.IOrderService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class OrderServiceImp implements IOrderService {
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

    @Autowired
    private JavaMailSender javaMailSender;
    //=============================================== Create Order ===============================================

    //user --> request --> order --> orderDetail
    @Override
    public Order saveOrder(OrderDTO orderDTO) {

        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy"));

        User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        EvaluationRequest request = evaluationRequestRepository.findById(orderDTO.getRequestId())
                .orElseThrow(() -> new RuntimeException("Request not found"));

        long count = orderRepository.count();
        String formattedCount = String.valueOf(count + 1);
        String orderId = "Or" + date + formattedCount;
        Order order;
        try {
            order = Order.builder()
                    .orderId(orderId)
                    .customerName(orderDTO.getCustomerName())
                    .phone(orderDTO.getPhone())
                    .diamondQuantity(orderDTO.getDiamondQuantity())
                    .orderDate(orderDTO.getOrderDate())
                    .status(Status.In_Progress)
                    .totalPrice(orderDTO.getTotalPrice())
                    .userId(user)
                    .requestId(request)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while creating the order", e);
        }
        Order savedOrder = orderRepository.save(order);

        List<OrderDetail> orderDetails = orderDTO.getOrderDetails().stream()
                .map(od -> {
                    EvaluationService service = evaluationServiceRepository.findById(od.getServiceId())
                            .orElseThrow(() -> new RuntimeException("Service not found"));

                    long countDetail = orderDetailRepository.count();
                    String formattedCountDetail = String.valueOf(countDetail + 1);
                    String orderDetailId = "OD" + date + formattedCountDetail;
                    OrderDetail orderDetail;
                    try {
                        orderDetail = OrderDetail.builder()
                                .orderDetailId(orderDetailId)
                                .receivedDate(od.getReceivedDate())
                                .expiredReceivedDate(od.getExpiredReceivedDate())
                                .unitPrice(od.getUnitPrice())
                                .size(od.getSize())
                                .isDiamond(od.getIsDiamond())
                                .img(od.getImg())
                                .status(Status.In_Progress)
                                .serviceId(service)
                                .evaluationStaffId(od.getEvaluationStaffId())
                                .orderId(savedOrder)
                                .build();
                    } catch (Exception e) {
                        throw new RuntimeException("An error occurred while creating the order detail", e);
                    }
                    return orderDetailRepository.save(orderDetail);
                })
                .collect(Collectors.toList());

        savedOrder.setOrderDetailId(orderDetails);
        return savedOrder;
    }

    //===============================================Methods Get Order ===============================================
    @Override
    public List<Order> getOrders() {

        return orderRepository.findOrderByStatus(Status.In_Progress);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrder(String id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order with id " + id + " not found"));
    }

    //get order by request id
    @Override
    public List<Order> getOrderByRequest(String requestId) {
        EvaluationRequest request = evaluationRequestRepository.findById(requestId).orElseThrow(() -> new RuntimeException("Request not found"));
        return orderRepository.findOrderByRequestId(request);
    }

    //get order by user id

    //order <-- tương ứng request <-- tương ứng user
    @Override
    public List<Order> getOrderByUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        List<EvaluationRequest> requestId = evaluationRequestRepository.findByUserId(user);

        List<Order> orderList = new ArrayList<>();

        for (EvaluationRequest request : requestId) {
            List<Order> orders = orderRepository.findOrderByRequestId(request);
            orderList.addAll(orders);
        }
        return orderList;
    }

    //===============================================Methods Update Order ===============================================
    @Override
    public Order updateOrderStatus(String orderId, OrderDTO orderDTO) throws MessagingException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        EvaluationRequest request = order.getRequestId();

        if (orderDTO.getStatus() != null) {
            order.setStatus(Status.valueOf(orderDTO.getStatus()));
            orderRepository.save(order);
            if (order.getStatus().equals(Status.Completed)) {
                sendMessageToEmail(request.getRequestEmail(), request.getGuestName(), orderId);
            }
        }

        return order;
    }


    //===============================================Methods Delete Order ===============================================
    @Override
    public boolean deleteOrder(String orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        orderRepository.delete(order);
        return true;
    }

    //Count order
    public class MonthlyOrderCount {
        private int month;
        private long count;

        public MonthlyOrderCount(int month, long count) {
            this.month = month;
            this.count = count;
        }

        public int getMonth() {
            return month;
        }

        public long getCount() {
            return count;
        }

        @Override
        public String toString() {
            return "MonthlyOrderCount{" +
                    "month=" + month +
                    ", count=" + count +
                    '}';
        }
    }

    @Override
    public List<MonthlyOrderCount> countOrdersRegisteredPerMonth(int numberOfMonths) {
        YearMonth currentYearMonth = YearMonth.now();
        List<YearMonth> months = IntStream.range(0, numberOfMonths)
                .mapToObj(currentYearMonth::minusMonths)
                .sorted()
                .collect(Collectors.toList());

        return months.stream()
                .map(yearMonth -> {
                    LocalDate startDate = yearMonth.atDay(1);
                    LocalDate endDate = yearMonth.atEndOfMonth();

                    long count = orderRepository.findAll().stream()
                            .filter(order -> {
                                Date orderDate = order.getOrderDate();
                                if (orderDate == null) {
                                    return false;
                                }
                                LocalDate localDate = orderDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                                return (localDate.isEqual(startDate) || localDate.isAfter(startDate)) &&
                                        (localDate.isEqual(endDate) || localDate.isBefore(endDate));
                            })
                            .count();
                    int month = yearMonth.getMonthValue();
                    return new MonthlyOrderCount(month, count);
                })
                .collect(Collectors.toList());
    }

    public BigDecimal sumTotalPriceWithinAMonth() {
        YearMonth yearMonth = YearMonth.now();
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

    public BigDecimal sumTotalPricePreviousMonth() {
        YearMonth yearMonth = YearMonth.now().minusMonths(1);
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

    //Hàm này gửi tin nhắn đã định giá xong tới email của khách hàng
    private void sendMessageToEmail(String email, String name, String orderId) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setTo(email);
        helper.setSubject("Message");

        String text = "Dear " + name + ",\n\n"
                + "Your order of you is " + orderId + " is Completed,\n"
                + "Please come pick it up within 30 days.\n\n"
                + "Best regards,\n\n"
                + "Valuation Diamond.";
        helper.setText(text);

        javaMailSender.send(message);
    }


    public class PercentageChangeResult {
        private BigDecimal PrevMonth;
        private BigDecimal CurrMonth;
        private BigDecimal percentageChange;

        // Getters and Setters

        public BigDecimal getPrevMonth() {
            return PrevMonth;
        }

        public void setPrevMonth(BigDecimal PrevMonth) {
            this.PrevMonth = PrevMonth;
        }

        public BigDecimal getCurrMonth() {
            return CurrMonth;
        }

        public void setCurrMonth(BigDecimal CurrMonth) {
            this.CurrMonth = CurrMonth;
        }

        public BigDecimal getPercentageChange() {
            return percentageChange;
        }

        public void setPercentageChange(BigDecimal percentageChange) {
            this.percentageChange = percentageChange;
        }
    }

    @Override
    public PercentageChangeResult calculatePercentageChange() {
        BigDecimal totalPriceCurrentMonth = sumTotalPriceWithinAMonth();
        BigDecimal totalPricePreviousMonth = sumTotalPricePreviousMonth();

        BigDecimal percentageChange;
        if (totalPricePreviousMonth.compareTo(BigDecimal.ZERO) == 0) {
            percentageChange = BigDecimal.ZERO;
        } else {
            BigDecimal change = totalPriceCurrentMonth.subtract(totalPricePreviousMonth);
            percentageChange = change.divide(totalPricePreviousMonth, 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100));
        }

        PercentageChangeResult result = new PercentageChangeResult();
        result.setPrevMonth(totalPricePreviousMonth);
        result.setCurrMonth(totalPriceCurrentMonth);
        result.setPercentageChange(percentageChange);

        return result;
    }

    public class MonthlyTotalPrice {
        private int month;
        private BigDecimal totalPrice;

        public MonthlyTotalPrice(int month, BigDecimal totalPrice) {
            this.month = month;
            this.totalPrice = totalPrice;
        }

        public int getMonth() {
            return month;
        }

        public BigDecimal getTotalPrice() {
            return totalPrice;
        }

        @Override
        public String toString() {
            return "MonthlyTotalPrice{" +
                    "month=" + month +
                    ", totalPrice=" + totalPrice +
                    '}';
        }
    }

    @Override
    public List<MonthlyTotalPrice> sumTotalPriceWithinMonths(int numberOfMonths) {
        YearMonth currentYearMonth = YearMonth.now();

        // Generate a list of YearMonth objects for the past numberOfMonths in reverse order
        List<YearMonth> months = IntStream.range(0, numberOfMonths)
                .mapToObj(currentYearMonth::minusMonths)
                .sorted()
                .collect(Collectors.toList());

        return months.stream()
                .map(yearMonth -> {
                    LocalDate startDate = yearMonth.atDay(1);
                    LocalDate endDate = yearMonth.atEndOfMonth();

                    BigDecimal totalPriceSum = orderRepository.findAll().stream()
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

                    int month = yearMonth.getMonthValue();
                    return new MonthlyTotalPrice(month, totalPriceSum);
                })
                .collect(Collectors.toList());
    }

    public class MonthlyQuantitySum {
        private int month;
        private int totalQuantity;

        public MonthlyQuantitySum(int month, int totalQuantity) {
            this.month = month;
            this.totalQuantity = totalQuantity;
        }

        public int getMonth() {
            return month;
        }

        public int getTotalQuantity() {
            return totalQuantity;
        }

        @Override
        public String toString() {
            return "MonthlyQuantitySum{" +
                    "month=" + month +
                    ", totalQuantity=" + totalQuantity +
                    '}';
        }
    }

    @Override
    public List<MonthlyQuantitySum> sumQuantityWithinMonths(int numberOfMonths) {
        YearMonth currentYearMonth = YearMonth.now();

        // Generate a list of YearMonth objects for the past numberOfMonths in ascending order
        List<YearMonth> months = IntStream.range(0, numberOfMonths)
                .mapToObj(currentYearMonth::minusMonths)
                .sorted()
                .collect(Collectors.toList());

        return months.stream()
                .map(yearMonth -> {
                    LocalDate startDate = yearMonth.atDay(1);
                    LocalDate endDate = yearMonth.atEndOfMonth();

                    int totalQuantitySum = orderRepository.findAll().stream()
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

                    int month = yearMonth.getMonthValue();
                    return new MonthlyQuantitySum(month, totalQuantitySum);
                })
                .collect(Collectors.toList());
    }
}

