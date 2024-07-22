package org.swp391.valuationdiamond.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.swp391.valuationdiamond.config.CustomDateDeserializer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {
    String orderId;
    @NotBlank
    String customerName;

    @NotBlank
    String phone;

    @NotNull
    int diamondQuantity;

    @JsonDeserialize(using = CustomDateDeserializer.class)
    @DateTimeFormat(pattern = "MM/dd/yyyy, HH:mm")
    Date orderDate;

    String status;

    BigDecimal totalPrice;

    String userId;

    String requestId;

    List<OrderDetailDTO> orderDetails;
}


