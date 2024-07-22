package org.swp391.valuationdiamond.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    Float unitPrice;

    String img;

    @NotNull
    Float size;

    Boolean isDiamond;
    @NotBlank
    String status;

    String orderId;

    String serviceId;

}
