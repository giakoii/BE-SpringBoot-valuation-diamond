package org.swp391.valuationdiamond.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.swp391.valuationdiamond.dto.OrderDetailDTO;
import org.swp391.valuationdiamond.entity.primary.OrderDetail;
import org.swp391.valuationdiamond.service.OrderDetailServiceImp;

import java.util.List;

@RequestMapping("/order_detail_request")
@RestController
public class OrderDetailController {
    @Autowired
    private OrderDetailServiceImp orderDetailServiceImp;
//    @PostMapping ("/create")
//    OrderDetail createOrderDetail(@RequestBody OrderDetailDTO orderDetailDTO){
//        return orderDetailServiceImp.createOrderDetail(orderDetailDTO);
//    }
//    @PostMapping("/create")
//    public ResponseEntity<List<OrderDetail>> saveAllOrderDetails(@RequestBody List<OrderDetailDTO> orderDetailsDTO) {
//    List<OrderDetail> savedOrderDetails = orderDetailServiceImp.saveAllOrderDetails(orderDetailsDTO);
//    return ResponseEntity.ok(savedOrderDetails);
//    }
//    @PostMapping("/create")
//    public ResponseEntity<List<OrderDetail>> saveAllOrderDetails(@RequestBody List<OrderDetailDTO> orderDetailsDTO) {
//    List<OrderDetail> savedOrderDetails = orderDetailServiceImp.saveAllOrderDetails(orderDetailsDTO);
//    return ResponseEntity.ok(savedOrderDetails);
//    }
    //tìm by order detail Id
    @GetMapping("/getOrderDe/{orderDetailId}")
    OrderDetail getOrderDetailId(@PathVariable("orderDetailId") String orderDetailId){
        return orderDetailServiceImp.getOrderDetailId(orderDetailId);
    }
    @PutMapping ("/getOrderDe/{orderDetailId}")
    OrderDetail updateOrderDe(@PathVariable("orderDetailId") String orderDetailId, @RequestBody OrderDetailDTO orderDetailDTO){
        return orderDetailServiceImp.updateOrderDeStatus(orderDetailId, orderDetailDTO);
    }
//    @GetMapping("/getOrderDetail/{orderId}")
//    public OrderDetail findOrderDetailList(@PathVariable("orderId") String orderId) {
//        return orderDetailServiceImp.findOrderDetailList(orderId);
//    }
    //tìm by order Id
    @GetMapping("/orderDetail/{orderId}")
    public List<OrderDetail> getOrderDetailsByOrderId(@PathVariable("orderId") String orderId) {
        return orderDetailServiceImp.getOrderDetailsByOrderId(orderId);
    }

    @GetMapping("/orderDetailByOrderStatus")
    public List<OrderDetail> getOrderDetailsByStatus() {
        return orderDetailServiceImp.getOrderDetailsByOrderStatusInProgress();
    }

    //hàm get all
    @GetMapping("/getOrderDetails")
    public List<OrderDetail> getOrderDetails(){
        return orderDetailServiceImp.getAllOrderDetail();
    }

    @PutMapping ("/getOrderDeEvaluationStaff/{orderDetailId}")
    OrderDetail updateOrderDeEvaluationStaff(@PathVariable("orderDetailId") String orderDetailId, @RequestBody OrderDetailDTO orderDetailDTO){
        return orderDetailServiceImp.updateOrderDeEvaluationStaff(orderDetailId, orderDetailDTO);
    }

    //hafm get staff == null
    @GetMapping("/getOrderDetailByEvaluationStaffIsNull")
    public List<OrderDetail> getOrderDetailByEvaluationStaffIsNull(){
        return orderDetailServiceImp.getOrderDetailByEvaluationStaffIsNull();
    }
    @PutMapping("/update_isdiamond/{orderDetailId}")
    public OrderDetail updateOrderDeIsDiamond(@PathVariable("orderDetailId") String orderDetailId, @RequestBody OrderDetailDTO orderDetailDTO) {
        return orderDetailServiceImp.updateOrderDeIsDiamond(orderDetailId, orderDetailDTO);
    }
    @PutMapping("updateAllOD/{orderDetailId}")
    public OrderDetail updateOrderDetail(@PathVariable("orderDetailId") String orderDetailId, @RequestBody OrderDetailDTO orderDetailDTO) {
        return orderDetailServiceImp.updateOrderDetail(orderDetailId, orderDetailDTO);
    }
    @GetMapping("/getOrderDetailByEvaluationStaffId/{evaluationStaffId}")
    public List<OrderDetail> getOrderDetailsByEvaluationStaffId(@PathVariable("evaluationStaffId") String evaluationStaffId){
        return orderDetailServiceImp.getOrderDetailByEvaluationStaffId(evaluationStaffId);
    }
}


