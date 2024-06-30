package org.swp391.valuationdiamond.dto;

import lombok.*;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CommittedPaperDTO {
    String committedId;
    String committedName;
    Date committedDate;
    String civilId;
    String userId;
    String orderId;
}
