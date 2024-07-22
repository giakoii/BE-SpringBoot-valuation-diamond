package org.swp391.valuationdiamond.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CommittedPaperDTO {
    String committedId;

    @NotBlank
    String committedName;

    Date committedDate;

    @NotBlank
    String civilId;

    @NotBlank
    String userId;

    @NotBlank
    String orderId;
}
