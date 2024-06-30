package org.swp391.valuationdiamond.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.swp391.valuationdiamond.config.CustomDateDeserializer;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderDetailDTO {
    String orderDetailId;
    String evaluationStaffId;
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @DateTimeFormat(pattern = "MM/dd/yyyy, HH:mm")
    Date receivedDate;
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @DateTimeFormat(pattern = "MM/dd/yyyy, HH:mm")
    Date expiredReceivedDate;
    Float unitPrice;
    String img;
    Float size;
    Boolean isDiamond;
    String status;
    String orderId;
    String serviceId;

}
