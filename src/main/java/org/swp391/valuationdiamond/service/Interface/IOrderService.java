package org.swp391.valuationdiamond.service.Interface;

import jakarta.mail.MessagingException;
import org.swp391.valuationdiamond.dto.OrderDTO;
import org.swp391.valuationdiamond.entity.primary.Order;
import org.swp391.valuationdiamond.service.Implement.OrderServiceImp;

import java.util.List;

public interface IOrderService {
    Order saveOrder(OrderDTO orderDTO);

    List<Order> getOrders();

    Order getOrder(String id);

    List<Order> getAllOrders();

    List<Order> getOrderByRequest(String requestId);

    List<Order> getOrderByUser(String userId);

    Order updateOrderStatus(String orderId, OrderDTO orderDTO) throws MessagingException;

    boolean deleteOrder(String orderId);

    List<OrderServiceImp.MonthlyOrderCount> countOrdersRegisteredPerMonth(int numberOfMonths);

    List<OrderServiceImp.MonthlyTotalPrice> sumTotalPriceWithinMonths(int numberOfMonths);

    List<OrderServiceImp.MonthlyQuantitySum> sumQuantityWithinMonths(int numberOfMonths);

    OrderServiceImp.PercentageChangeResult calculatePercentageChange();
}
